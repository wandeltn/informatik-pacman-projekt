
/**
 * Verwaltet die Spielfigur
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
class Pacman extends Figur
{
    /** Länge der Bewegung */
    int BewegungsLänge;
    int Richtung = 1; // 0 = Hoch ; 1 = Rechts ; 2 = Runter ; 3 = Links

    /**
     * Legt das Aussehen der Spielfigur fest
     */
    Pacman()
    {
        super();
        FigurteilFestlegenEllipse(0, 0, 120, 120, "Gelb");
        BewegungsLänge = 4;
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
                PositionSetzen(XPositionGeben(),YPositionGeben()-BewegungsLänge);
            }
        }
        // Runter
        if(Richtung == 2)
        {
            if(YPositionGeben()<Zeichenfenster.MalflächenHöheGeben()-50)
            {
                PositionSetzen(XPositionGeben(),YPositionGeben()+BewegungsLänge);
            }
        }
        // Links
        if(Richtung == 3)
        {
            if(XPositionGeben()>0)
            {
                PositionSetzen(XPositionGeben()-BewegungsLänge,YPositionGeben());
            }
        }
        // Rechts
        if(Richtung == 1)
        {
            if(XPositionGeben()<Zeichenfenster.MalflächenBreiteGeben()-50)
            {
                PositionSetzen(XPositionGeben()+BewegungsLänge,YPositionGeben());
            }
        }
    }

}
