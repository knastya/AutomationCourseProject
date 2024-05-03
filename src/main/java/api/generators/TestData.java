package api.generators;

import api.models.BuildType;
import api.models.NewProjectDescription;
import api.models.User;
import api.requests.unchecked.UncheckedProject;
import api.requests.unchecked.UncheckedUser;
import lombok.Builder;
import lombok.Data;

import static api.spec.Specifications.superUserSpec;

@Builder
@Data
public class TestData {
    private User user;
    private NewProjectDescription project;
    private BuildType buildType;

    public void delete() {
        var spec = superUserSpec();
        new UncheckedProject(spec).delete(project.getId());
        new UncheckedUser(spec).delete(user.getUsername());
    }
}
