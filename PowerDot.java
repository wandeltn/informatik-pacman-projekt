public class PowerDot extends Figur
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
