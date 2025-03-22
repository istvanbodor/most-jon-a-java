package lab.proj.mostjonajava.model;

abstract class Tekton {
    protected int sporaSzam;
    protected int fonalakElettartama;

    public abstract Tekton ujTektonLetrehozasa();
    public abstract void ketteTores();
    public boolean vanFonalKozottuk(Tekton tekton) { return false; }
    public void fonalHozzaadasa(GombaFonal fonal) {}
    public void fonalTorlese(GombaFonal fonal) {}
    public void mindenFonalElszakadasa() {}
    public void sporaHozzaadasa(Spora spora) {}
    public void sporaTorlese(Spora spora) {}
    public void eletIdoCsokkentes() {}
    public boolean gombafonalNoveszthetoE() { return false; }
    public boolean gombatestNoveszthetoE() { return false; }
    public void rovarMozgasa(Tekton tekton) {}
    public boolean egyenlo(Tekton tekton) { return false; }
    public int getSporaSzam() { return sporaSzam; }
    public int getFonalElettartama() { return fonalakElettartama; }
}
