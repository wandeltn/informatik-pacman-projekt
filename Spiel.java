import java.io.*;
import java.util.Random;
/**
 * Legt das Spielszenario fest
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
class Spiel extends Ereignisbehandlung
{
    int zähler;
    
    static Pacman pacman;
    static Playingfield playingfield;
    static GhostBlinky blinky;
    
    /**
     * Legt die Spielfigur und den Zufallsgenertor an 
     */
    Spiel()
    {
        super();
        playingfield = new Playingfield();
        pacman = new Pacman();
        blinky = new GhostBlinky(100, 100);
        
        blinky.setPacmanTarget(pacman);
        
        zähler = 8;
    }

    /**
     * Legt neue Hindernisse an 
     */
    @Override void TaktImpulsAusführen ()
    {
        
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
        
    }
    
    /**
     * Erzeugt ein zufälliges Hindernis
     * @param x x-Koordinate des Hindernisses
     * @param y y-Koordinate des Hindernisses
     * @param richtung Blickrichtung des Hindernisses
     */
    void HindernisErzeugen(int x, int y, char richtung)
    {
        
    }
    
    int getPacmanPosX()
    {
        return pacman.getXPosition();
    }
    
    int getPacmanPosY()
    {
        return pacman.getYPosition();
    }
    
    int getPacmanDirection()
    {
        return pacman.getRichtung();
    }

}
