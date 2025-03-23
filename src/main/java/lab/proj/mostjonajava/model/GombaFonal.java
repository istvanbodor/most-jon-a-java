package lab.proj.mostjonajava.model;

import lombok.Data;

@Data
public class GombaFonal {
    private Tekton honnan;
    private Tekton hova;
    private Gombatest gombatest;

    public void fonalMasikTektonhozKapcsolas(Tekton tekton) {}
    public void ujGombaFonalLetrehozasa() {}
    public void fonalTorlese() {}
}
