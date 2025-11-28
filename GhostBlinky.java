import Logger.*;
/**
 * Write a description of class GhostInky here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GhostBlinky extends BaseGhost {

    private Pacman pacmanRef; // target reference
    private int retargetIntervalTicks = 10; // how often to recompute target
    private int tickCounter = 0;

    public GhostBlinky(int x, int y) {
        super(x, y, new ColorRGB(255, 0, 0));
        Zeichnen();
        initPathfinding();
        Logger.log("Blinky spawned at (" + XPositionGeben()/10 + "," + YPositionGeben()/10 + ")", LogLevel.INFO);
    }

    public void setPacmanTarget(Pacman pacman) {
        this.pacmanRef = pacman;
        Logger.log("Blinky target set to Pacman", LogLevel.DEBUG);
    }

    @Override
    void Bewegen() {
        // Regular figure housekeeping
        if (Berührt("blau")) {
            Logger.log("Touching something, removing self, temporarily disabled", LogLevel.DEBUG);
            // EigeneFigurLöschen();
            //return;
        }

        if (pacmanRef != null) {
            tickCounter++;
            if (tickCounter % retargetIntervalTicks == 0 || pathFinished()) {
               // int pacTileX = pacmanRef.getXPosition() / 10 - Playingfield.getOffsetX();
               // int pacTileY = pacmanRef.getYPosition() / 10 - Playingfield.getOffsetY();
                // Update target and (re)compute path
                //setTarget(pacTileX, pacTileY);
                //Logger.log("Blinky retarget Pacman tile (" + pacTileX + "," + pacTileY + ")", LogLevel.TRACE);
            }
            // Smooth movement toward current path
            stepAlongPath();
        }
    }

    @Override
    public String toString() {
        return "GhostBlinky";
    }
}