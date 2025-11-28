    
/**
 * Verwaltet die Spielfigur
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
public class PacmanDirectionchecker extends Figur
{ 
    /**
     * Legt das Aussehen der Spielfigur fest
     */
    public PacmanDirectionchecker(int wert)
    {
        super();
        FigurteilFestlegenRechteck(70, -50, 20, 100, "Weiß");
        Drehen(90*(1-wert));
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
    }
    
    /**
     * Bewegt die Figur.
     */
    @Override void AktionAusführen()
    {
    }
    
    public void move(int BewegungsLaenge, boolean Horizontal) // false = Vertikal ; true = Horizontal
    {
        if (Horizontal) PositionSetzen(XPositionGeben() + BewegungsLaenge, YPositionGeben());
        if (!Horizontal) PositionSetzen(XPositionGeben(),YPositionGeben() + BewegungsLaenge);
    }
    
    public boolean PacManAnAnWand()
    {
        return Berührt("blau");
    }
}
