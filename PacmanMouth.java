
/**
 * Verwaltet die Spielfigur
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
public class PacmanMouth extends Figur
{
    /** Länge der Bewegung */
    private int BewegungsLaenge;
    private int Richtung = 1; // 0 = Hoch ; 1 = Rechts ; 2 = Runter ; 3 = Links
    private boolean Mundoeffnen = true;
    private int Mundwinkel = 0;
    
    private int testwertfuerdieanimation = 0;
    /**
     * Legt das Aussehen der Spielfigur fest
     */
    public PacmanMouth()
    {
        super();
        FigurteilFestlegenDreieck(40-60, 60-60, 40+80-60, 60+60-70, 40+80-60, 60-60-50, "Schwarz");
        BewegungsLaenge = 4;
        SichtbarkeitSetzen(false);
    }
    
    /**
     * Tasten werden ignoriert
     * @param taste die gedrückte Taste
     */
    @Override void TasteGedrückt (char taste)
    {
    }

    /**
     * Sondertasten werden ausgewertet
     * @param taste die gedrückte Sondertaste
     */
    @Override void SonderTasteGedrückt(int taste)
    {
        //Hoch
        if(taste == 38)
        {
            WinkelSetzen(90);
            Richtung = 0;
        }
        // Runter
        if(taste == 40)
        {
            WinkelSetzen(270);
            Richtung = 2;
        }
        // Links
        if(taste == 37)
        {
            WinkelSetzen(180);
            Richtung = 3;
        }
        // Rechts
        if(taste == 39)
        {
            WinkelSetzen(0);
            Richtung = 1;
        }
    }
    
    /**
     * Bewegt die Figur.
     */
    @Override void AktionAusführen()
    {
        //Hoch
        if (!PacManAnWand())
            {
            if(Richtung == 0)
            {
                if(YPositionGeben()>0)
                {
                    Animieren();
                    PositionSetzen(XPositionGeben(),YPositionGeben()-BewegungsLaenge);
                }
            }
            // Runter
            if(Richtung == 2)
            {
                if(YPositionGeben()<Zeichenfenster.MalflächenHöheGeben()-50)
                {
                    Animieren();
                    PositionSetzen(XPositionGeben(),YPositionGeben()+BewegungsLaenge);
                }
            }
            // Links
            if(Richtung == 3)
            {
                if(XPositionGeben()>0)
                {
                    Animieren();
                    PositionSetzen(XPositionGeben()-BewegungsLaenge,YPositionGeben());
                }
            }
            // Rechts
            if(Richtung == 1)
            {
                if(XPositionGeben()<Zeichenfenster.MalflächenBreiteGeben()-50)
                {
                    Animieren();
                    PositionSetzen(XPositionGeben()+BewegungsLaenge,YPositionGeben());
                }
            }
        }
    }
    
    private void Animieren() 
    {
         if (testwertfuerdieanimation > 10)// * BewegungsLaenge)
        {
            testwertfuerdieanimation = 0;
            if (Mundoeffnen) {
                SichtbarkeitSetzen(true);
                Mundoeffnen = false;
            }
            else {
                SichtbarkeitSetzen(false);
                Mundoeffnen = true;
            }
        }
        testwertfuerdieanimation += BewegungsLaenge;
    }
    
    public boolean PacManAnWand()
    {
        return Berührt("rot");
    }
}
