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
    
    public ColorRGB(String colorString)
    {
        this(
            calculateR(colorString), 
            calculateG(colorString), 
            calculateB(colorString)
        );
    }
    
    
    // Helper methods to calculate the component values
    private static int calculateR(String colorString) {
        return switch (colorString.toLowerCase()) {
            case "rot" -> 255;
            case "blau" -> 0;
            default -> 250;
        };
    }

    private static int calculateG(String colorString) {
        return switch (colorString.toLowerCase()) {
            case "rot" -> 0;
            case "blau" -> 0;
            default -> 5;
        };
    }
    
    private static int calculateB(String colorString) {
        return switch (colorString.toLowerCase()) {
            case "rot" -> 0;
            case "blau" -> 255;
            default -> 176;
        };
    }

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