
/**
 * Klasse Quadrat
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
class BaseGhost extends Hindernis
{

    /**
     * Legt das Aussehen und die Startposition fest
     * @param x x-Koordinate des Hindernisses
     * @param y y-Koordinate des Hindernisses
     * @param richtungNeu Blickrichtung des Hindernisses
     */
    ColorRGB color = new ColorRGB(255, 255, 255);
    
    BaseGhost(int x, int y, ColorRGB color)
    {
        super(x, y, 'O', color.toColor());
        this.color = color;
        System.out.println(color);
        System.out.println(this.color);
        
        Zeichnen();
    }

    /**
     * Lässt das Hindernis um 20 Einheiten gehen
     */
    @Override void Bewegen()
    {
        Gehen(1);
        EntfernenWennAußerhalb();
        if (Berührt())
        {
            EigeneFigurLöschen();
        }
    }

    /**
     * Legt Form und Größe des Hindernisses fest
     */
    @Override void Zeichnen()
    {
        super.EigeneFigurLöschen();

        FigurteilFestlegenEllipse(0, 0, 140, 140, this.color.toColor());
        FigurteilFestlegenRechteck(0, 70, 140, 70, this.color.toColor());
    }
}