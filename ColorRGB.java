import java.awt.Color;

/**
 * Write a description of record ColorRGB here.
 *
 * @author (your name)
 * @version (a version number or a date)
 * @param x The sample x parameter of the record
 * @param y The sample y parameter of the record
 */
public record ColorRGB(int r, int g, int b)
{
    /**
     * An extra constructor for ColorRGB records
     */
    public ColorRGB(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  z  a sample parameter for a method
     * @return    the sum of x and y and z
     */
    public int getValueRed()
    {
        // put your code here
        return r;
    }
    
    public int getValueGreen()
    {
        return g;
    }
    
    public int getValueBlue()
    {
        return b;
    }
    
    public Color toColor()
    {
        return new Color(r, g, b);
    }
}