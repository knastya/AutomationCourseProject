package api.models;

import api.requests.unchecked.UncheckedRequests;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static api.spec.Specifications.superUserSpec;

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
        new UncheckedRequests(superUserSpec()).getUserRequest().deleteByUsername(username);
    }
}
