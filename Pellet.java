public class Pellet extends Figur
{
    public Pellet()
    {
        super();
        // etwas kleineres Pellet
        FigurteilFestlegenEllipse(-4, -4, 8, 8, "Gelb");
    }

    @Override
    void AktionAusführen()
    {
        // Wenn dieses Pellet mit einer gelben Figur (also PacMan) überlappt, verschwinden
        if (Berührt("Gelb"))
        {
            Entfernen();
        }
    }
}

//zu