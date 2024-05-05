package api.generators;

import api.enums.RoleId;
import api.models.*;

import static api.enums.RoleId.SYSTEM_ADMIN;
import static api.generators.RandomData.getRandomString;
import static api.models.Step.buildCommandLineStep;
import static java.util.Collections.singletonList;

public class TestDataGenerator {

    public static final String ROOT = "_Root";

    public static TestData generate() {
        var user = User.builder()
                .username(getRandomString())
                .password(getRandomString())
                .email(getRandomString() + "@gmail.com")
                .roles(generateRoles(SYSTEM_ADMIN, "g"))
                .build();
        var newProjectDescription = NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("id:" + ROOT)
                        .build())
                .name(getRandomString())
                .id(getRandomString())
                .copyAllAssociatedSettings(true)
                .build();
        var buildType = BuildType.builder()
                .id(getRandomString())
                .name(getRandomString())
                .project(Project.builder()
                        .id(newProjectDescription.getId())
                        .build())
                .steps(new Steps(buildCommandLineStep()))
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
        return new Roles(singletonList(role));
    }

    public static String projectScope(String projectId) {
        return "p:" + projectId;
    }

}
