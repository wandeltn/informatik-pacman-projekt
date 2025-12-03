public class Pellet extends Figur {

    public Pellet(int tileCenterX, int tileCenterY) {
        // PositionSetzen erwartet die figuren-Mittelpunkt-Koordinate
        // tileCenterX/tileCenterY sollten bereits col*10 + OFFSET_X + 5 sein
        PositionSetzen(0, 0);
        // kleines Quadrat 6x6 zentriert (linke obere Ecke rel. zum Mittelpunkt: -3,-3)

        FigurteilFestlegenEllipse(-10, -10, 20, 20, "gelb");

        FigurteilFestlegenRechteck(tileCenterX, tileCenterY, 6, 6, "gelb");

        FigurteilFestlegenRechteck(tileCenterX, tileCenterY, 6, 6, "gelb");

        NachVornBringen(); // sicherstellen, dass Pellets sichtbar vor Wänden liegen
    }
    // keine Aktion/Entfernen überschreiben -> Figur.Entfernen() wird benutzt
}
