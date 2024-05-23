package com.example.teamcity.api.models;

import com.example.teamcity.api.enums.RoleId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private RoleId roleId;
    private String scope;
    private String href;
}
