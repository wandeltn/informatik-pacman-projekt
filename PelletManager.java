/* public class Pellet_Anzeige extends Figur
{
    private static int pelletCount = 0;
    private static boolean gewonnenAngezeigt = false;

    public static void pelletErzeugt()
    {
        pelletCount++;
    }

    public static void pelletEntfernt()
    {
        pelletCount--;
        if (pelletCount <= 0 && !gewonnenAngezeigt)
        {
            gewonnenAnzeigen();
        }
    }

    private static void gewonnenAnzeigen()
    {
        gewonnenAngezeigt = true;

        
        Figur gewonnenText = new Figur();
        gewonnenText.FigurteilFestlegenRechteck(-500, -200, 1000, 400, "Blau");
        gewonnenText.FigurteilFestlegenRechteck(-480, -180, 960, 360, "Weiß");

        
        gewonnenText.FigurteilFestlegenRechteck(-450, -50, 900, 100, "Gelb");
        gewonnenText.PositionSetzen(
            Zeichenfenster.MalflächenBreiteGeben()/2 ,
            Zeichenfenster.MalflächenHöheGeben()/2
        );

        
    }
}
*/
import java.util.ArrayList;

public class PelletManager {

    public static int pelletCount = 0;
    public static ArrayList<Figur> pelletListe = new ArrayList<>();

    public PelletManager() {
        spawnAllPellets();
    }

    private void spawnAllPellets() {

        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();

        int offsetX = Playingfield.getOffsetX();
        int offsetY = Playingfield.getOffsetY();

        for (int row = 0; row < field.size(); row++) {
            ArrayList<Integer> line = field.get(row);

            for (int col = 0; col < line.size(); col++) {
                int tile = line.get(col);

                int x = col * 10 + offsetX;
                int y = row * 10 + offsetY;

                Figur f = null;

                if (tile == 3) {
                    f = new Pellet(x, y);
                } else if (tile == 4) {
                    f = new PowerDot(x, y);
                }

                if (f != null) {
                    pelletListe.add(f);
                    pelletCount++;
                }
            }
        }

        System.out.println("Pellets + PowerDots gespawnt: " + pelletCount);
    }
}