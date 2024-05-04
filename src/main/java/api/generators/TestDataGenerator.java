package api.generators;

import api.enums.RoleId;
import api.models.*;

import static api.enums.RoleId.SYSTEM_ADMIN;
import static java.util.Collections.singletonList;

public class TestDataGenerator {

    public static final String ROOT = "_Root";

    public static TestData generate() {
        var user = User.builder()
                .username(RandomData.getString())
                .password(RandomData.getString())
                .email(RandomData.getString() + "@gmail.com")
                .roles(Roles.builder()
                        .role(singletonList(Role.builder()
                                .roleId(SYSTEM_ADMIN)
                                .scope("g")
                                .build()))
                        .build())
                .build();
        var newProjectDescription = NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("id:" + ROOT)
                        .build())
                .name(RandomData.getString())
                .id(RandomData.getString())
                .copyAllAssociatedSettings(true)
                .build();
        var project = Project.builder()
                .id(newProjectDescription.getId())
                .build();
        var buildType = BuildType.builder()
                .id(RandomData.getString())
                .name(RandomData.getString())
                .project(project)
                .build();
        return TestData.builder()
                .user(user)
                .project(newProjectDescription)
                .buildType(buildType)
                .build();
    }

    public static Roles generateRoles(RoleId roleId, String scope) {
        var role = Role.builder()
                .roleId(roleId)
                .scope(scope)
                .build();
        return Roles.builder().role(singletonList(role)).build();
    }
}
