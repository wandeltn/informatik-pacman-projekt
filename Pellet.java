/* public class Pellet extends Figur
{
   private boolean eingesammelt = false;
   Pellet()
   {
       super();
       FigurteilFestlegenEllipse(-10, -10, 20, 20, "Gelb");
       Pellet_Anzeige.pelletErzeugt();
   }
  
   @Override void AktionAusführen()
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
                       Pellet_Anzeige.pelletEntfernt();
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
}
*/
public class Pellet extends Figur {

    public Pellet(int x, int y) {
        PositionSetzen(x, y);
        GrößeSetzen(6);
        FigurteilFestlegenEllipse(-3, -3, 6, 6, "gelb");
    }
}
