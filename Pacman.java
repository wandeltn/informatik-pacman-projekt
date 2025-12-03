public class Pacman extends Figur
{
    int BewegungsLaenge;
    int Richtung = 1;
    boolean tot = false;
    
    public int lives = 99999;
    
    
    PacmanMouth Mouth = new PacmanMouth();
    PacmanDirectionchecker CheckerAbove = new PacmanDirectionchecker(0); //0 = Hoch; 1 = Rechts, 2 = Runter; 3 = Links
    PacmanDirectionchecker CheckerRight = new PacmanDirectionchecker(1); //0 = Hoch; 1 = Rechts, 2 = Runter; 3 = Links
    PacmanDirectionchecker CheckerBelow = new PacmanDirectionchecker(2); //0 = Hoch; 1 = Rechts, 2 = Runter; 3 = Links
    PacmanDirectionchecker CheckerLeft = new PacmanDirectionchecker(3); //0 = Hoch; 1 = Rechts, 2 = Runter; 3 = Links
    
    boolean AboveFree = true;
    boolean RightFree = true;
    boolean BelowFree = true;
    boolean LeftFree = true;
    

    Pacman()
    {
        super();
        FigurteilFestlegenEllipse(-60, -60, 120, 120, "Gelb");
        BewegungsLaenge = 4;
    }

    @Override void TasteGedrückt(char taste) {}

    @Override void SonderTasteGedrückt(int taste)
    {
        AboveFree = !CheckerAbove.PacManAnAnWand();
        RightFree = !CheckerRight.PacManAnAnWand();
        BelowFree = !CheckerBelow.PacManAnAnWand();
        LeftFree = !CheckerLeft.PacManAnAnWand();
        Mouth.setCheckers(AboveFree, RightFree, BelowFree, LeftFree);
        if(taste == 38 && AboveFree) Richtung = 0;  // Hoch
        if(taste == 39 && RightFree) Richtung = 1;  // Rechts
        if(taste == 40 && BelowFree) Richtung = 2;  // Runter
        if(taste == 37 && LeftFree) Richtung = 3;  // Links
    }

    @Override void AktionAusführen()
    {
        if (!Mouth.PacManAnWand() && !tot)
        {
            if(Richtung == 0 && YPositionGeben()>0)
            {
                PositionSetzen(XPositionGeben(), YPositionGeben() - BewegungsLaenge);
                CheckerAbove.move(-BewegungsLaenge, false);
                CheckerRight.move(-BewegungsLaenge, false);
                CheckerBelow.move(-BewegungsLaenge, false);
                CheckerLeft.move(-BewegungsLaenge, false);
            }

            if(Richtung == 2 && YPositionGeben() < Zeichenfenster.MalflächenHöheGeben()-50)
            {
                PositionSetzen(XPositionGeben(), YPositionGeben() + BewegungsLaenge);
                CheckerAbove.move(BewegungsLaenge, false);
                CheckerRight.move(BewegungsLaenge, false);
                CheckerBelow.move(BewegungsLaenge, false);
                CheckerLeft.move(BewegungsLaenge, false);
            }

            if(Richtung == 3 && XPositionGeben()>0)
            {
                PositionSetzen(XPositionGeben() - BewegungsLaenge, YPositionGeben());
                CheckerAbove.move(-BewegungsLaenge, true);
                CheckerRight.move(-BewegungsLaenge, true);
                CheckerBelow.move(-BewegungsLaenge, true);
                CheckerLeft.move(-BewegungsLaenge, true);
            }

            if(Richtung == 1 && XPositionGeben() < Zeichenfenster.MalflächenBreiteGeben()-50)
            {
                PositionSetzen(XPositionGeben() + BewegungsLaenge, YPositionGeben());
                CheckerAbove.move(BewegungsLaenge, true);
                CheckerRight.move(BewegungsLaenge, true);
                CheckerBelow.move(BewegungsLaenge, true);
                CheckerLeft.move(BewegungsLaenge, true);
            }
        }
        
        if ((Berührt("Magenta") || Berührt("cyan") || Berührt("orange") || Berührt("rot")) && !tot) 
        {
            setTot(true);
        }
    }

    public void setFarbe (String Farbe)
    
    {
        
    checkPelletCollision();

    {
        FigurteilFestlegenEllipse(-60, -60, 120, 120, "Orange");
        
        //muss noch getestes werden Niklas, grrrrr gakluabe aber klappt)
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
    
    public void checkPelletCollision() {

    for (int i = 0; i < PelletManager.pelletListe.size(); i++) {

        Figur f = PelletManager.pelletListe.get(i);

        if (this.Berührt(f)) {

            f.Entfernen();
            PelletManager.pelletListe.remove(i);
            PelletManager.pelletCount--;

            System.out.println("Pellet eingesammelt. Übrig: " + PelletManager.pelletCount);

            if (PelletManager.pelletCount == 0) {
                System.out.println("GEWONNEN!");
            }

            break;
        }
    }
}


    public int getRichtung() 
    {
       return Richtung;
    }
    
    public int getXPosition()
    {
       return XPositionGeben();
    }
    
    public int getYPosition()
    {
       return YPositionGeben();
    }
   
    public int getBewegungsLaenge()
    {
       return BewegungsLaenge;
    }
    
}