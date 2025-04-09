package lab.proj.mostjonajava.model;

import lombok.Data;

@Data
public abstract class Spora {

    private int tapanyagErtek;

    /**
     * Spora konstuktora.
     * @param tapErtek
     */
    Spora(int tapErtek){ tapanyagErtek = tapErtek; }

    /**
     * Visszaadja a spora tapanyag erteket.
     */
    public int getTapanyag(){ return tapanyagErtek; }

    /**
     * A spora kifejti a hatasat a rovarra, amelyik megeszi,
     * ezt a fuggvenyt a leszarmazott osztalyai valositjak meg.
     * @param rovar
     */
    public abstract void hatasKifejtese(Rovar rovar);

    
}
