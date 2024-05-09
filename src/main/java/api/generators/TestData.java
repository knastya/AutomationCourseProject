package api.generators;

import api.models.BuildType;
import api.models.NewProjectDescription;
import api.models.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private User user;
    private NewProjectDescription project;
    private BuildType buildType;
}
