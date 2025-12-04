import Logger.*;
import java.util.ArrayList;

public class PelletManager {

    public static ArrayList<Figur> pelletListe = new ArrayList<>();
    public static int pelletCount = 0;

    public static boolean hasWon = false;  // NEU
    public static WinMarker winMarker;     // NEU

    public PelletManager() {
        spawnAllPellets();
    }

    private static void spawnAllPellets() {
        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();
        int offsetX = Playingfield.getOffsetX();
        int offsetY = Playingfield.getOffsetY();

        pelletListe.clear();
        pelletCount = 0;
        hasWon = false;

        if (field == null) {
            System.out.println("PelletManager: playingfield == null");
            return;
        }

        for (int row = 0; row < field.size(); row++) {
            ArrayList<Integer> data = field.get(row);
            for (int col = 0; col < data.size(); col++) {
                int tile = data.get(col);
                if (tile == 3 || tile == 4) {
                    int worldX = col * 10 + offsetX;
                    int worldY = row * 10 + offsetY;

                    Figur f;
                    if (tile == 4) {
                        f = new PowerDot(worldX, worldY);
                    } else {
                        f = new Pellet(worldX, worldY);
                    }

                    pelletListe.add(f);
                }
            }
        }

        for (Figur f : pelletListe) {
            f.NachVornBringen();
            pelletCount++;
        }
        
        System.out.println("PelletManager: Pellets + PowerDots = " + pelletCount);
    }

    public static void removePellet(Figur f) {
        if (pelletListe.remove(f)) {
            pelletCount = Math.max(0, pelletCount - 1);
        }
    }

    public static void checkWin() {
        if (!hasWon && pelletCount == 0) {
            hasWon = true;

            int centerX = Zeichenfenster.MalflächenBreiteGeben() / 2;
            int centerY = Zeichenfenster.MalflächenHöheGeben() / 2;

            winMarker = new WinMarker(centerX, centerY);
            winMarker.NachVornBringen();

            System.out.println("✔ Spiel gewonnen!");
        }
    }
}
