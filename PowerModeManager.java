public class PowerModeManager
{
    private static boolean aktiv = false;
    private static int timer = 0;
    private static final int DAUER = 500;

    private static Pacman pacmanRef;

    public static boolean istPowerMode()
    {
        return aktiv;
    }

    public static void aktivierePowerMode(Pacman p)
    {
        aktiv = true;
        timer = DAUER;
        pacmanRef = p;

        p.aktivierePowerMode();

        System.out.println("POWER MODE AKTIV!");
    }

    public static void tick()
    {
        if (!aktiv) return;

        timer--;

        if (timer <= 0)
        {
            aktiv = false;
            if (pacmanRef != null)
            {
                pacmanRef.deaktivierePowerMode();
            }

            System.out.println("POWER MODE AUS!");
        }
    }
}
