import java.util.ArrayList;

public class PelletManager {

    public static ArrayList<Figur> pelletListe = new ArrayList<>();
    public static int pelletCount = 0;

    public PelletManager() {
        spawnAllPellets();
    }

    private void spawnAllPellets() {
        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();
        int offsetX = Playingfield.getOffsetX();
        int offsetY = Playingfield.getOffsetY();

        pelletListe.clear();
        pelletCount = 0;

        if (field == null) {
            System.out.println("PelletManager: playingfield == null");
            return;
        }

        for (int row = 0; row < field.size(); row++) {
            ArrayList<Integer> line = field.get(row);
            if (line == null) continue;
            for (int col = 0; col < line.size(); col++) {
                int tile = line.get(col);
                // tile top-left coords:
                int tileX = col * 10 + offsetX;
                int tileY = row * 10 + offsetY;
                // center coords for our Figuren (figuren zeichnen relativ zum Mittelpunkt)
                int centerX = tileX + 5;
                int centerY = tileY + 5;

                if (tile == 3) { // Pellet
                    Pellet p = new Pellet(centerX, centerY);
                    pelletListe.add(p);
                    pelletCount++;
                } else if (tile == 4) { // PowerDot
                    PowerDot pd = new PowerDot(centerX, centerY);
                    pelletListe.add(pd);
                    pelletCount++;
                    
                }
            }
        }

        System.out.println("PelletManager: Pellets + PowerDots gespawnt = " + pelletCount);
        // bring all to front (nochmal, sicher)
        for (Figur f : pelletListe) {
            f.NachVornBringen();
        }
    }

    public static void removePellet(Figur f) {
        // helper falls du extern entfernen willst
        if (pelletListe.remove(f)) {
            pelletCount = Math.max(0, pelletCount - 1);
        }
    }
}
