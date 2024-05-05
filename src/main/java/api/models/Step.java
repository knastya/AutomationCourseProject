package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static api.enums.StepType.CMD_LINE;
import static api.enums.StepType.POWERSHELL;
import static api.generators.RandomData.getRandomString;
import static java.util.Collections.singletonList;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
