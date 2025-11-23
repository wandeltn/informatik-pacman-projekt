import GraphTraversal.AStar;
import GraphTraversal.GraphTraversal;
import GraphTraversal.Node;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse Quadrat
 * 
 * @author Klaus Reinold 
 * @version 1.0
 */
class BaseGhost extends Hindernis
{
    // Pathfinding state
    private List<Node> currentPath = new ArrayList<>();
    private int pathIndex = 0;
    private int targetX = -1;
    private int targetY = -1;
    private long lastPathComputeNs = 0L;
    private int movementSpeed = 2; // pixels per update toward next waypoint
    private boolean pathBlocked = false;

    /**
     * Legt das Aussehen und die Startposition fest
     * @param x x-Koordinate des Hindernisses
     * @param y y-Koordinate des Hindernisses
     * @param richtungNeu Blickrichtung des Hindernisses
     */
    ColorRGB color = new ColorRGB(255, 255, 255);
    
    CollisionChecker CollisionRight = new CollisionChecker();
    CollisionChecker CollisionLeft = new CollisionChecker();
    CollisionChecker CollisionTop = new CollisionChecker();
    CollisionChecker CollisionBottom = new CollisionChecker();

    GraphTraversal graphTraversal;

    
    BaseGhost(int x, int y, ColorRGB color)
    {
        super(x, y, 'O', color.toColor());
        this.color = color;
        System.out.println(color);
        System.out.println(this.color);
        
        CollisionRight.PositionSetzen(x + 140, y + 35);
        CollisionLeft.PositionSetzen(x - 1, y + 35);
        CollisionTop.PositionSetzen(x + 70, y - 1);
        CollisionBottom.PositionSetzen(x + 70, y + 140);
        
        
        Zeichnen();
    }

    /**
     * Lässt das Hindernis um 20 Einheiten gehen
     */
    @Override void Bewegen()
    {
        Gehen(1);
        EntfernenWennAußerhalb();
        if (Berührt())
        {
            EigeneFigurLöschen();
        }
    }

    /**
     * Legt Form und Größe des Hindernisses fest
     */
    void Zeichnen()
    {
        super.EigeneFigurLöschen();

        FigurteilFestlegenEllipse(0, 0, 140, 140, this.color.toColor());
        FigurteilFestlegenRechteck(0, 70, 140, 70, this.color.toColor());
    }

    boolean CheckCollision()
    {
        boolean collision = false;

        if (CollisionRight.CheckFieldCollision() ||
            CollisionLeft.CheckFieldCollision() ||
            CollisionTop.CheckFieldCollision() ||
            CollisionBottom.CheckFieldCollision())
        {
            collision = true;
            System.out.println("Kollision erkannt");
        }
        return collision;
    }

    boolean CheckCollision(Himmelsrichtung direction)
    {
        boolean collision = false;

        switch (direction)
        {
            case Himmelsrichtung.NORTH:
                collision = CollisionTop.CheckFieldCollision();
                break;
            case Himmelsrichtung.SOUTH:
                collision = CollisionBottom.CheckFieldCollision();
                break;
            case Himmelsrichtung.WEST:
                collision = CollisionLeft.CheckFieldCollision();
                break;
            case Himmelsrichtung.EAST:
                collision = CollisionRight.CheckFieldCollision();
                break;
        }
        return collision;
    }
    
    void initPathfinding()
    {
        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();
        Logger.log("Fetched field for pathfinding with length: "+ field.size(), LogLevel.DEBUG);
        this.graphTraversal = new GraphTraversal(field);
        GraphTraversal.precomputeWallDistances();
        GraphTraversal.traverse(this.XPositionGeben() / 10, this.YPositionGeben() / 10);
        Logger.log("Ghost initPathfinding at (" + this.XPositionGeben()/10 + "," + this.YPositionGeben()/10 + ")", LogLevel.INFO);
    }

    void updatePathfinding()
    {
        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();
        this.graphTraversal = new GraphTraversal(field);
        GraphTraversal.traverse(this.XPositionGeben() / 10, this.YPositionGeben() / 10);
        Logger.log("Ghost updatePathfinding at (" + this.XPositionGeben()/10 + "," + this.YPositionGeben()/10 + ")", LogLevel.DEBUG);
    }

    List<Node> getPathTo(int targetX, int targetY)
    {
        return AStar.findPath(this.XPositionGeben() / 10, this.YPositionGeben() / 10, targetX, targetY);
    }

    void setTarget(int tileX, int tileY) {
        this.targetX = tileX;
        this.targetY = tileY;
        computePath();
    }

    private void computePath() {
        if (targetX < 0 || targetY < 0) return;
        long t0 = System.nanoTime();
        currentPath = getPathTo(targetX, targetY);
        pathIndex = 0;
        lastPathComputeNs = System.nanoTime() - t0;
        Logger.log("Ghost path computed length=" + currentPath.size() + " time=" + (lastPathComputeNs/1_000_000.0) + "ms", LogLevel.SUCCESS);
    }

    void stepAlongPath() {
        if (currentPath == null || currentPath.isEmpty()) return;
        if (pathIndex >= currentPath.size()) return;
        advanceSmooth();
    }

    boolean pathFinished() {
        return pathIndex >= currentPath.size();
    }

    void maybeRecomputePath(int tileX, int tileY) {
        // Recompute only if target changed
        if (tileX != targetX || tileY != targetY) {
            setTarget(tileX, tileY);
        }
    }

    // Smooth incremental movement toward next node with collision recheck
    private void advanceSmooth() {
        if (pathIndex >= currentPath.size()) return;
        Node next = currentPath.get(pathIndex);
        int targetWorldX = next.getX() * 10;
        int targetWorldY = next.getY() * 10;
        int currentX = this.XPositionGeben();
        int currentY = this.YPositionGeben();
        int dx = targetWorldX - currentX;
        int dy = targetWorldY - currentY;
        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);
        if (absDx == 0 && absDy == 0) {
            pathIndex++;
            Logger.log("Ghost reached node (" + next.getX() + "," + next.getY() + ")", LogLevel.TRACE);
            return;
        }
        // Normalize for axis-aligned movement (A* only returns orthogonal nodes now)
        int stepX = 0;
        int stepY = 0;
        if (dx != 0) stepX = (dx > 0 ? 1 : -1) * Math.min(movementSpeed, absDx);
        if (dy != 0) stepY = (dy > 0 ? 1 : -1) * Math.min(movementSpeed, absDy);
        int proposedX = currentX + stepX;
        int proposedY = currentY + stepY;

        // Collision recheck at proposed position
        updateCollisionBoxes(proposedX, proposedY);
        if (CheckCollision()) {
            Logger.log("Ghost movement blocked; recomputing path", LogLevel.WARN);
            pathBlocked = true;
            // Reset collision boxes back to current position
            updateCollisionBoxes(currentX, currentY);
            // Attempt path recompute toward current target
            computePath();
            return;
        }
        pathBlocked = false;
        PositionSetzen(proposedX, proposedY);
        updateCollisionBoxes(proposedX, proposedY);
        Logger.log("Ghost smooth move to (" + proposedX + "," + proposedY + ")", LogLevel.TRACE);
        // If now at target node center advance index
        if (proposedX == targetWorldX && proposedY == targetWorldY) {
            pathIndex++;
            Logger.log("Ghost finalized node (" + next.getX() + "," + next.getY() + ")", LogLevel.DEBUG);
        }
    }

    private void updateCollisionBoxes(int baseX, int baseY) {
        // Offsets preserved from constructor logic
        CollisionRight.PositionSetzen(baseX + 140, baseY + 35);
        CollisionLeft.PositionSetzen(baseX - 1, baseY + 35);
        CollisionTop.PositionSetzen(baseX + 70, baseY - 1);
        CollisionBottom.PositionSetzen(baseX + 70, baseY + 140);
    }
}