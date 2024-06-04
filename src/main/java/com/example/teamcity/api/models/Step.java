package com.example.teamcity.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.teamcity.api.enums.StepType.CMD_LINE;
import static com.example.teamcity.api.enums.StepType.POWERSHELL;
import static com.example.teamcity.api.generators.RandomData.getRandomString;
import static java.util.Collections.singletonList;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Step {
    private String name;
    private String type;
    private Properties properties;

    public static Step buildStep(String stepName, String value, String stepType, String propertyName) {
        var property = Property.builder()
                .name(propertyName)
                .value(value)
                .build();
        return Step.builder()
                .name(stepName)
                .type(stepType)
                .properties(new Properties(singletonList(property)))
                .build();
    }

    public static Step buildCommandLineStep(){
        return buildStep(getRandomString(), "echo 'Hello World!'", CMD_LINE.getText(), "script.content");
    }

    public static Step buildPowerShellStep(){
        return buildStep(getRandomString(), "Write-Host 'Hello, World!'", POWERSHELL.getText(), "script.content");
    }
}
