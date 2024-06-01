package com.example.teamcity.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.teamcity.api.models.Properties.defaultProperties;
import static com.example.teamcity.api.models.Properties.ldapProperties;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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