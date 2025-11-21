
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
    
    CollisionChecker CollisionRight = new CollisionChecker();
    CollisionChecker CollisionLeft = new CollisionChecker();
    CollisionChecker CollisionTop = new CollisionChecker();
    CollisionChecker CollisionBottom = new CollisionChecker();

    
    BaseGhost(int x, int y, ColorRGB color)
    {
        super(x, y, 'O', color.toColor());
        this.color = color;
        System.out.println(color);
        System.out.println(this.color);
        
        CollisionRight.PositionSetzen(x + 140, y + 35);
        CollisionLeft.PositionSetzen(x - 1, y + 35);
        CollisionTop.PositionSetzen(x + 70, y - 1);
        CollisionBottom.PositionSetzen(x + 70, y + 140);
        
        
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
    void Zeichnen()
    {
        super.EigeneFigurLöschen();

        FigurteilFestlegenEllipse(0, 0, 140, 140, this.color.toColor());
        FigurteilFestlegenRechteck(0, 70, 140, 70, this.color.toColor());
    }

    boolean CheckCollision()
    {
        boolean collision = false;

        if (CollisionRight.CheckFieldCollision() ||
            CollisionLeft.CheckFieldCollision() ||
            CollisionTop.CheckFieldCollision() ||
            CollisionBottom.CheckFieldCollision())
        {
            collision = true;
            System.out.println("Kollision erkannt");
        }
        return collision;
    }

    boolean CheckCollision(Himmelsrichtung direction)
    {
        boolean collision = false;

        switch (direction)
        {
            case Himmelsrichtung.NORTH:
                collision = CollisionTop.CheckFieldCollision();
                break;
            case Himmelsrichtung.SOUTH:
                collision = CollisionBottom.CheckFieldCollision();
                break;
            case Himmelsrichtung.WEST:
                collision = CollisionLeft.CheckFieldCollision();
                break;
            case Himmelsrichtung.EAST:
                collision = CollisionRight.CheckFieldCollision();
                break;
        }
        return collision;
    }
}