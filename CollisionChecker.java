
/**
 * Write a description of class CollisionChecker here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CollisionChecker extends Figur
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class CollisionChecker
     */
    public CollisionChecker()
    {
    }

    void SetPositionX(int x)
    {
        int y = YPositionGeben();
        
        PositionSetzen(x, y);
    }
    
    void SetPositionY(int y)
    {
        int x = XPositionGeben();
        PositionSetzen(x, y);
    }
    
    boolean CheckFieldCollision()
    {
        return false;
        // boolean collision = Berührt(new ColorRGB("blau"));
        // return collision;
    }
}