public class WinMarker extends Figur
{
public WinMarker(int tileCenterX, int tileCenterY) {
    
        PositionSetzen(tileCenterX, tileCenterY);
        
        FigurteilFestlegenRechteck(-50, -50, 100, 100, "Grün");
        NachVornBringen();
    }
    
}
