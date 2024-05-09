package api.models;

import api.requests.unchecked.UncheckedRequests;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static api.generators.TestDataGenerator.ROOT;
import static api.spec.Specifications.superUserSpec;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProjectDescription implements ToDelete {
    private Project parentProject;
    private String name;
    private String id;
    private Boolean copyAllAssociatedSettings;
    private String parentProjectId;

    @Override
    public void delete() {
        if (Objects.equals(parentProjectId, ROOT)) {
            new UncheckedRequests(superUserSpec()).getProjectRequest().delete(id);
        }
    }
}
