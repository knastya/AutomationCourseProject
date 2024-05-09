package api.generators;

import api.models.BuildType;
import api.models.NewProjectDescription;
import api.models.User;
import api.requests.unchecked.UncheckedRequests;
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
        new UncheckedRequests(spec).getProjectRequest().delete(project.getId());
        new UncheckedRequests(spec).getUserRequest().deleteByUsername(user.getUsername());
    }
}
