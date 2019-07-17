package exc;

public class ProjectNotFoundException extends Throwable {

    private String projectId;

    public ProjectNotFoundException(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Project with id " + projectId + "not found";
    }
}
