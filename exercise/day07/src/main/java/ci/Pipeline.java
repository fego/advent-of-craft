package ci;

import ci.dependencies.Config;
import ci.dependencies.Emailer;
import ci.dependencies.Logger;
import ci.dependencies.Project;

public class Pipeline {
    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    private boolean testsPassed = true;
    private boolean alreadyRun = false;

    public Pipeline(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        if (alreadyRun) {
            return;
        }
        alreadyRun = true;

        if (project.hasTests()) {
            runTests(project);
        } else {
            log.info("No tests");
        }

        if (testsPassed) {
            deploy(project);
        }

        if (config.sendEmailSummary()) {
            sendEmail(project);
        } else {
            log.info("Email disabled");
        }
    }

    private void deploy(Project project) {
        if (project.deployedSuccessfully()) {
            log.info("Deployment successful");
        } else {
            log.error("Deployment failed");
        }
    }

    private void runTests(Project project) {
        if ("success".equals(project.runTests())) {
            log.info("Tests passed");
            testsPassed = true;
        } else {
            log.error("Tests failed");
            testsPassed = false;
        }
    }

    private void sendEmail(Project project) {
        log.info("Sending email");
        if (this.testsPassed) {
            sendMailWhenTestsPassed(project);
        } else {
            emailer.send("Tests failed");
        }
    }

    private void sendMailWhenTestsPassed(Project project) {
        if (project.deployedSuccessfully()) {
            emailer.send("Deployment completed successfully");
        } else {
            emailer.send("Deployment failed");
        }
    }
}
