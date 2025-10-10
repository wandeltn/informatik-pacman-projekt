
/**
 * Beschreiben Sie hier die Klasse LöschQuadrat.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class LöschQuadrat extends Quadrat
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int x;

    /**
     * Konstruktor für Objekte der Klasse LöschQuadrat
     */
    public LöschQuadrat(int x, int y, char richtung)
    {
        super(x, y, richtung);
        // Instanzvariable initialisieren
        x = 0;
    }

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

        FigurteilFestlegenRechteck(-50,-50,100,100,"rot");
        FigurteilFestlegenRechteck(-10,-10,20,20,"schwarz");
    }
}