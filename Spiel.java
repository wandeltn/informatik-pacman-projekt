import GraphTraversal.GraphTraversal;
import java.util.ArrayList;

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
        
        // Initialize graph traversal with the field
        new GraphTraversal(Playingfield.getPlayingField());
        GraphTraversal.buildFullGraph();
        
        // Get actual field dimensions and calculate center
        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();
        int fieldHeight = field.size();
        int fieldWidth = fieldHeight > 0 ? field.get(0).size() : 0;
        
        // Find a good spawn location near center
        int centerTileX = fieldWidth / 2;
        int centerTileY = fieldHeight / 2;
        
        // Try exact center first, then fall back to clearance-aware search
        int spawnTileX = centerTileX;
        int spawnTileY = centerTileY;
        
        // Check if center is at least walkable (ignore clearance for spawn)
        boolean centerIsWalkable = false;
        if (centerTileY >= 0 && centerTileY < field.size()) {
            ArrayList<Integer> row = field.get(centerTileY);
            if (centerTileX >= 0 && centerTileX < row.size()) {
                int val = row.get(centerTileX);
                centerIsWalkable = (val == 0 || val == 3 || val == 4); // walkable, pellet, or power dot
            }
        }
        
        if (!centerIsWalkable) {
            // Fall back to clearance-aware search
            boolean foundValid = false;
            int foundAtRadius = -1;
            
            // Search in expanding circles around center
            for (int radius = 0; radius <= 20 && !foundValid; radius++) {
                for (int dx = -radius; dx <= radius && !foundValid; dx++) {
                    for (int dy = -radius; dy <= radius && !foundValid; dy++) {
                        int testX = centerTileX + dx;
                        int testY = centerTileY + dy;
                        if (GraphTraversal.isCoordinateValid(testX, testY)) {
                            spawnTileX = testX;
                            spawnTileY = testY;
                            foundValid = true;
                            foundAtRadius = radius;
                        }
                    }
                }
            }
            
            System.out.println("[INFO] Center tile not walkable, using clearance-aware spawn at radius " + foundAtRadius);
        } else {
            System.out.println("[INFO] Using exact center for spawn");
        }
        
        int ghostWorldX = spawnTileX * 10 + Playingfield.getOffsetX();
        int ghostWorldY = spawnTileY * 10 + Playingfield.getOffsetY();
        System.out.println("[DEBUG] Final spawn tile: (" + spawnTileX + "," + spawnTileY + ")");
        System.out.println("[DEBUG] Ghost world pos: (" + ghostWorldX + "," + ghostWorldY + ")");
        
        blinky = new GhostBlinky(ghostWorldX, ghostWorldY);
        
        new PelletManager();

        
        //blinky.setPacmanTarget(pacman);
        
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
    
    public static void main(String[] args) {
        new Spiel();
    }
 
}