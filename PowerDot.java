public class PowerDot extends Figur {

    public PowerDot(int tileCenterX, int tileCenterY) {
        PositionSetzen(tileCenterX, tileCenterY);
        // etwas größeres Quadrat, zentriert
        FigurteilFestlegenRechteck(-5, -5, 10, 10, "orange");
        NachVornBringen();
    }
    // keine Aktion hier; Pacman entfernt das PowerDot beim Überlappen
}
