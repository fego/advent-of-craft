package ci;

import ci.dependencies.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static ci.dependencies.TestStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PipelineTest {
    private final Config config = mock(Config.class);
    private final CapturingLogger log = new CapturingLogger();
    private final Emailer emailer = mock(Emailer.class);

    private Pipeline pipeline;

    @BeforeEach
    void setUp() {
        pipeline = new Pipeline(config, emailer, log);
    }

    @Test
    void project_with_tests_that_deploys_successfully_with_email_notification() {
        // given
        when(config.sendEmailSummary()).thenReturn(true);
        var project = buildProject(PASSING_TESTS, DeploymentStatus.SUCCESS);
        // when
        pipeline.run(project);
        // then
        assertThat(log.getLoggedLines()).containsExactly(
                "INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Sending email");

        verify(emailer).send("Deployment completed successfully");
    }

    @Test
    void project_with_tests_that_deploys_successfully_without_email_notification() {
        // given
        when(config.sendEmailSummary()).thenReturn(false);
        Project project = buildProject(PASSING_TESTS, DeploymentStatus.SUCCESS);
        // when
        pipeline.run(project);
        // then
        assertThat(log.getLoggedLines()).containsExactly(
                "INFO: Tests passed",
                "INFO: Deployment successful",
                "INFO: Email disabled");
    }

    @Test
    void project_without_tests_that_deploys_successfully_with_email_notification() {
        // given
        when(config.sendEmailSummary()).thenReturn(true);
        Project project = buildProject(NO_TESTS, DeploymentStatus.SUCCESS);
        // when
        pipeline.run(project);
        // then
        assertThat(log.getLoggedLines()).containsExactly(
                "INFO: No tests",
                "INFO: Deployment successful",
                "INFO: Sending email");

        verify(emailer).send("Deployment completed successfully");
    }

    @Test
    void project_without_tests_that_deploys_successfully_without_email_notification() {
        // given
        when(config.sendEmailSummary()).thenReturn(false);
        Project project = buildProject(NO_TESTS, DeploymentStatus.SUCCESS);
        // when
        pipeline.run(project);
        // then
        assertThat(log.getLoggedLines()).containsExactly(
                "INFO: No tests",
                "INFO: Deployment successful",
                "INFO: Email disabled");

        verify(emailer, never()).send(any());
    }

    @Test
    void project_with_tests_that_fail_with_email_notification() {
        // given
        when(config.sendEmailSummary()).thenReturn(true);
        Project project = buildProject(FAILING_TESTS, DeploymentStatus.NOT_RUN);

        // when
        pipeline.run(project);

        // then
        assertEquals(Arrays.asList(
                "ERROR: Tests failed",
                "INFO: Sending email"
        ), log.getLoggedLines());

        verify(emailer).send("Tests failed");
    }

    @Test
    void project_with_tests_that_fail_without_email_notification() {
        // given
        when(config.sendEmailSummary()).thenReturn(false);

        Project project = buildProject(FAILING_TESTS, DeploymentStatus.NOT_RUN);

        // when
        pipeline.run(project);

        // then
        assertEquals(Arrays.asList(
                "ERROR: Tests failed",
                "INFO: Email disabled"
        ), log.getLoggedLines());

        verify(emailer, never()).send(any());
    }

    @Test
    void project_with_tests_and_failing_build_with_email_notification() {
        // given
        when(config.sendEmailSummary()).thenReturn(true);

        Project project = buildProject(PASSING_TESTS, DeploymentStatus.FAILURE);

        // when
        pipeline.run(project);

        // then
        assertThat(log.getLoggedLines()).containsExactly(
                "INFO: Tests passed",
                "ERROR: Deployment failed",
                "INFO: Sending email");

        verify(emailer).send("Deployment failed");
    }

    @Test
    void project_with_tests_and_failing_build_without_email_notification() {
        // given
        when(config.sendEmailSummary()).thenReturn(false);

        Project project = buildProject(PASSING_TESTS, DeploymentStatus.FAILURE);

        // when
        pipeline.run(project);

        // then
        assertThat(log.getLoggedLines()).containsExactly(
                "INFO: Tests passed",
                "ERROR: Deployment failed",
                "INFO: Email disabled");

        verify(emailer, never()).send(any());
    }

    @Test
    void project_without_tests_and_failing_build_with_email_notification() {
        // given
        when(config.sendEmailSummary()).thenReturn(true);

        Project project = buildProject(NO_TESTS, DeploymentStatus.FAILURE);

        // when
        pipeline.run(project);

        // then
        assertThat(log.getLoggedLines()).containsExactly(
                "INFO: No tests",
                "ERROR: Deployment failed",
                "INFO: Sending email");

        verify(emailer).send("Deployment failed");
    }

    @Test
    void project_without_tests_and_failing_build_without_email_notification() {
        // given
        when(config.sendEmailSummary()).thenReturn(false);

        Project project = buildProject(NO_TESTS, DeploymentStatus.FAILURE);

        // when
        pipeline.run(project);

        // then
        assertThat(log.getLoggedLines()).containsExactly(
                "INFO: No tests",
                "ERROR: Deployment failed",
                "INFO: Email disabled");

        verify(emailer, never()).send(any());
    }

    private static Project buildProject(TestStatus testStatus, DeploymentStatus deploymentStatus) {
        return Project.builder()
                .setTestStatus(testStatus)
                .setDeploymentStatus(deploymentStatus)
                .build();
    }
}