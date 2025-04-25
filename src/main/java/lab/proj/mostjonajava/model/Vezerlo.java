package lab.proj.mostjonajava.model;

import lab.proj.mostjonajava.game.Jatek;


// package lab.proj.mostjonajava.ui;

public interface Vezerlo {
    /** Beállítja, hogy melyik Jatek-példányt kezelje */
    void setJatek(Jatek jatek);

    /** Feldolgoz egy bejövő parancssori vagy gombnyomásos parancsot */
    void parancsFeldolgozas(String bemenet);
}
