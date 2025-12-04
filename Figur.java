import java.awt.Color;

/**
 * Wrapperklasse für die Turtle auf der Zeichenfläche.
 * 
 * @author Albert Wiedemann 
 * @version 1.0
 */
public class Figur
{
    /** x-Position der Figur. */
    private int x;
    /** y-Position der Figur. */
    private int y;
    /** Größe der Figur. */
    private int größe;
    /** Farbe der Figur. */
    private String farbe;
    /** Sichtbarkeit der Figur. */
    private boolean sichtbar;
    /** Drehwinkel (mathemtisch positiver Drehsinn) der Turtle in Grad. */
    private int winkel;
    /** Referenz auf das echte Figursymbol. */
    Zeichenfenster.FigurIntern symbol;
    /** Referenz auf das Aktionsempfängerobjekt. */
    Zeichenfenster.AktionsEmpfaenger aktionsEmpfänger;
    
    /**
     * Konstruktor der Figur
     */
    Figur ()
    {
        symbol = (Zeichenfenster.FigurIntern) Zeichenfenster.SymbolErzeugen(Zeichenfenster.SymbolArt.figur);
        symbol.SichtbarkeitSetzen(true);
        x = symbol.x;
        y = symbol.y;
        winkel = symbol.winkel;
        größe = symbol.b;
        sichtbar = symbol.sichtbar;
        aktionsEmpfänger = new Zeichenfenster.AktionsEmpfaenger()
        {
            public void Ausführen () { AktionAusführen(); }
            public void Taste (char taste) { TasteGedrückt(taste); }
            public void SonderTaste (int taste) { SonderTasteGedrückt(taste); }
            public void Geklickt (int x, int y, int anzahl) { MausGeklickt(x, y, anzahl); }
        };
        Zeichenfenster.AktionsEmpfängerEintragen(aktionsEmpfänger);
    }
    
    void AktionAusführen() {}
    void TasteGedrückt (char taste) {}
    void SonderTasteGedrückt (int taste) {}
    void MausGeklickt (int x, int y, int anzahl) {}
    
    void PositionSetzen(int x, int y)
    {
        this.x = x;
        this.y = y;
        symbol.PositionSetzen(x, y);
    }
        
    void GrößeSetzen (int größe)
    {
        this.größe = größe;
        symbol.GrößeSetzen(größe, größe);
    }
        
    void WinkelSetzen (int winkel)
    {
        this.winkel = winkel;
        symbol.WinkelSetzen(winkel);
    }
    
    void SichtbarkeitSetzen (boolean sichtbar)
    {
        this.sichtbar = sichtbar;
        symbol.SichtbarkeitSetzen(sichtbar);
    }
        
    void Entfernen () { Zeichenfenster.AktionsEmpfängerEntfernen(aktionsEmpfänger); symbol.Entfernen(); }
    void NachVornBringen () { symbol.NachVornBringen(); }
    void GanzNachVornBringen () { symbol.GanzNachVornBringen(); }
    void NachHintenBringen () { symbol.NachHintenBringen(); }
    void GanzNachHintenBringen () { symbol.GanzNachHintenBringen(); }
    void ZumStartpunktGehen() { symbol.ZumStartpunktGehen(); x = symbol.x; y = symbol.y; winkel = symbol.winkel; }
    void Gehen(double länge) { symbol.Gehen(länge); x = symbol.x; y = symbol.y; }
    void Drehen(int grad) { symbol.Drehen(grad); winkel = symbol.winkel; }
    int WinkelGeben() { return winkel; }
    int XPositionGeben() { return x; }
    int YPositionGeben() { return y; }
    boolean Berührt () { return symbol.Berührt(); }
    boolean Berührt (String farbe) { return symbol.Berührt(farbe); }
    boolean Berührt (Object objekt) { return symbol.Berührt(objekt); }
    
    void FigurteilFestlegenRechteck (int x, int y, int breite, int höhe, String farbe) { symbol.FigurteilFestlegenRechteck(x, y, breite, höhe, farbe); }
    void FigurteilFestlegenRechteck (int x, int y, int breite, int höhe, Color color, boolean repaint) { symbol.FigurteilFestlegenRechteck(x, y, breite, höhe, color, repaint); }
    void FigurteilFestlegenRechteck (int x, int y, int breite, int höhe, Color color) { symbol.FigurteilFestlegenRechteck(x, y, breite, höhe, color); }
    void FigurteilFestlegenEllipse (int x, int y, int breite, int höhe, String farbe) { symbol.FigurteilFestlegenEllipse(x, y, breite, höhe, farbe); }
    void FigurteilFestlegenEllipse (int x, int y, int breite, int höhe, Color farbe) { symbol.FigurteilFestlegenEllipse(x, y, breite, höhe, farbe); }
    void FigurteilFestlegenDreieck (int x1, int y1, int x2, int y2, int x3, int y3, String farbe) { symbol.FigurteilFestlegenDreieck(x1, y1, x2, y2, x3, y3, farbe); }
    void EigeneFigurLöschen() { symbol.EigeneFigurLöschen(); }
}
