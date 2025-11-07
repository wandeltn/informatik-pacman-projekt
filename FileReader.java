import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.*;
import java.util.Scanner;
/**
 * Beschreiben Sie hier die Klasse FileReader.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class FileReader
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int x;

    /**
     * Konstruktor für Objekte der Klasse FileReader
     */
    public FileReader()
    {
        /*File myObj = new File("filename.txt");
        try (Scanner myReader = new Scanner(myObj)) {
          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
          }
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }*/
    }
    

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter für eine Methode
     * @return        die Summe aus x und y
     */
    public String ReadFile(String FilePath, int ycord)
    {
        String data = "";
        try (Stream<String> lines = Files.lines(Paths.get(FilePath))) {
            data = lines.skip(ycord).findFirst().get();
        } catch (IOException e) {
            System.out.println(e);
        }
        return data;
    }
    public boolean HasNextLine(String FilePath, int ycord)
    {
        boolean stop = false;
        try (Stream<String> lines = Files.lines(Paths.get(FilePath))) {
            if (lines.skip(ycord).findFirst().get() != "") {
                stop = false;
            } else {
                stop = true;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return stop;
    }
}