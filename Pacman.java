public class Pacman extends Figur
{
    int BewegungsLaenge;
    int Richtung = 1;
    boolean tot = false;
//<<<<<<< HEAD

//=======
    
    public int lives = 3;
    
//>>>>>>> 461ece4898092d849a4a3a4747145aa77509374a
    PacmanMouth Mouth = new PacmanMouth();
    Rechteck rechteck = new Rechteck();

    Pacman()
    {
        super();
        FigurteilFestlegenEllipse(-60, -60, 120, 120, "Gelb");
        BewegungsLaenge = 4;
        rechteck.FarbeSetzen("rot");
    }

    @Override void TasteGedrückt(char taste) {}

    @Override void SonderTasteGedrückt(int taste)
    {
        if(taste == 38) Richtung = 0;  // Hoch
        if(taste == 40) Richtung = 2;  // Runter
        if(taste == 37) Richtung = 3;  // Links
        if(taste == 39) Richtung = 1;  // Rechts
    }

    @Override void AktionAusführen()
    {
        if (!Mouth.PacManAnWand() && !tot)
        {
            if(Richtung == 0 && YPositionGeben()>0)
                PositionSetzen(XPositionGeben(), YPositionGeben() - BewegungsLaenge);

            if(Richtung == 2 && YPositionGeben() < Zeichenfenster.MalflächenHöheGeben()-50)
                PositionSetzen(XPositionGeben(), YPositionGeben() + BewegungsLaenge);

            if(Richtung == 3 && XPositionGeben()>0)
                PositionSetzen(XPositionGeben() - BewegungsLaenge, YPositionGeben());

            if(Richtung == 1 && XPositionGeben() < Zeichenfenster.MalflächenBreiteGeben()-50)
                PositionSetzen(XPositionGeben() + BewegungsLaenge, YPositionGeben());
        }
//<<<<<<< HEAD

        if (Berührt("Magenta") || Berührt("cyan") || Berührt("orange") || Berührt("rot"))
//=======
        
        if ((Berührt("Magenta") || Berührt("cyan") || Berührt("orange") || Berührt("rot")) && !tot) 
//>>>>>>> 461ece4898092d849a4a3a4747145aa77509374a
        {
            setTot(true);
        }
    }
//<<<<<<< HEAD

 //   private void tot()
//=======
    
    public void setTot(boolean wert)
//>>>>>>> 461ece4898092d849a4a3a4747145aa77509374a
    {
        tot = wert;
        Mouth.setTot(wert);
        if (wert = true) {
            Entfernen();
        }
    }
}
