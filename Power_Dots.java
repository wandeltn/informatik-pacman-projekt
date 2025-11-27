public class Power_Dots extends Figur
{
    public Power_Dots()
    {
        super();
        
        FigurteilFestlegenEllipse(-20, -20, 40, 40, "Orange");
    }

    @Override
    void AktionAusführen()
    {
       
        if (Berührt("Gelb"))
        {
            Entfernen();
        }
    }
}
