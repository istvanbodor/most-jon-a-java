package lab.proj.mostjonajava.model;

import lab.proj.mostjonajava.utils.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class FejlettGombatest extends Gombatest {
    /**
     * Felulirja a gombatest spora kilovo metodusat,
     * a szomszedos tektonok szomszedaira is tud sporat loni.
     * @param tekton
     */
    @Override
    public void sporaKiloves(Tekton tekton) {
        hivasLog("sporaKiloves(Tekton tekton)", List.of("tekton: Tekton - " + tekton.toString()), 0);
        log("Fejlett gombatest kilotte a sporat");
        tekton.sporaHozzaadasa(new SimaSpora());
    }
}
