public class Pacman extends Figur
{
    int BewegungsLaenge;
    int Richtung = 1;
    boolean tot = false;

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

        if (Berührt("Magenta") || Berührt("cyan") || Berührt("orange") || Berührt("rot"))
        {
            tot();
        }
    }

    private void tot()
    {
        tot = true;
        Mouth.setTot(true);
        Entfernen();
    }
}
