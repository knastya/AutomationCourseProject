package com.example.teamcity.api.models;

import com.example.teamcity.api.requests.unchecked.UncheckedRequests;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.teamcity.api.spec.Specifications.superUserSpec;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements ToDelete {
    private String username;
    private String password;
    private String email;
    private Roles roles;

    @Override
    public void delete() {
        new UncheckedRequests(superUserSpec()).getUserRequest().deleteByUsername(username);
    }
}
