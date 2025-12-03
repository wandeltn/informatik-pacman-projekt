public class PowerDot extends Figur {

    public PowerDot(int tileCenterX, int tileCenterY) {
        PositionSetzen(tileCenterX, tileCenterY);
        // etwas größeres Quadrat, zentriert
        FigurteilFestlegenEllipse(-20, -20, 40, 40, "orange");
        NachVornBringen();
    }
    // keine Aktion hier; Pacman entfernt das PowerDot beim Überlappen
}
