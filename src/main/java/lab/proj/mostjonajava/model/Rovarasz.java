package lab.proj.mostjonajava.model;

import java.util.ArrayList;
import java.util.List;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
//@Data
//@NoArgsConstructor
public class Rovarasz extends Jatekos {
    private static int nextId = 1;
    private int id;
    private String nev;
    private List<Rovar> rovarok = new ArrayList<>();

    public Rovarasz(String nev) {
        super(nev);
        this.id = nextId++;
    }

    public void setRovar(Rovar rovar) {
        if (rovar != null && !rovarok.contains(rovar)) {
            rovarok.add(rovar);
            hivasLog("Rovar(String nev)", List.of("nev: String - " + nev), 1);

        }
    }

    public void torolRovar(Rovar rovar) {
        if (rovar != null && rovarok.remove(rovar)) {
        }
    }

    public List<Rovar> getRovarok() {
        return new ArrayList<>(rovarok);
    }
}

