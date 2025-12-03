public class Pellet extends Figur {

    public Pellet(int tileCenterX, int tileCenterY) {
        // PositionSetzen erwartet die figuren-Mittelpunkt-Koordinate
        // tileCenterX/tileCenterY sollten bereits col*10 + OFFSET_X + 5 sein
        PositionSetzen(0, 0);
        // kleines Quadrat 6x6 zentriert (linke obere Ecke rel. zum Mittelpunkt: -3,-3)

        FigurteilFestlegenEllipse(tileCenterX, tileCenterY, 20, 20, "gelb");

        NachVornBringen(); // sicherstellen, dass Pellets sichtbar vor Wänden liegen
    }
    
}
    // k }
  /* @Override void AktionAusführen()
   {
       if (!eingesammelt)
       {
          
           if (Berührt())
           {
              
               Figur[] figuren = AlleFigurenFinden();
               for (Figur f : figuren)
               {
                   if (f instanceof Pacman)
                   {
                       eingesammelt = true;
                       SichtbarkeitSetzen(false);
                       Entfernen();
                       break;
                   }
               }
           }
       }
   }
   private Figur[] AlleFigurenFinden()
   {
    
       return new Figur[0];
   }
// eine Aktion/Entfernen überschreiben -> Figur.Entfernen() wird benutzt
}
*/