/* public class PowerDot extends Figur
{
    public PowerDot()
    {
        super();
       
        FigurteilFestlegenEllipse(-20, -20, 40, 40, "Orange");
       
    }

    @Override
    void AktionAusführen()
    {
        
        if (Berührt("Gelb"))
        {
           
            PowerModeManager.aktivierePowerMode();

            Entfernen();
        }
    }
}
*/
public class PowerDot extends Figur {

    public PowerDot(int x, int y) {
        PositionSetzen(x, y);
        GrößeSetzen(12);
        FigurteilFestlegenEllipse(-6, -6, 12, 12, "orange");
    }
}