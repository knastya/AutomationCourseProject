package com.example.teamcity.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleId {
    SYSTEM_ADMIN("SYSTEM_ADMIN"),
    PROJECT_ADMIN("PROJECT_ADMIN"),
    PROJECT_DEVELOPER("PROJECT_DEVELOPER"),
    PROJECT_VIEWER("PROJECT_VIEWER"),
    AGENT_MANAGER("AGENT_MANAGER");

    private final String text;

    @Override
    public String toString() {
        return text;
    }
}
