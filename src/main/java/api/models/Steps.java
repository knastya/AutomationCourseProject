package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Collections.singletonList;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Steps {
    private Integer count;
    private List<Step> step;

    public Steps(Step step){
        this.step = singletonList(step);
    }
}
