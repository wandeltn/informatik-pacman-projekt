public class Pellet extends Figur
{
    public Pellet()
    {
        super();
        FigurteilFestlegenEllipse(-4, -4, 8, 8, "Gelb");

        
        Pellet_Anzeige.pelletErzeugt();
    }

    @Override
    void AktionAusführen()
    {
        if (Berührt("Gelb"))  
        {
            Pellet_Anzeige.pelletEntfernt();
            Entfernen();
        }
    }
}
