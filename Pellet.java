public class Pellet extends Figur {

    public Pellet(int tileCenterX, int tileCenterY) {
        
        PositionSetzen(0, 0);

        FigurteilFestlegenEllipse(tileCenterX, tileCenterY, 20, 20, "gelb");

        NachVornBringen(); 
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