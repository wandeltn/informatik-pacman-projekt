public class Pellet extends Figur
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
