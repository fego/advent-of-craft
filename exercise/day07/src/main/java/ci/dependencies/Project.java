package ci.dependencies;

public class Project {
    private final DeploymentStatus deploymentStatus;
    private final TestStatus testStatus;

    private Project(TestStatus testStatus, DeploymentStatus deploymentStatus) {
        this.testStatus = testStatus;
        this.deploymentStatus = deploymentStatus;
    }

    public static ProjectBuilder builder() {
        return new ProjectBuilder();
    }

    public boolean hasTests() {
        return testStatus != TestStatus.NO_TESTS;
    }

    public String runTests() {
        return testStatus == TestStatus.PASSING_TESTS ? "success" : "failure";
    }

    public boolean deployedSuccessfully() {
        return deploymentStatus == DeploymentStatus.SUCCESS;
    }

    public static class ProjectBuilder {
        private TestStatus testStatus;

        private DeploymentStatus deploymentStatus;

        public ProjectBuilder setTestStatus(TestStatus testStatus) {
            this.testStatus = testStatus;
            return this;
        }

        public ProjectBuilder setDeploymentStatus(DeploymentStatus deploymentStatus) {
            this.deploymentStatus = deploymentStatus;
            return this;
        }

        public Project build() {
            return new Project(testStatus, deploymentStatus);
        }
    }
}
