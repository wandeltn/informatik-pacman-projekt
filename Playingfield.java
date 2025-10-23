
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

    /**
     * Konstruktor für Objekte der Klasse Playingfield
     */
    public Playingfield()
    {
        PositionSetzen(0,0);
        symbol.FigurteilFestlegenRechteck(0,0, 7000, 4000, "schwarz");
        
        int[][] walls = new int[56][66];
        
        walls[0][7] = 11;
        walls[27][7] = 1;
        walls[28][7] = 1;
        walls[55][7] = 12;
        walls[27][15] = 8;
        walls[28][15] = 7;
        walls[5][12] = 5;
        walls[5][15] = 8;
        walls[10][12] = 6;
        walls[10][15] = 7;
        walls[15][12] = 5;
        walls[15][15] = 8;
        walls[22][12] = 6;
        walls[22][15] = 7;
        walls[33][12] = 5;
        walls[40][12] = 6;
        walls[33][15] = 8;
        walls[40][15] = 7;
        walls[45][12] = 5;
        walls[50][12] = 6;
        walls[45][15] = 8;
        walls[50][15] = 7;
        walls[5][20] = 5;
        walls[10][20] = 6;
        walls[5][21] = 8;
        walls[10][21] = 7;
        walls[15][20] = 5;
        walls[16][20] = 6;
        walls[15][32] = 8;
        
        for (int counterY = 21; counterY < 32; counterY++){
            walls[15][counterY] = 4;
        }
        for (int counterX = 6; counterX < 10; counterX++){
            walls[counterX][21] = 3;
        }
        for (int counterX = 6; counterX < 10; counterX++){
            walls[counterX][20] = 1;
        }
        for (int counterY = 13; counterY < 15; counterY++){
            walls[50][counterY] = 2;
        }
        for (int counterY = 13; counterY < 15; counterY++){
            walls[45][counterY] = 4;
        }
        for (int counterX = 46; counterX < 50; counterX++){
            walls[counterX][15] = 3;
        }
        for (int counterX = 46; counterX < 50; counterX++){
            walls[counterX][12] = 1;
        }
        for (int counterY = 13; counterY < 15; counterY++){
            walls[40][counterY] = 2;
        }
        for (int counterY = 13; counterY < 15; counterY++){
            walls[33][counterY] = 4;
        }
        for (int counterX = 34; counterX < 40; counterX++){
            walls[counterX][15] = 3;
        }
        for (int counterX = 34; counterX < 40; counterX++){
            walls[counterX][12] = 1;
        }
        for (int counterY = 13; counterY < 15; counterY++){
            walls[22][counterY] = 2;
        }
        for (int counterY = 13; counterY < 15; counterY++){
            walls[15][counterY] = 4;
        }
        for (int counterX = 16; counterX < 22; counterX++){
            walls[counterX][15] = 3;
        }
        for (int counterX = 16; counterX < 22; counterX++){
            walls[counterX][12] = 1;
        }
        for (int counterX = 6; counterX < 10; counterX++){
            walls[counterX][15] = 3;
        }
        for (int counterY = 13; counterY < 15; counterY++){
            walls[10][counterY] = 2;
        }
        for (int counterX = 6; counterX < 10; counterX++){
            walls[counterX][12] = 1;
        }
        for (int counterY = 13; counterY < 15; counterY++){
            walls[5][counterY] = 4;
        }
        for (int counterX = 1; counterX < 27; counterX++){
            walls[counterX][7] = 10;
        }
        for (int counterX = 29; counterX < 55; counterX++){
            walls[counterX][7] = 10;
        }
        for (int counterY = 8; counterY < 15; counterY++){
            walls[27][counterY] = 4;
        }
        for (int counterY = 8; counterY < 15; counterY++){
            walls[28][counterY] = 2;
        }
        for (int counterY = 8; counterY < 26; counterY++){
            walls[0][counterY] = 9;
        }
        for (int counterY = 8; counterY < 26; counterY++){
            walls[55][counterY] = 9;
        }
        
        for (int counterY = 7; counterY < 66; counterY++){
            for (int counterX = 0; counterX < 56; counterX++){
                x = counterX * 40 + 1000;
                y = counterY * 40;
                switch (walls[counterX][counterY]){
                    case 1:
                        WandOben(x,y);
                        break;
                    case 2:
                        WandRechts(x,y);
                        break;
                    case 3:
                        WandUnten(x,y);
                        break;
                    case 4:
                        WandLinks(x,y);
                        break;
                    case 5:
                        EckeObenLinks(x,y);
                        break;
                    case 6:
                        EckeObenRechts(x,y);
                        break;
                    case 7:
                        EckeUntenRechts(x,y);
                        break;
                    case 8:
                        EckeUntenLinks(x,y);
                        break;
                    case 9:
                        DoppelWandSenkrecht(x,y);
                        break;
                    case 10:
                        DoppelWandWagerecht(x,y);
                        break;
                    case 11:
                        DoppelEckeObenLinks(x,y);
                        break;
                    case 12:
                        DoppelEckeObenRechts(x,y);
                        break;
                    case 13:
                        DoppelEckeUntenRechts(x,y);
                        break;
                    case 14:
                        DoppelEckeUntenLinks(x,y);
                        break;
                    }
            }
        }
        
    }
    

    void WandOben(int x,int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 10, "blau");
    }
    void WandRechts(int x,int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x,y, 30, 40, "schwarz");
    }
    void WandUnten(int x,int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x,y, 40, 30, "schwarz");
    }
    void WandLinks(int x,int y){
        symbol.FigurteilFestlegenRechteck(x,y, 10, 40, "blau");
    }
    void EckeObenLinks(int x,int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 10, "blau");
        symbol.FigurteilFestlegenRechteck(x,y, 10, 40, "blau");
    }
    void EckeObenRechts(int x,int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x,y + 10, 30, 30, "schwarz");
    }
    void EckeUntenRechts(int x,int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x,y, 30, 30, "schwarz");
    }
    void EckeUntenLinks(int x,int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x + 10,y, 30, 30, "schwarz");
    }
    void DoppelWandSenkrecht(int x,int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x + 10,y, 20, 40, "schwarz");
    }
    void DoppelWandWagerecht(int x,int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x,y + 10, 40, 20, "schwarz");
    }
    void DoppelEckeObenLinks(int x, int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x + 10,y + 10, 20, 30, "schwarz");
        symbol.FigurteilFestlegenRechteck(x + 10,y + 10, 30, 20, "schwarz");
    }
    void DoppelEckeObenRechts(int x, int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x + 10,y + 10, 20, 30, "schwarz");
        symbol.FigurteilFestlegenRechteck(x,y + 10, 30, 20, "schwarz");
    }
    void DoppelEckeUntenRechts(int x, int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x + 10,y, 20, 30, "schwarz");
        symbol.FigurteilFestlegenRechteck(x,y + 10, 30, 20, "schwarz");
    }
    void DoppelEckeUntenLinks(int x, int y){
        symbol.FigurteilFestlegenRechteck(x,y, 40, 40, "blau");
        symbol.FigurteilFestlegenRechteck(x + 10,y, 20, 30, "schwarz");
        symbol.FigurteilFestlegenRechteck(x + 10,y + 10, 30, 20, "schwarz");
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