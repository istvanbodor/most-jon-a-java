package lab.proj.mostjonajava.model;

import java.util.List;
import java.util.ArrayList;

import static lab.proj.mostjonajava.utils.Logger.hivasLog;
import static lab.proj.mostjonajava.utils.Logger.log;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class Rovarasz extends Jatekos {

    private static int nextId = 1;
    private int id;

    private List<Rovar> rovarok = new ArrayList<>();

    /**
     * Rovarász osztály konstruktora.
     * @param nev
     */
    public Rovarasz(String nev) {
        super(nev);
        hivasLog("Rovarasz(String nev)", List.of("nev: String - " + nev), 1);
        this.id = nextId++;
        rovarok = new ArrayList<>();
        log("Rovarasz letrejott");
    }

    /**
     * Visszatér a rovarász id-jával.
     * @return 
     */
    public int getId(){ return id; }

    /**
     * Visszatér a rovarász rovarjaival.
     * @return
     */
    public List<Rovar> getRovarok() {
        hivasLog("getRovarok()", List.of(), 1);
        return rovarok;
    }
    
}

