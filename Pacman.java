
/**
 * Verwaltet die Spielfigur
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
class Pacman extends Figur
{
    /** Länge der Bewegung */
    int BewegungsLaenge;
    int Richtung = 1; // 0 = Hoch ; 1 = Rechts ; 2 = Runter ; 3 = Links
    boolean Mundoeffnen = true;
    int Mundwinkel = 0;
    
    int testwertfuerdieanimation = 0;
    /**
     * Legt das Aussehen der Spielfigur fest
     */
    Pacman()
    {
        super();
        FigurteilFestlegenEllipse(-60, -60, 120, 120, "Gelb");
        BewegungsLaenge = 4;
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
            Richtung = 0;
        }
        // Runter
        if(taste == 40)
        {
            Richtung = 2;
        }
        // Links
        if(taste == 37)
        {
            Richtung = 3;
        }
        // Rechts
        if(taste == 39)
        {
            Richtung = 1;
        }
    }

    /**
     * Bewegt die Figur.
     */
    @Override void AktionAusführen()
    {

        //Hoch
        if(Richtung == 0)
        {
            if(YPositionGeben()>0)
            {
                Animieren();
                WinkelSetzen(90);
                PositionSetzen(XPositionGeben(),YPositionGeben()-BewegungsLaenge);
            }
        }
        // Runter
        if(Richtung == 2)
        {
            if(YPositionGeben()<Zeichenfenster.MalflächenHöheGeben()-50)
            {
                Animieren();
                WinkelSetzen(270);
                PositionSetzen(XPositionGeben(),YPositionGeben()+BewegungsLaenge);
            }
        }
        // Links
        if(Richtung == 3)
        {
            if(XPositionGeben()>0)
            {
                Animieren();
                WinkelSetzen(180);
                PositionSetzen(XPositionGeben()-BewegungsLaenge,YPositionGeben());
            }
        }
        // Rechts
        if(Richtung == 1)
        {
            if(XPositionGeben()<Zeichenfenster.MalflächenBreiteGeben()-50)
            {
                Animieren();
                WinkelSetzen(0);
                PositionSetzen(XPositionGeben()+BewegungsLaenge,YPositionGeben());
            }
        }
    }
    
    void Animieren() 
    {
        if (testwertfuerdieanimation > 10)
        {
            testwertfuerdieanimation = 0;
            if (Mundoeffnen) {
                FigurteilFestlegenEllipse(-60, -60, 120, 120, "Gelb");
                FigurteilFestlegenDreieck(40-60, 60-60, 40+80-60, 60+60-60, 40+80-60, 60-60-60, "Schwarz");
                Mundoeffnen = false;
            }
            else {
                FigurteilFestlegenEllipse(0-60, 0-60, 120, 120, "Gelb");
                Mundoeffnen = true;
            }
        }
        testwertfuerdieanimation++;
    }
    
    int clamp(int min, int value, int max) 
    {
        return value;
    } 
}
