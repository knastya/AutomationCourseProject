package com.example.teamcity.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {
    private List<Property> property;

    public static Properties defaultProperties() {
        var propertyList = Arrays.asList(
                new Property("usersCanResetOwnPasswords", true),
                new Property("usersCanChangeOwnPasswords", true),
                new Property("freeRegistrationAllowed", false)
        );
        return new Properties(propertyList);
    }

    public static Properties ldapProperties() {
        var propertyList = singletonList(new Property("allowCreatingNewUsersByLogin", true));
        return new Properties(propertyList);
    }
}
