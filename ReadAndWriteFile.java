import java.io.*;
import java.util.Scanner;
/**
 * Beschreiben Sie hier die Klasse ReadAndWriteFile.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class ReadAndWriteFile
{
    public static void main(String[] args)
    {
        String[] myNames = new String[10];
        try{
            BufferedReader br = new BufferedReader(new FileReader("D:/Schule/Informatik/PacMan/names.txt.txt"));
            String txt = br.readLine();
            int index = 0;
            
            while (txt != null)
            {
                myNames[index] = txt;
                txt = br.readLine();
                index++;
            }
            br.close();
        }
        catch (IOException e){
            System.out.println("Something went wrong");
        }
        
        for (int x = 0; x < myNames.length; x++){
            System.out.println(myNames[x]);
        }
        
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("reverseNames.txt"));
            for ( int x = myNames.length - 1; x >= 0; x--){
                bw.write(myNames[x]);
                bw.newLine();
            }
            bw.close();
        }
        catch (IOException e){
            System.out.println("Something went wrong");
        }
    }
    
    public void ReadFile() {
       File myObj = new File("Level 1.txt");
       try (Scanner myReader = new Scanner(myObj)) {
           while (myReader.hasNextLine()) {
               String data = myReader.nextLine();
               System.out.println(data);
           }
       } catch (FileNotFoundException e) {
           System.out.println("An error occurred.");
           e.printStackTrace();
       }
    }
    
    
    
    
}