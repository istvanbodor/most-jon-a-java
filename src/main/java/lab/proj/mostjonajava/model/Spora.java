package lab.proj.mostjonajava.model;

import lombok.Data;

@Data
public abstract class Spora {
    private Tekton tekton;
    private Gombatest gombatest;
    private int tapanyagErtek;
    public abstract void hatasKifejtese();
    public abstract Spora ujSporaLetrehozasa();
}
