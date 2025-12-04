import GraphTraversal.GraphTraversal;
import java.util.ArrayList;

class Spiel extends Ereignisbehandlung
{
    int zähler;
    
    static Pacman pacman;
    static Playingfield playingfield;
    static GhostBlinky blinky;
    
    Spiel()
    {
        super();
        playingfield = new Playingfield();
        pacman = new Pacman();
        
        new GraphTraversal(Playingfield.getPlayingField());
        GraphTraversal.buildFullGraph();
        
        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();
        int fieldHeight = field.size();
        int fieldWidth = fieldHeight > 0 ? field.get(0).size() : 0;
        
        int centerTileX = fieldWidth / 2;
        int centerTileY = fieldHeight / 2;
        
        int spawnTileX = centerTileX;
        int spawnTileY = centerTileY;
        
        boolean centerIsWalkable = false;
        if (centerTileY >= 0 && centerTileY < field.size()) {
            ArrayList<Integer> row = field.get(centerTileY);
            if (centerTileX >= 0 && centerTileX < row.size()) {
                int val = row.get(centerTileX);
                centerIsWalkable = (val == 0 || val == 3 || val == 4);
            }
        }
        
        if (!centerIsWalkable) {
            boolean foundValid = false;
            for (int radius = 0; radius <= 20 && !foundValid; radius++) {
                for (int dx = -radius; dx <= radius && !foundValid; dx++) {
                    for (int dy = -radius; dy <= radius && !foundValid; dy++) {
                        int testX = centerTileX + dx;
                        int testY = centerTileY + dy;
                        if (GraphTraversal.isCoordinateValid(testX, testY)) {
                            spawnTileX = testX;
                            spawnTileY = testY;
                            foundValid = true;
                        }
                    }
                }
            }
        }
        
        int ghostWorldX = spawnTileX * 10 + Playingfield.getOffsetX();
        int ghostWorldY = spawnTileY * 10 + Playingfield.getOffsetY();
        
        blinky = new GhostBlinky(ghostWorldX, ghostWorldY);
        
        new PelletManager();
        
        zähler = 8;
    }

    @Override void TaktImpulsAusführen () {}

    @Override void TasteGedrückt (char taste) {}

    @Override void SonderTasteGedrückt (int taste) {}

    void HindernisErzeugen() {}

    void HindernisErzeugen(int x, int y, char richtung) {}

    int getPacmanPosX() {
        return pacman.getXPosition();
    }

    // NEU (du wolltest das!)
    int getPacmanPosY() {
        return pacman.getYPosition();
    }

    // NEU
    int getPacmanDirection() {
        return pacman.getRichtung();
    }
}
