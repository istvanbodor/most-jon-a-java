package lab.proj.mostjonajava.model;

// package lab.proj.mostjonajava.ui;

public interface Nezet {
    /** Kiír valamit a felhasználónak (konzol, GUI stb.) */
    void kiir(String szoveg);

    /** Frissíti az egész játék állapotának megjelenítését */
    void frissit(Jatek jatek);
}
