public class PowerDot extends Figur {

    public PowerDot(int tileCenterX, int tileCenterY) {
        PositionSetzen(tileCenterX, tileCenterY);
        
        FigurteilFestlegenEllipse(-20, -20, 40, 40, "orange");
        NachVornBringen();
    }
    
}
