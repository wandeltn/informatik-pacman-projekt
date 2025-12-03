import Logger.*;
import java.util.ArrayList;

public class PelletManager {

    public static ArrayList<Figur> pelletListe = new ArrayList<>();
    public static int pelletCount = 0;

    public PelletManager() {
        spawnAllPellets();
    }

    private static void spawnAllPellets() {
        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();
        int offsetX = Playingfield.getOffsetX();
        int offsetY = Playingfield.getOffsetY();

        pelletListe.clear();
        pelletCount = 0;

        if (field == null) {
            System.out.println("PelletManager: playingfield == null");
            return;
        }

        // First pass: register all pellets BEFORE any field modification during rendering
        for (int row = 0; row < field.size(); row++) {
            ArrayList<Integer> data;
            if (row >= field.size()) break;
            data = field.get(row);
            for (int col = 0; col < data.size(); col++) {
                int tile = data.get(col);
                if (tile == 3 || tile == 4) {
                    int worldX = col * 10 + offsetX;
                    int worldY = row * 10 + offsetY;
                    pelletListe.add(new Pellet(worldX, worldY));
                    Logger.log("Registered pellet at tile (" + col + "," + row + ") -> world (" + worldX + "," + worldY + ")", LogLevel.TRACE);
                }
            }
        }

        // bring all to front (nochmal, sicher)
        for (Figur f : pelletListe) {
            f.NachVornBringen();
            pelletCount++;
        }
        
        System.out.println("PelletManager: Pellets + PowerDots gespawnt = " + pelletCount);
    }

    public static void removePellet(Figur f) {
        // helper falls du extern entfernen willst
        if (pelletListe.remove(f)) {
            pelletCount = Math.max(0, pelletCount - 1);
        }
    }
    
    // public static void registerPellet(int x, int y)
    // {
    //     pelletListe.add(new Pellet(x, y));
    // }
}
