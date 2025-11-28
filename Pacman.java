
/**
 * Verwaltet die Spielfigur
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
public class Pacman extends Figur
{
    /** Länge der Bewegung */
    int BewegungsLaenge;
    int Richtung = 1; // 0 = Hoch ; 1 = Rechts ; 2 = Runter ; 3 = Links
    boolean tot = false;
    
    public int lives = 99999;
    
    PacmanMouth Mouth = new PacmanMouth();
    Rechteck rechteck = new Rechteck();
    /**
     * Legt das Aussehen der Spielfigur fest
     */
    Pacman()
    {
        super();
        FigurteilFestlegenEllipse(-60, -60, 120, 120, "Gelb");
        BewegungsLaenge = 4;
        rechteck.FarbeSetzen("rot");
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
        if (!Mouth.PacManAnWand() && !tot) 
        {
            //Hoch
            if(Richtung == 0)
            {
                if(YPositionGeben()>0)
                {
                    PositionSetzen(XPositionGeben(),YPositionGeben()-BewegungsLaenge);
                }
            }
            // Runter
            if(Richtung == 2)
            {
                if(YPositionGeben()<Zeichenfenster.MalflächenHöheGeben()-50)
                {
                    PositionSetzen(XPositionGeben(),YPositionGeben()+BewegungsLaenge);
                }
            }
            // Links
            if(Richtung == 3)
            {
                if(XPositionGeben()>0)
                {
                    PositionSetzen(XPositionGeben()-BewegungsLaenge,YPositionGeben());
                }
            }
            // Rechts
            if(Richtung == 1)
            {
                if(XPositionGeben()<Zeichenfenster.MalflächenBreiteGeben()-50)
                {
                    PositionSetzen(XPositionGeben()+BewegungsLaenge,YPositionGeben());
                }
            }
        }
        
        if ((Berührt("Magenta") || Berührt("cyan") || Berührt("orange") || Berührt("rot")) && !tot) 
        {
            setTot(true);
        }
    }
    
    public void revive() 
    {
        if (lives > 0)
        {
            setTot(false);
            SichtbarkeitSetzen(true);
            ZumStartpunktGehen();
            lives--;
            Richtung = 1;
        }
    }
    
    public void setTot(boolean wert)
    {
        tot = wert;
        Mouth.setTot(wert);
        if (wert = true) {
            SichtbarkeitSetzen(false);
        }
    }
    
    public int getRichtung() 
    {
        return Richtung;
    }
    
    public int getXPosition()
    {
        return XPositionGeben();
    }
    
    public int getYPosition()
    {
        return YPositionGeben();
    }
    
    public int getBewegungsLaenge()
    {
        return BewegungsLaenge;
    }
}
