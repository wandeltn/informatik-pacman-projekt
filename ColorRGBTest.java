

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;

/**
 * The test class ColorRGBTest.
 *
 * @author  Niklas Wandelt
 * @version 0.1.0
 */
public class ColorRGBTest
{
    ColorRGB color;
    
    /**
     * Default constructor for test class ColorRGBTest
     */
    public ColorRGBTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        this.color = new ColorRGB(0, 0, 0);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    @Test
    public void ColorRGBToColor()
    {
        assertEquals(new Color(0, 0, 0), this.color.toColor());
        
        this.color = new ColorRGB(50, 50, 50);
        
        assertEquals(new Color(50, 50, 50), this.color.toColor());
    }
}