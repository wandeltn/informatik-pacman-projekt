public class Pellet_Anzeige extends Figur
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
        gewonnenText.FigurteilFestlegenRechteck(-500, -200, 1000, 400, "Schwarz");
        gewonnenText.FigurteilFestlegenRechteck(-480, -180, 960, 360, "Weiß");

        
        gewonnenText.FigurteilFestlegenRechteck(-450, -50, 900, 100, "Gelb");
        gewonnenText.PositionSetzen(
            Zeichenfenster.MalflächenBreiteGeben()/2 ,
            Zeichenfenster.MalflächenHöheGeben()/2
        );

        
    }
}
