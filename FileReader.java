import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.*;
/**
 * Beschreiben Sie hier die Klasse FileReader.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class FileReader
{
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
        String File = FilePath;
        String data = "";
        try (Stream<String> lines = Files.lines(Paths.get(File))) {
            data = lines.skip(ycord).findFirst().get();
        } catch (IOException e) {
            System.out.println(e);
        }
        return data;
    }
    public boolean HasNextLine(String FilePath, int ycord)
    {
        String File = FilePath;
        boolean stop = false;
        try (Stream<String> lines = Files.lines(Paths.get(File))) {
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