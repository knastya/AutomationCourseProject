package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static api.models.Properties.defaultProperties;
import static api.models.Properties.ldapProperties;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    private String name;
    private Properties properties;

    public static Module defaultModule() {
        return Module.builder()
                .name("Default")
                .properties(defaultProperties())
                .build();
    }

    public static Module httpBasic() {
        return Module.builder().name("HTTP-Basic").build();
    }

    public static Module ldapModule() {
        return Module.builder()
                .name("LDAP")
                .properties(ldapProperties())
                .build();
    }

    public static Module tokenAuthModule() {
        return Module.builder().name("Token-Auth").build();
    }
}