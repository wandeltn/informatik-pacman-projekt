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
    
    static ArrayList<ArrayList<Integer>> playingfield;
    
    ColorRGB WandFarbe;
    ColorRGB HintergrundFarbe;
    /**
     * Konstruktor für Objekte der Klasse Playingfield
     */
    Playingfield()
    {
        long start = System.currentTimeMillis();

        ArrayList<Integer> data;
        
        PositionSetzen(0,0);
        playingfield = LoadPlayingFieldFromFile();
        
        
        y = 0;
        while (y <= playingfield.size() - 1) {
            data = playingfield.get(y);
            System.out.println("Data.length" + data.size());
            if (data.size() <= 0)
            {
                Logger.log("Skipping Row, no values to process: " + y, LogLevel.DEBUG);
            }
            for(int Spalte = 0; Spalte <= data.size() - 1; Spalte++) {
                Logger.log("Processing field data at index: " + Spalte, LogLevel.DEBUG);
                int currentChar = data.get(Spalte);
                Logger.log("Current character: " + (int)currentChar, LogLevel.DEBUG);
                int nextCharcord = Spalte + 1;
                Logger.log("Next character index: " + nextCharcord, LogLevel.DEBUG);
                if (nextCharcord >= data.size()){
                    Logger.log("Skipping read because index out of range", LogLevel.DEBUG);
                    continue;
                }
                int nextChar = data.get(nextCharcord);
                int länge = 1;
                x = Spalte * 10 + 1000;
                Zahl = currentChar;
                
                
                if (Zahl == 0)
                {
                    Logger.log("Skipping tile checks, no valid tile found", LogLevel.DEBUG);
                    continue;
                } else if (Zahl < 0)
                {
                    Logger.log("Using move-ahead tip for n tiles: " + ((Zahl + 1) * -1), LogLevel.DEBUG);
                    Spalte = Spalte + (Zahl +1) * -1;
                    continue;
                }
                
                
                // Check rows below for same value
                int numRowsBelow = getVerticalWallHeight(Spalte, y);
                Logger.log("Possible to draw vertical Pixel with hight: " + numRowsBelow, LogLevel.DEBUG);
                
                if (numRowsBelow > 1)
                {
                    Logger.log("Taking opportunity for vertical optimize", LogLevel.SUCCESS);
                    switch (Zahl){
                    case 0:
                        break;
                    case 1:
                        Pixel(x,y * 10 + 70,10, numRowsBelow * 10);
                        break;
                    case 2:
                        spawntür(x,y * 10 + 70,10, numRowsBelow * 10);
                        break;
                }
                }
                
                
                while (nextChar == Zahl && Spalte < data.size()) {
                    länge++;
                    Spalte++;
                    Logger.log("Checking field data forward to index: " + (Spalte + 1), LogLevel.TRACE);
                    // currentChar = data.get(Spalte);
                    nextCharcord = Spalte + 1;
                    if (nextCharcord >= data.size()){
                        break;
                    }                            
                    nextChar = data.get(nextCharcord); 
                    Logger.log("About to check equality of inital: " + Zahl + " against: " + nextChar, LogLevel.DEBUG);
                }
                länge = (länge) * 10;
                switch (Zahl){
                    case 0:
                        Logger.log("Invalid Tile found during render switch", LogLevel.WARN);
                        break;
                    case 1:
                        Pixel(x,y * 10 + 70,länge, 10);
                        break;
                    case 2:
                        spawntür(x,y * 10 + 70,länge, 10);
                        break;
                }
                // }
            }
            y++;
        }
        
        NachVornBringen();
        spawntür(0, 0, 1, 10);
        
        long end = System.currentTimeMillis();
        
        long timeElapsed = end - start;
        
        Logger.log("Added Rendered whole Playingfield in " + timeElapsed + "ms", LogLevel.SUCCESS);
        
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
    
    int getVerticalWallHeight(int x, int y)
    {
        Logger.log("getVerticalWallHeight(int, int) called with x: " + x + " y: " + y, LogLevel.TRACE);
        
        int checkValue = playingfield.get(y).get(x);
        int nextValue;
        int currentIndex = 1;
        
        Logger.log("Initial Value to check against during vertical optimizer: " + checkValue, LogLevel.DEBUG);
        
        // Skip checking of 0 tiles, no tile will be places anyway
        if (checkValue == 0)
        {
            Logger.log("Skipping, no valid tile found", LogLevel.DEBUG);
            return 1;
        }
        
        // also set first value to 0
        playingfield.get(y).set(x, 0);
        
        if (y + currentIndex <= playingfield.size() - 1 && x <= playingfield.get(y + currentIndex).size() - 1)
        {
            nextValue = playingfield.get(y + currentIndex++).get(x);
        } else
        {
            Logger.log("Skipping vertical optimizer, no match with tile immediatly below", LogLevel.DEBUG);
            return 1;
        }
    
        while (
            y + currentIndex <= playingfield.size() - 1 && 
            nextValue == checkValue && 
            x <= playingfield.get(y + currentIndex).size() - 1)
        {
            Logger.log("Checking forward vertical for y: " + (y + currentIndex), LogLevel.DEBUG);
            Logger.log("Length of data at vertical check: " + playingfield.get(y + currentIndex).size(), LogLevel.TRACE);
            Logger.log("Field Row to be checked: " + playingfield.get(y + currentIndex), LogLevel.TRACE);
            // prevent double drawing by deleting values in checked places
            
            nextValue = playingfield.get(y + currentIndex).get(x);
            playingfield.get(y + currentIndex++).set(x, 0);
            
            Logger.log("About to check equality of inital: " + checkValue + " against: " + nextValue, LogLevel.DEBUG);
        }
        
        Logger.log("Returning vertical optimization of n tiles: " + (currentIndex -1), LogLevel.TRACE);        
        return currentIndex - 1;
    }
    
    
    ArrayList<ArrayList<Integer>> LoadPlayingFieldFromFile()
    {
        String data;
        
        ArrayList<ArrayList<Integer>> field = new ArrayList<ArrayList<Integer>>();
        field.add(new ArrayList<Integer>());          
        
        File myObj = new File("./Level 1.txt");
        int Zeile = 0;
        boolean skip = false;
        
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                Logger.log("Data.length: " + data.length(), LogLevel.TRACE);
                int firstZeroIndex = 0;
                int currentZeroIndex = 0;
                int Spalte = 0;
                for(; Spalte <= data.length() - 1; Spalte++) {
                    if (data.toLowerCase().contains("color")) {
                        WandFarbe = new ColorRGB(data.toLowerCase().split(":")[1]);
                        Logger.log("Set Wandfarbe to: " + WandFarbe.toString(), LogLevel.INFO);
                        skip = true;
                        break;
                    } else if (data.toLowerCase().contains("background")) {
                        HintergrundFarbe = new ColorRGB(data.toLowerCase().split(":")[1]);
                        Logger.log("Set Hintergrundfarbe to:" + HintergrundFarbe.toString(), LogLevel.INFO);
                        skip = true;
                        break;
                    } else {
                        Logger.log("Processing field data at index: " + Spalte, LogLevel.DEBUG);
                        char currentChar = data.charAt(Spalte);
                        int currentNumber = currentChar - '0';
                        field.get(Zeile).add(currentNumber);
                        Logger.log("Current character: " + currentChar, LogLevel.DEBUG);
                        
                        if (currentNumber == 0)
                        {
                            currentZeroIndex--;
                            if (firstZeroIndex == 0)
                            {
                                firstZeroIndex = Spalte;
                            }
                        }
                        else if (currentZeroIndex < 0)
                        {
                            field.get(Zeile).set(firstZeroIndex, currentZeroIndex);
                            
                            currentZeroIndex = 0;
                            firstZeroIndex = 0;
                        }
                    }
                }
                if (!skip || Spalte >= data.length() - 1)
                {
                    ++Zeile;
                    field.add(new ArrayList<Integer>());  
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
        
        // Fill incomplete rows for consitent lenth
        for (int i = 0; i < field.size(); i++)
        {
            ArrayList<Integer> row = field.get(i);
            while (row.size() < field.get(i).size())
            {
                row.add(0);
            }
        }
        
        
        return field;
    }
    
   
   
   
    void Pixel(int x, int y, int länge, int höhe){
        long start = System.currentTimeMillis();
        symbol.FigurteilFestlegenRechteck(x,y,länge, höhe, WandFarbe.toColor(), false);
        long end = System.currentTimeMillis();
        
        long timeElapsed = end - start;
        
        System.out.println("Added Pixel in " + timeElapsed + "ms");
    }
    void spawntür(int x, int y, int länge, int höhe){
        symbol.FigurteilFestlegenRechteck(x,y,länge, höhe, "rot");
    }
    
    static ArrayList<ArrayList<Integer>> getPlayingField()
    {
        return playingfield;
    }
}