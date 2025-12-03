/**
 * Verwaltet die Spielfigur (Mund-Teil)
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
    private boolean tot = false;
    
    public int testwertfuerdieanimationen = 0;
    
    boolean AboveFree;
    boolean RightFree;
    boolean BelowFree;
    boolean LeftFree;

    // neu: speichert die aktuelle Farbe des Mundes
    private String mundFarbe = "Schwarz";
    
    /**
     * Legt das Aussehen der Spielfigur fest
     */
    public PacmanMouth()
    {
        super();
        // Zeichne das Anfangs-Dreieck mit der aktuellen Mundfarbe
        FigurteilFestlegenDreieck(40-60, 60-60, 40+80-60, 60+60-70, 40+80-60, 60-60-50, mundFarbe);
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
        if (!tot)
        {
            //Hoch
            if(taste == 38 && AboveFree)
            {
                WinkelSetzen(90);
                Richtung = 0;
            }
            // Runter
            if(taste == 40 && BelowFree)
            {
                WinkelSetzen(270);
                Richtung = 2;
            }
            // Links
            if(taste == 37 && LeftFree)
            {
                WinkelSetzen(180);
                Richtung = 3;
            }
            // Rechts
            if(taste == 39 && RightFree)
            {
                WinkelSetzen(0);
                Richtung = 1;
            }
        }
    }
    
    /**
     * Bewegt die Figur.
     */
    @Override void AktionAusführen()
    {
        if (!PacManAnWand() && !tot)
            {
            //Hoch
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
        
        else if (tot) 
        {
            totAnimieren();
        }
    }
    
    private void Animieren() 
    {
         if (testwertfuerdieanimationen > 10)// * BewegungsLaenge)
        {
            testwertfuerdieanimationen = 0;
            if (Mundoeffnen) {
                SichtbarkeitSetzen(true);
                Mundoeffnen = false;
            }
            else {
                SichtbarkeitSetzen(false);
                Mundoeffnen = true;
            }
        }
        testwertfuerdieanimationen += BewegungsLaenge;
    }
    
    private void totAnimieren()
    {
        if (testwertfuerdieanimationen == 1)
        {
            FigurteilFestlegenEllipse(-60, -60, 120, 120, "Gelb");
            SichtbarkeitSetzen(true);
        }
        else if (testwertfuerdieanimationen == 10)
        {
            FigurteilFestlegenDreieck(0, 60-60, 40+80-60, 60+60-70, 40+80-60, 60-60-50, "Schwarz");
            FigurteilFestlegenDreieck(0, 60-60, 40+80-60, 60+60-70, 40+80-60, 60-60-50, "Schwarz");
        }
        else if (testwertfuerdieanimationen == 20)
        {
            FigurteilFestlegenRechteck(0, -60, 60, 120, "Schwarz");
        }
        else if (testwertfuerdieanimationen == 30)
        {
            FigurteilFestlegenDreieck(0, 0, 0, 60, -60, 60, "Schwarz");
            FigurteilFestlegenDreieck(0, 0, 0, -60, -60, -60, "Schwarz");
        }
        else if (testwertfuerdieanimationen == 40)
        {
            FigurteilFestlegenRechteck(-60, -60, 120, 120, "Schwarz");
        }
        
        
        if (testwertfuerdieanimationen < 41)
        {
            testwertfuerdieanimationen++;
        } else 
        {
            SichtbarkeitSetzen(false);
        }
    }
    
    public void setTot(boolean wert)
    {
        if (!wert)
        {
            EigeneFigurLöschen();
            FigurteilFestlegenDreieck(40-60, 60-60, 40+80-60, 60+60-70, 40+80-60, 60-60-50, "Schwarz");
            ZumStartpunktGehen();
            Richtung = 1;
        }
        if (tot != wert) {
            testwertfuerdieanimationen = 0;
        }
        tot = wert;
    }
    
    public void setCheckers(boolean up, boolean right, boolean down, boolean left)
    {
        AboveFree = up;
        RightFree = right;
        BelowFree = down;
        LeftFree = left;
    }
    
    public boolean PacManAnWand()
    {
        return Berührt("blau");
    }

    /**
     * Setzt die Mundfarbe neu. Wird von Pacman aufgerufen, wenn PowerMode an/aus geht.
     * Die Methode löscht vorher die eigene Figurteile und zeichnet das Dreieck neu in der gewünschten Farbe.
     */
    public void setMundFarbe(String farbe)
    {
        mundFarbe = farbe;
        EigeneFigurLöschen();
        FigurteilFestlegenDreieck(40-60, 60-60, 40+80-60, 60+60-70, 40+80-60, 60-60-50, mundFarbe);
    }
}
