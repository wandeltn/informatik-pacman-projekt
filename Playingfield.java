import java.io.*;
import java.util.Scanner;
/**
 * Beschreiben Sie hier die Klasse Playingfield.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Playingfield extends Figur
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int x;
    private int y;
    int Zahl;
    int ycord;
    String data;
    ColorRGB WandFarbe;
    String HintergrundFarbe;
    /**
     * Konstruktor für Objekte der Klasse Playingfield
     */
    public void playingfield()
    {
        PositionSetzen(0,0);
        int[][] walls = new int[224][264];
        
        /*File myObj = new File("./Level 1.txt");
        try (Scanner myReader = new Scanner(myObj)) {
            int ycord = 0;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                System.out.println("Data.length" + data.length());
                for(int Spalte = 0; Spalte < data.length(); Spalte++) {
                    int Zahl = 0;
                    if (ycord == 0) {
                        WandFarbe = new ColorRGB(data.toLowerCase());
                        Spalte = data.length() - 1;
                    } else if (ycord == 1) {
                        HintergrundFarbe = data.toLowerCase();
                        Spalte = data.length() -1 ;
                    } else {
                        char a = data.charAt(Spalte);
                        Zahl = a - '0';
                        walls[Spalte][ycord - 2] = Zahl;
                    }
                }
                ycord++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBounds");
            e.printStackTrace();
        }
        */
        //symbol.FigurteilFestlegenRechteck(0,0, 7000, 4000, "blau");
        FileReader myReader = new FileReader();
        data = myReader.ReadFile("./Level1", 4);
        for(int Spalte = 0; Spalte < data.length(); Spalte++) {
            char a = data.charAt(Spalte);
            Zahl = a - '0';
            walls[Spalte][ycord - 2] = Zahl;
        }
        
        
        
        for (int counterY = 0; counterY < 264; counterY++){
            for (int counterX = 0; counterX < 224; counterX++){
                x = counterX * 10 + 1000;
                y = counterY * 10 +70;
                y = counterY * 10 + 70;
                switch (walls[counterX][counterY]){
                    case 0:
                        break;
                    case 1:
                        Pixel(x,y);
                        break;
                    case 2:
                        spawntür(x,y);
                        break;
                    }
            }
        }
    }
    
    void Pixel(int x, int y){
        symbol.FigurteilFestlegenRechteck(x,y, 10, 10, WandFarbe.toColor(), false);
    }
    void spawntür(int x, int y){
        symbol.FigurteilFestlegenRechteck(x,y, 10, 10, "rot");
    }
    
    
    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    public int beispielMethode(int y)
    {
        // tragen Sie hier den Code ein
        return x + y;
    }
}