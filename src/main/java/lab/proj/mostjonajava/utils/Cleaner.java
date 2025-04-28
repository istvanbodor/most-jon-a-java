package lab.proj.mostjonajava.utils;

import lab.proj.mostjonajava.model.*;

public class Cleaner {
    public static void cleanUp() {
        Gombasz.nextId = 1;
        Gombatest.nextId = 1;
        Rovar.nextId = 1;
        Rovarasz.nextId = 1;
        Tekton.nextId = 1;
    }
}
