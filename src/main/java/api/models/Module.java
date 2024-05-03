package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import static api.models.Property.defaultProperty;
import static api.models.Property.ldapProperty;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    private String name;
    private Map<String, List<Property>> properties;

    public static Module defaultModule() {
        return Module.builder()
                .name("Default")
                .properties(defaultProperty())
                .build();
    }

    public static Module httpBasic(){
       return Module.builder().name("HTTP-Basic").build();
    }

    public static Module ldapModule() {
        return Module.builder()
                .name("LDAP")
                .properties(ldapProperty())
                .build();
    }

    public static Module tokenAuthModule() {
        return Module.builder().name("Token-Auth").build();
    }
}