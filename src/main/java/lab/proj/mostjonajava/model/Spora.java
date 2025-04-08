package lab.proj.mostjonajava.model;

import lombok.Data;

@Data
public abstract class Spora {
    private Tekton tekton;
    private Gombatest gombatest;
    private int tapanyagErtek;

    /**
     * Spora konstuktora.
     */
    Spora(int tapErtek){
        this.tapanyagErtek = tapErtek;
    }

    /**
     * A spora kifejti a hatasat a rovarra, amelyik megeszi,
     * ezt a fuggvenyt a leszarmazott osztalyai valositjak meg.
     */
    public abstract void hatasKifejtese(Rovar rovar);

    public int getTapanyag(){
        return tapanyagErtek;
    }
}
