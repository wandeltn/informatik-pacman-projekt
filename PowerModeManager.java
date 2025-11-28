public class PowerModeManager
{
    private static boolean powerAktiv = false;
    private static int timer = 0;
    private static final int POWER_DAUER = 500; 

    public static boolean istPowerMode()
    {
        return powerAktiv;
    }

    public static void aktivierePowerMode()
    {
        powerAktiv = true;
        timer = POWER_DAUER;

        System.out.println("Friss du Sau!");
    }

    public static void tick()
    {
        if (powerAktiv)
        {
            timer--;

            if (timer <= 0)
            {
                deaktivierePowerMode();
            }
        }
    }

    private static void deaktivierePowerMode()
    {
        powerAktiv = false;

        System.out.println("Power entfernt du affe!");
    }
}
   