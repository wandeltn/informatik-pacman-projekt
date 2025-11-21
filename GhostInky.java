
/**
 * Write a description of class GhostInky here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GhostInky extends BaseGhost
{
    public GhostInky(int x, int y)
    {
        super(x, y, new ColorRGB(0, 255, 255));

        Zeichnen();
    }

    @Override
    void Bewegen()
    {
        
    }

    @Override
    public String toString() {
        return "GhostInky []";
    }
}