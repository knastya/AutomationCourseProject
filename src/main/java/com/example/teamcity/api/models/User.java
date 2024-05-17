package com.example.teamcity.api.models;

import com.example.teamcity.api.requests.unchecked.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements ToDelete {
    private String username;
    private String password;
    private String email;
    private Roles roles;

    @Override
    public void delete() {
        new UncheckedRequests(Specifications.superUserSpec()).getUserRequest().deleteByUsername(username);
    }
}
