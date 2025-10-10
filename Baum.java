
/**
 * Beschreiben Sie hier die Klasse Baum.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */


public class Baum extends Hindernis
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int x;

    /**
     * Konstruktor für Objekte der Klasse Baum
     */
    public Baum(int x, int y, char richtung)
    {
        super(x, y, richtung);
        // Instanzvariable initialisieren
        x = 0;
    }

    /**
    /**
     * Lässt das Hindernis um 20 Einheiten gehen
     */
    @Override void Bewegen()
    {
        Gehen(20);
        EntfernenWennAußerhalb();
    }

    /**
     * Legt Form und Größe des Hindernisses fest
     */
    @Override void Zeichnen()
    {
        super.EigeneFigurLöschen();

        FigurteilFestlegenRechteck(-50,-20,170,50,"schwarz");
        FigurteilFestlegenEllipse(-50, -20, 50, 50, "rot");
        FigurteilFestlegenEllipse(10, -20, 50, 50, "gelb");
        FigurteilFestlegenEllipse(70, -20, 50, 50, "grün");
    }
}