
import java.util.Random;
/**
 * Legt das Spielszenario fest
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
class Spiel extends Ereignisbehandlung
{
    /** Zufallsgenerator */
    Random zzgenerator;
    /** Taktzähler */
    int zähler;
    
    /**
     * Legt die Spielfigur und den Zufallsgenertor an 
     */
    Spiel()
    {
        super();
        new Pacman();
        new Playingfield();
        zähler = 8;
        zzgenerator = new Random();
    }

    /**
     * Legt neue Hindernisse an 
     */
    @Override void TaktImpulsAusführen ()
    {
        if(zähler<3)
        {
            zähler++;
        }
        else
        {
            HindernisErzeugen();
            zähler=0;
        }
    }
    
    /**
     * Tasten werden ignoriert
     * @param taste die gedrückte Taste
     */
    @Override void TasteGedrückt (char taste)
    {
    }
    
    /**
     * Sonderasten werden ignoriert
     * @param taste die gedrückte Taste
     */
    @Override void SonderTasteGedrückt (int taste)
    {
    }

    /**
     * Erzeugt ein Hindernis mit zufälliger Position und Richtung
     */
    void HindernisErzeugen()
    {
        int zufall = zzgenerator.nextInt(4);
        if (zufall == 0)
        {
            HindernisErzeugen(0,zzgenerator.nextInt(600),'O');
        }
        else
        {
            if (zufall == 1)
            {
                HindernisErzeugen(800,zzgenerator.nextInt(600),'W');
            }
            else
            {
                if (zufall == 2)
                {
                    HindernisErzeugen(zzgenerator.nextInt(800),0,'S');
                }
                else
                {
                    if (zufall == 3)
                    {
                        HindernisErzeugen(zzgenerator.nextInt(800),600,'N');
                    }
                    else
                    {
                        System.out.println("Fehler in Spiel, HindernisErzeugen()");
                    }
                }
            }
        }
    }
    
    /**
     * Erzeugt ein zufälliges Hindernis
     * @param x x-Koordinate des Hindernisses
     * @param y y-Koordinate des Hindernisses
     * @param richtung Blickrichtung des Hindernisses
     */
    void HindernisErzeugen(int x, int y, char richtung)
    {
        new BaseGhost(x, y, new ColorRGB(255, 184, 81));
    }
}
