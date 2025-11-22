import java.io.*;
import java.util.ArrayList;
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
    
    ColorRGB WandFarbe;
    ColorRGB HintergrundFarbe;
    /**
     * Konstruktor für Objekte der Klasse Playingfield
     */
    Playingfield()
    {
        ArrayList<Character> data;
        
        PositionSetzen(0,0);
        ArrayList<ArrayList<Character>> field = LoadPlayingFieldFromFile();
        
        
        y = 0;
        while (y <= field.size()) {
            data = field.get(y);
            System.out.println("Data.length" + data.size());
            for(int Spalte = 0; Spalte <= data.size() - 1; Spalte++) {
                Logger.log("Processing field data at index: " + Spalte, LogLevel.DEBUG);
                char currentChar = data.get(Spalte);
                Logger.log("Current character: " + currentChar, LogLevel.DEBUG);
                int nextCharcord = Spalte + 1;
                Logger.log("Next character index: " + nextCharcord, LogLevel.DEBUG);
                if (nextCharcord >= data.size()){
                    Logger.log("Skipping read because index out of range", LogLevel.DEBUG);
                    continue;
                }
                char nextChar = data.get(nextCharcord);
                int länge = 1;
                x = Spalte * 10 + 1000;
                Zahl = currentChar - '0';
                while (nextChar == currentChar && Spalte < data.size()) {
                    länge++;
                    Spalte++;
                    Logger.log("Checking field data forward to index: " + (Spalte + 2), LogLevel.TRACE);
                    currentChar = data.get(Spalte);
                    nextCharcord = Spalte + 1;
                    if (nextCharcord >= data.size()){
                        break;
                    }                            
                    nextChar = data.get(nextCharcord); 
                }
                länge = länge * 10;
                switch (Zahl){
                    case 0:
                        break;
                    case 1:
                        Pixel(x,y * 10 + 70,länge);
                        break;
                    case 2:
                        spawntür(x,y * 10 + 70,länge);
                        break;
                }
                // }
            }
            y++;
        }
        
        NachVornBringen();
        
        /*
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
        }*/
    }
    
    
    ArrayList<ArrayList<Character>> LoadPlayingFieldFromFile()
    {
        String data;
        
        ArrayList<ArrayList<Character>> field = new ArrayList<ArrayList<Character>>();
        field.add(new ArrayList<Character>());          
        
        File myObj = new File("./Level 1.txt");
        int Zeile = 0;
        boolean skip = false;
        
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                System.out.println("Data.length" + data.length());
                for(int Spalte = 0; Spalte <= data.length() - 1; Spalte++) {
                    if (data.toLowerCase().contains("color")) {
                        WandFarbe = new ColorRGB(data.toLowerCase().split(":")[1]);
                        Logger.log("Set Wandfarbe to:" + WandFarbe.toString(), LogLevel.INFO);
                        break;
                    } else if (data.toLowerCase().contains("background")) {
                        HintergrundFarbe = new ColorRGB(data.toLowerCase().split(":")[1]);
                        Logger.log("Set Hintergrundfarbe to:" + HintergrundFarbe.toString(), LogLevel.INFO);
                        break;
                    } else {
                        Logger.log("Processing field data at index: " + Spalte, LogLevel.DEBUG);
                        char currentChar = data.charAt(Spalte);
                        field.get(Zeile).add(currentChar);
                        Logger.log("Current character: " + currentChar, LogLevel.DEBUG);
                    }
                }
                if (!skip)
                {
                    ++Zeile;
                    field.add(new ArrayList<Character>());  
                } else 
                {
                    skip = false;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBounds");
            e.printStackTrace();
        }
        System.out.println("Field loaded with " + field.size() + " rows.");
        return field;
    }
    
   
   
   
    void Pixel(int x, int y, int länge){
        long start = System.currentTimeMillis();
        symbol.FigurteilFestlegenRechteck(x,y,länge, 10, WandFarbe.toColor(), true);
        long end = System.currentTimeMillis();
        
        long timeElapsed = end - start;
        
        System.out.println("Added Pixel in " + timeElapsed + "ms");
    }
    void spawntür(int x, int y, int länge){
        symbol.FigurteilFestlegenRechteck(x,y,länge, 10, "rot");
    }
}