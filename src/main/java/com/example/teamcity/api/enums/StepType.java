package com.example.teamcity.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StepType {

    CMD_LINE("simpleRunner"),
    POWERSHELL("jetbrains_powershell");

    private final String text;

    @Override
    public String toString() {
        return text;
    }
}
