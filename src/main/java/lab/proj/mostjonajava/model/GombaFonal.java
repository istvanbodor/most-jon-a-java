package lab.proj.mostjonajava.model;


//import static lab.proj.mostjonajava.utils.Logger.hivasLog;
//import static lab.proj.mostjonajava.utils.Logger.log;
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
        //hivasLog("GombaFonal(Tekton honnan, Tekton hova, Gombatest gombatest)", List.of("honnan: Tekton - " + honnan.toString(), "hova: Tekton - " + hova.toString()  +" gombatest: Gombatest"), 2);
        this.honnan = honnan;
        this.hova = hova;
        this.gombatest = gombatest; 
        //log("Fonal letrejott");
    }

    /**
     * Ha a gombafonal egyik tektonján van egy bénult rovar akkor azt a fonal meg tudja enni és helyére gombatest nő.
     * @param rovar
     */
    public void rovarElfogyasztas(Rovar rovar){
        //hivasLog("rovarElfogyasztas(Rovar rovar)", List.of("rovar: Rovar"), 1);
    
        Tekton rovarTektonja = rovar.getTekton();

        if ((!rovarTektonja.equals(honnan) && !rovarTektonja.equals(hova)) || !rovar.getBenulas()) {
            //log("A rovar nem fogyaszthato el.");
            return;
        }

        rovarTektonja.getRovarok().remove(rovar);
        rovar.getRovarasz().getRovarok().remove(rovar);

        Gombatest ujGombatest = new Gombatest(rovarTektonja, gombatest.getGombasz());
        rovarTektonja.setGombatest(ujGombatest); //ha TestNelkuliTektonrol van szó akkor ebben nem történik semmi
        //log("Rovar elfogyasztva.");
    }

    /**
     * Visszatér a gombafonal egyik tektonjával. 
     * @return
     */
    public Tekton getHonnan(){
        //hivasLog("getHonnan()", List.of(), 1);
        return honnan;
    }

    /**
     * Visszatér a gombafonal másik tektonjával. 
     * @return
     */
    public Tekton getHova(){
        //hivasLog("getHova()", List.of(), 1);
        return hova;
    }

    /**
     * Visszatér a gombafonal gombatestével. 
     * @return
     */
    public Gombatest getGombatest(){
        //hivasLog("getGombatest()", List.of(), 1);
        return gombatest;
    }

    /**
     * Beállíthatjuk vele a gombafonál gombatestjét.
     * @param gombatest
     */
    public void setGombatest(Gombatest gombatest){
        //hivasLog("setGombatest(Gombatest gombatest)", List.of("gombatest: Gombatest"), 0);
        this.gombatest = gombatest;
    }

}
