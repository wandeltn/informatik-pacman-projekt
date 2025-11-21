import java.awt.Color;

/**
 * Klasse Hindernis
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
class Hindernis extends Figur
{
   
    /**
     * Legt das Aussehen des Hindernisses an
     * @param x x-Koordinate des Hindernisses
     * @param y y-Koordinate des Hindernisses
     * @param richtung Blickrichtung des Hindernisses
     */
    Hindernis(int x, int y, char richtung)
    {
        super();
        
        PositionSetzen(x,y);
        RichtungSetzen(richtung);
    }
    
    Hindernis(int x, int y, char richtung, Color color)
    {
        super();
        PositionSetzen(x,y);
        RichtungSetzen(richtung);
    }

    /**
     * Bewegt das Hindernis
     */
    @Override void AktionAusführen()
    {
        Bewegen();
    }
    
    /**
     * Tasten werden ignoriert
     * @param taste die gedrückte Taste
     */
    @Override void TasteGedrückt (char taste)
    {
    }
    
    /**
     * Sondertasten werden ignoriert
     * @param taste die gedrückte Taste
     */
    @Override void SonderTasteGedrückt (int taste)
    {
    }
    
    /**
     * Lässt das Hindernis um 5 Einheiten gehen
     */
    void Bewegen()
    {
        Gehen(5);
    }
    
    /**
     * Legt Form und Größe des Hindernisses fest
     */
    
    
    /**
     * Setzt die Blickrichtuing des Hindernisses
     * @param richtung Blickrichtung des Hindernisses
     */
    void RichtungSetzen(char richtung)
    {
        if (richtung == 'O')
        {
            //nichts
        }
        else
        {
            if (richtung == 'N')
            {
                Drehen(90);
            }
            else
            {
                if (richtung == 'W')
                {
                    Drehen(180);
                }
                else
                {
                    if (richtung == 'S')
                    {
                        Drehen(270);
                    }
                    else
                    {
                        System.out.println("Fehlerhafte Richtung in Konstruktor von Hindernis");
                    }
                }
            }
        }
    }
    
    /**
     * Entfernt das Hindernis, wenn es die Zeichenfläche verlässt
     */
    void EntfernenWennAußerhalb()
    {
        if((XPositionGeben() < -100) || (XPositionGeben() > 900) || (YPositionGeben() < -100) || (YPositionGeben() > 700))
        {
            Entfernen();
        }
    }
}
