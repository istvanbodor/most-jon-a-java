package lab.proj.mostjonajava.model;

import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
@Data
public class GombaFonal {

    private Tekton honnan;
    private Tekton hova;
    private Gombatest gombatest;

    /**
     * GombaFonal konsturktora.
     * @param honnan
     * @param hova
     * @param gombatest
     */
    public GombaFonal(Tekton honnan, Tekton hova, Gombatest gombatest) {
        hivasLog("GombaFonal(Tekton honnan, Tekton hova, Gombatest gombatest)", List.of(), 2);
        this.honnan = honnan;
        this.hova = hova;
        this.gombatest = gombatest; 
        log("Fonal letrejott a ket tekton kozott");
    }

    /**
     * Ha a gombafonal egyik tektonján van egy bénult rovar akkor azt a fonal meg tudja enni és helyére gombatest nő.
     * @param rovar
     */
    public void rovarElfogyasztas(Rovar rovar){
        hivasLog("rovarElfogyasztas(Rovar rovar)", List.of("rovar: Rovar"), 1);
    
        Tekton rovarTektonja = rovar.getTekton();

        if (!(rovarTektonja.equals(honnan) || rovarTektonja.equals(hova))) {
            log("A rovar nem a fonal egyik végén van, nem fogyasztható el.");
            return;
        }

        if (!rovar.getBenulas()) {
            log("A rovar nincs lebénulva, nem fogyasztható el.");
            return;
        }

        rovarTektonja.getRovarok().remove(rovar);
        rovar.getRovarasz().getRovarok().remove(rovar);

        Gombatest ujGombatest = new Gombatest(rovarTektonja, gombatest.getGombasz());
        getGombatest().getGombasz().getGombatestek().add(ujGombatest);
        rovarTektonja.setGombatest(ujGombatest);
        log("Új Gombatest letrejott a rovar helyén.");
    }

    /**
     * Visszatér a gombafonal egyik tektonjával. 
     * @return
     */
    public Tekton getHonnan(){ return honnan; }

    /**
     * Visszatér a gombafonal másik tektonjával. 
     * @return
     */
    public Tekton getHova(){ return hova; }

    /**
     * Visszatér a gombafonal gombatestével. 
     * @return
     */
    public Gombatest getGombatest(){ return gombatest; }

    /**
     * Beállíthatjuk vele a gombafonál gombatestjét.
     * @param gombatest
     */
    public void setGombatest(Gombatest gombatest){ this.gombatest = gombatest; }

}
