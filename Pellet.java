class Pellet extends Figur
{
    private boolean eingesammelt = false;

    Pellet()
    {
        super();

        
        FigurteilFestlegenEllipse(-5, -5, 10, 10, "Gelb");
    }

    @Override void AktionAusführen()
    {
        if (!eingesammelt)
        {
            
            if (Berührt(new PacmanDummy()))
            {
                eingesammelt = true;

                
                SichtbarkeitSetzen(false);
                Entfernen();
            }
        }
    }

    /**
     * Trick-Klasse, weil Berührt(Object) ein OBJEKT verlangt.
     * Wir können Pacman nicht direkt nutzen, daher verwenden wir eine Dummy-Klasse.
     */
    private class PacmanDummy extends Pacman { }
}
