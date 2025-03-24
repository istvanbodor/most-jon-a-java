package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FejlettGombatest extends Gombatest {
    public void sporaKiloves(Tekton tekton) {}
}
