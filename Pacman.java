public class Pacman extends Figur
{
    int BewegungsLaenge;
    int Richtung = 1;
    boolean tot = false;
    private String normaleFarbe = "Gelb";

    public int lives = 99999;

    PacmanMouth Mouth = new PacmanMouth();
    PacmanDirectionchecker CheckerAbove = new PacmanDirectionchecker(0);
    PacmanDirectionchecker CheckerRight = new PacmanDirectionchecker(1);
    PacmanDirectionchecker CheckerBelow = new PacmanDirectionchecker(2);
    PacmanDirectionchecker CheckerLeft = new PacmanDirectionchecker(3);

    boolean AboveFree = true;
    boolean RightFree = true;
    boolean BelowFree = true;
    boolean LeftFree = true;

    Pacman()
    {
        super();
        FigurteilFestlegenEllipse(-60, -60, 120, 120, normaleFarbe);
        BewegungsLaenge = 4;
    }

    @Override void TasteGedrückt(char taste) {}

    @Override void SonderTasteGedrückt(int taste)
    {
        // AboveFree = !CheckerAbove.PacManAnAnWand();
        // RightFree = !CheckerRight.PacManAnAnWand();
        // BelowFree = !CheckerBelow.PacManAnAnWand();
        // LeftFree = !CheckerLeft.PacManAnAnWand();
        // Mouth.setCheckers(AboveFree, RightFree, BelowFree, LeftFree);

        if(taste == 38 && AboveFree) Richtung = 0;
        if(taste == 39 && RightFree) Richtung = 1;
        if(taste == 40 && BelowFree) Richtung = 2;
        if(taste == 37 && LeftFree) Richtung = 3;
    }

    @Override void AktionAusführen()
    {
        PowerModeManager.tick();  
        if (!Mouth.PacManAnWand() && !tot)
        {
            if(Richtung == 0)
            {
                PositionSetzen(XPositionGeben(), YPositionGeben() - BewegungsLaenge);
                CheckerAbove.move(-BewegungsLaenge, false);
                CheckerRight.move(-BewegungsLaenge, false);
                CheckerBelow.move(-BewegungsLaenge, false);
                CheckerLeft.move(-BewegungsLaenge, false);
            }

            if(Richtung == 2)
            {
                PositionSetzen(XPositionGeben(), YPositionGeben() + BewegungsLaenge);
                CheckerAbove.move(BewegungsLaenge, false);
                CheckerRight.move(BewegungsLaenge, false);
                CheckerBelow.move(BewegungsLaenge, false);
                CheckerLeft.move(BewegungsLaenge, false);
            }

            if(Richtung == 3)
            {
                PositionSetzen(XPositionGeben() - BewegungsLaenge, YPositionGeben());
                CheckerAbove.move(-BewegungsLaenge, true);
                CheckerRight.move(-BewegungsLaenge, true);
                CheckerBelow.move(-BewegungsLaenge, true);
                CheckerLeft.move(-BewegungsLaenge, true);
            }

            if(Richtung == 1)
            {
                PositionSetzen(XPositionGeben() + BewegungsLaenge, YPositionGeben());
                CheckerAbove.move(BewegungsLaenge, true);
                CheckerRight.move(BewegungsLaenge, true);
                CheckerBelow.move(BewegungsLaenge, true);
                CheckerLeft.move(BewegungsLaenge, true);
            }
        }

        checkPelletCollision();
    }
    
    public void setFarbe(String farbe)
    {
        EigeneFigurLöschen();
        FigurteilFestlegenEllipse(-60, -60, 120, 120, farbe);
    }

    public void aktivierePowerMode()
    {
        setFarbe("Orange");
        Mouth.setMundFarbe("Schwarz");
    }

    public void deaktivierePowerMode()
    {
        setFarbe(normaleFarbe);
        Mouth.setMundFarbe("Schwarz");
    }


    public void checkPelletCollision() 
    {
        for (int i = 0; i < PelletManager.pelletListe.size(); i++)
        {
            Figur f = PelletManager.pelletListe.get(i);
    
            if (this.Berührt(f))
            {
                System.out.println("DEBUG: Pacman berührt ein Objekt: " + f.getClass().getSimpleName());
    
                if (f instanceof PowerDot)
                {
                    System.out.println("DEBUG: Es ist ein PowerDot -> aktiviere PowerMode");
                    PowerModeManager.aktivierePowerMode(this);
                }
    
                // entferne die Figur vom Feld (Figur.Entfernen existiert in deiner Figur-Klasse)
                f.Entfernen();
    
                // entferne aus Liste und decrement count
                PelletManager.pelletListe.remove(i);
                PelletManager.pelletCount = Math.max(0, PelletManager.pelletCount - 1);
    
                System.out.println("Pellet eingesammelt. Übrig: " + PelletManager.pelletCount);
    
                if (PelletManager.pelletCount == 0)
                {
                    System.out.println("GEWONNEN!");
                }
                if (PelletManager.pelletCount == 0)
                {
                    checkWinCondition();   
                }

    
                break;
            }
        }
    }
    
    public void revive() 
    {
        if (lives > 0)
        {
            setTot(false);
            SichtbarkeitSetzen(true);
            ZumStartpunktGehen();
            lives--;
            Richtung = 1;
        }
    }
    
    public void setTot(boolean wert)
    {
        tot = wert;
        Mouth.setTot(wert);
        if (wert = true) {
            SichtbarkeitSetzen(false);
        }
    }
    
    public int getXPosition()
    {
        return XPositionGeben();
    }

    public int getYPosition()
    {
        return YPositionGeben();
    }

    public int getRichtung()
    {
        return Richtung;
    }
  
    private void checkWinCondition() {
        PelletManager.checkWin();
    }

}
