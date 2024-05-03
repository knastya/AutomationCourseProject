package api.generators;

import api.enums.RoleId;
import api.models.*;

import java.util.Collections;

import static api.enums.RoleId.SYSTEM_ADMIN;

public class TestDataGenerator {

    public static TestData generate() {
        var user = User.builder()
                .username(RandomData.getString())
                .password(RandomData.getString())
                .email(RandomData.getString() + "@gmail.com")
                .roles(Roles.builder()
                        .role(Collections.singletonList(Role.builder()
                                .roleId(SYSTEM_ADMIN)
                                .scope("g")
                                .build()))
                        .build())
                .build();
        var project = NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("id:_Root")
                        .build())
                .name(RandomData.getString())
                .id(RandomData.getString())
                .copyAllAssociatedSettings(true)
                .build();
        var buildType = BuildType.builder()
                .id(RandomData.getString())
                .name(RandomData.getString())
                .project(project)
                .build();
        return TestData.builder()
                .user(user)
                .project(project)
                .buildType(buildType)
                .build();
    }

    public static Roles generateRoles(RoleId roleId, String scope) {
        return Roles.builder().role
                (Collections.singletonList(
                        Role.builder()
                                .roleId(roleId)
                                .scope(scope)
                                .build()))
                .build();
    }
}
