package lab.proj.mostjonajava.model;

import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.ToString;

@Data
public class GombaFonal {

    private Tekton honnan;
    private Tekton hova;
    @ToString.Exclude
    private Gombatest gombatest;

    /**
     * GombaFonal konsturktora.
     * @param honnan
     * @param hova
     * @param gombatest
     */
    public GombaFonal(Tekton honnan, Tekton hova, Gombatest gombatest) {
        this.honnan = honnan;
        this.hova = hova;
        this.gombatest = gombatest; 
        log("Fonal letrejott");
    }

    /**
     * Ha a gombafonal egyik tektonján van egy bénult rovar akkor azt a fonal meg tudja enni és helyére gombatest nő.
     * @param rovar
     */
    public void rovarElfogyasztas(Rovar rovar){

        // lekérjük, hogy a rovar melyik tektonon van
        Tekton rovarTektonja = rovar.getTekton();

        // ellenőrizzük, hogy a rovar a gombafonal egyik végpontján van-e és bénult-e
        if ((!rovarTektonja.equals(honnan) && !rovarTektonja.equals(hova)) || !rovar.getBenulas()) {
            log("A rovar nem fogyaszthato el.");
            return;
        }

        // eltávolítjuk a rovart a tektonjáról
        rovarTektonja.getRovarok().remove(rovar);
        // eltávolítjuk a rovart a hozzá tartozó rovarász rovargyűjteményéből is
        rovar.getRovarasz().getRovarok().remove(rovar);

        // új gombatestet hozunk létre a rovar helyén, a meglévő gombatest gombászával
        Gombatest ujGombatest = new Gombatest(rovarTektonja, gombatest.getGombasz());
        gombatest.getGombasz().getGombatestek().add(ujGombatest);
        // beállítjuk az új gombatestet a tektonra (amennyiben lehetséges)
        // ha a tekton "TestNelkuliTekton", akkor ez a metódus nem fog változást okozni
        rovarTektonja.setGombatest(ujGombatest);
        log("Rovar elfogyasztva.");
    }

    /**
     * Visszatér a gombafonal egyik tektonjával. 
     * @return
     */
    public Tekton getHonnan(){
        return honnan;
    }

    /**
     * Visszatér a gombafonal másik tektonjával. 
     * @return
     */
    public Tekton getHova(){
        return hova;
    }

    /**
     * Visszatér a gombafonal gombatestével. 
     * @return
     */
    public Gombatest getGombatest(){
        return gombatest;
    }

    /**
     * Beállíthatjuk vele a gombafonál gombatestjét.
     * @param gombatest
     */
    public void setGombatest(Gombatest gombatest){
        this.gombatest = gombatest;
    }

}
