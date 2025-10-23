
/**
 * Verwaltet die Spielfigur
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
class Pacman extends Figur
{
    /** Länge der Bewegung */
    int delta;
    /** Anzeigetext */
    Text t;

    /**
     * Legt das Aussehen der Spielfigur fest
     */
    Pacman()
    {
        super();
        FigurteilFestlegenEllipse(0, 0, 140, 140, "Gelb");
        delta = 4;
        t = null;
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
            if(YPositionGeben()>0)
            {
                PositionSetzen(XPositionGeben(),YPositionGeben()-delta);
            }
        }
        // Runter
        if(taste == 40)
        {
            if(YPositionGeben()<500)
            {
                PositionSetzen(XPositionGeben(),YPositionGeben()+delta);
            }
        }
        // Links
        if(taste == 37)
        {
            if(XPositionGeben()>0)
            {
                PositionSetzen(XPositionGeben()-delta,YPositionGeben());
            }
        }
        // Rechts
        if(taste == 39)
        {
            if(XPositionGeben()<740)
            {
                PositionSetzen(XPositionGeben()+delta,YPositionGeben());
            }
        }
    }

    /**
     * Bewegt die Figur.
     */
    @Override void AktionAusführen()
    {

        if(Berührt())
        {
            if(t!=null) 
            {
                t.Entfernen();
            }
            t=new Text();
            t.TextSetzen("Don't touch");

        }
        if(t!=null)
        {
            t.TextVergrößern();
            t.Verschieben(5,5);
        }
    }

}
