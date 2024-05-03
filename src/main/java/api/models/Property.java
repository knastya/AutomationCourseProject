package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    private String name;
    private Boolean value;

    public static Map<String, List<Property>> ldapProperty() {
        return new HashMap<>() {{
            put("property", singletonList(new Property("allowCreatingNewUsersByLogin", true)));
        }};
    }

    public static Map<String, List<Property>> defaultProperty() {
        return new HashMap<>() {{
            put("property",
                    Arrays.asList(
                            new Property("usersCanResetOwnPasswords", true),
                            new Property("usersCanChangeOwnPasswords", true),
                            new Property("freeRegistrationAllowed", false))
            );
        }};
    }

}