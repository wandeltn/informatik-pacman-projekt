import GraphTraversal.AStar;
import GraphTraversal.GraphTraversal;
import GraphTraversal.Node;
import Logger.*;
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
    private long lastPathComputeNs = 0L;
    private int movementSpeed = 2; // pixels per update toward next waypoint
    private boolean pathBlocked = false;
    protected Pacman pacmanRef; // target reference


    /**
     * Legt das Aussehen und die Startposition fest
     * @param x x-Koordinate des Hindernisses
     * @param y y-Koordinate des Hindernisses
     * @param richtungNeu Blickrichtung des Hindernisses
     */
    ColorRGB color = new ColorRGB(255, 255, 255);
    
    // CollisionChecker CollisionRight = new CollisionChecker();
    // CollisionChecker CollisionLeft = new CollisionChecker();
    // CollisionChecker CollisionTop = new CollisionChecker();
    // CollisionChecker CollisionBottom = new CollisionChecker();

    GraphTraversal graphTraversal;

    ArrayList<Figur> pathIndicators = new ArrayList<>();
    
    BaseGhost(int x, int y, ColorRGB color)
    {
        super(x, y, 'O', color.toColor());
        this.color = color;
        System.out.println(color);
        System.out.println(this.color);
        
        // CollisionRight.PositionSetzen(x + 140, y + 35);
        // CollisionLeft.PositionSetzen(x - 1, y + 35);
        // CollisionTop.PositionSetzen(x + 70, y - 1);
        // CollisionBottom.PositionSetzen(x + 70, y + 140);
        
        
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

        FigurteilFestlegenEllipse(0, 0, 70, 70, this.color.toColor());
        FigurteilFestlegenRechteck(0, 35, 70, 35, this.color.toColor());
    }

    final protected void setSpeed(int speed)
    {
        this.movementSpeed = speed;
    }

    boolean CheckCollision()
    {
        boolean collision = false;
        return false;

        /* if (CollisionRight.CheckFieldCollision() ||
             CollisionLeft.CheckFieldCollision() ||
             CollisionTop.CheckFieldCollision() ||
             CollisionBottom.CheckFieldCollision())
         {
             collision = true;
             System.out.println("Kollision erkannt");
         }
         return collision;
         */
    }

    boolean CheckCollision(Himmelsrichtung direction)
    {
        boolean collision = false;
        return false;

        // switch (direction)
        // {
            // case Himmelsrichtung.NORTH:
                // collision = CollisionTop.CheckFieldCollision();
                // break;
            // case Himmelsrichtung.SOUTH:
                // collision = CollisionBottom.CheckFieldCollision();
                // break;
            // case Himmelsrichtung.WEST:
                // collision = CollisionLeft.CheckFieldCollision();
                // break;
            // case Himmelsrichtung.EAST:
                // collision = CollisionRight.CheckFieldCollision();
                // break;
        // }
        // return collision;
    }
    
    void initPathfinding()
    {
        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();
        Logger.log("Fetched field for pathfinding with length: "+ field.size(), LogLevel.DEBUG);
        this.graphTraversal = new GraphTraversal(field);
        ensureValidStartPosition();
        GraphTraversal.precomputeWallDistances();
        GraphTraversal.buildFullGraph();
        GraphTraversal.traverse(worldToTileX(this.XPositionGeben()), worldToTileY(this.YPositionGeben()));
        Logger.log("Ghost initPathfinding at (" + this.XPositionGeben()/10 + "," + this.YPositionGeben()/10 + ")", LogLevel.INFO);
    }

    void updatePathfinding()
    {
        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();
        this.graphTraversal = new GraphTraversal(field);
        ensureValidStartPosition();
        GraphTraversal.buildFullGraph();
        GraphTraversal.traverse(worldToTileX(this.XPositionGeben()), worldToTileY(this.YPositionGeben()));
        Logger.log("Ghost updatePathfinding at (" + this.XPositionGeben()/10 + "," + this.YPositionGeben()/10 + ")", LogLevel.DEBUG);
    }

    List<Node> getPathTo(int targetX, int targetY)
    {
        List<Node> path = new ArrayList<>(AStar.findPath(worldToTileX(this.XPositionGeben()), worldToTileY(this.YPositionGeben()), targetX, targetY));
        if (path.isEmpty())
        {
            Logger.log("No path found to target, falling back to direct movement", LogLevel.WARN);
            path.add(new Node(targetX, targetY));
        }
        return path;
    }

    private void computePath() {
        Logger.log("Checking for pacman on field: x=" + pacmanRef.getXPosition() + " y=" + pacmanRef.getYPosition(), LogLevel.ERROR);
        Logger.log("Converted to tile coordinates: x=" + worldToTileX(pacmanRef.getXPosition()) + " y=" + worldToTileY(pacmanRef.getYPosition()), LogLevel.ERROR);
        if (
            worldToTileX(pacmanRef.getXPosition()) < 0 ||
            worldToTileY(pacmanRef.getYPosition()) < 0
        )
            Logger.log("Pacman position out of bounds: x=" + worldToTileX(pacmanRef.getXPosition()) + " y=" + worldToTileY(pacmanRef.getYPosition()), LogLevel.WARN);
        long t0 = System.nanoTime();
        currentPath = getPathTo(worldToTileX(pacmanRef.getXPosition()), worldToTileY(pacmanRef.getYPosition()));
        pathIndex = 0;
        lastPathComputeNs = System.nanoTime() - t0;
        Logger.log("Ghost path computed length=" + currentPath.size() + " time=" + (lastPathComputeNs/1_000_000.0) + "ms", LogLevel.SUCCESS);
        showPathIndicators();
    }

    void stepAlongPath() 
    {
        computePath();
        if (currentPath == null || currentPath.isEmpty())
        {
            Logger.log("Skipping advanceSmooth, no Path found, defaulting to pacman direct", LogLevel.DEBUG);
            currentPath.add(new Node(worldToTileX(pacmanRef.getXPosition()), worldToTileY(pacmanRef.getYPosition())));
            //return;
        }
        if (pathIndex >= currentPath.size()) 
        {
            Logger.log("Skipping advanceSmooth, finished current Path", LogLevel.DEBUG);
            return;
        }
        advanceSmooth();
    }

    boolean pathFinished() {
        return pathIndex >= currentPath.size();
    }

    // Smooth incremental movement toward next node with collision recheck
    private void advanceSmooth() {
        if (pathIndex >= currentPath.size())
        {
            Logger.log("Last path destination reached, not moving", LogLevel.DEBUG);
            clearPathIndicators();
            return;
        }
        Node next = currentPath.get(pathIndex);
        int nextX = next.getX();
        int nextY = next.getY();
        int targetWorldX = tileToWorldX(nextX);
        int targetWorldY = tileToWorldY(nextY);
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
        // CollisionRight.PositionSetzen(baseX + 140, baseY + 35);
        // CollisionLeft.PositionSetzen(baseX - 1, baseY + 35);
        // CollisionTop.PositionSetzen(baseX + 70, baseY - 1);
        // CollisionBottom.PositionSetzen(baseX + 70, baseY + 140);
    }

    // Fallback if starting tile not valid: move to nearest valid node center.
    private void ensureValidStartPosition() {
        int tileX = worldToTileX(this.XPositionGeben());
        int tileY = worldToTileY(this.YPositionGeben());
        ArrayList<ArrayList<Integer>> field = Playingfield.getPlayingField();
        int fieldVal = -1;
        if (tileY >= 0 && tileY < field.size()) {
            ArrayList<Integer> row = field.get(tileY);
            if (tileX >= 0 && tileX < row.size()) {
                fieldVal = row.get(tileX);
            }
        }
        boolean isWalkable = (fieldVal == 0 || fieldVal == 3 || fieldVal == 4);
        
        // For spawning, we only require walkability, not full clearance
        if (!isWalkable) {
            int[] nearest = GraphTraversal.findNearestValid(tileX, tileY);
            if (nearest != null) {
                PositionSetzen(tileToWorldX(nearest[0]), tileToWorldY(nearest[1]));
                Logger.log("Ghost fallback reposition to valid node (" + nearest[0] + "," + nearest[1] + ")", LogLevel.WARN);
            } else {
                Logger.log("Ghost fallback failed: no valid node found", LogLevel.ERROR);
            }
        }
    }

    public void clearPathIndicators() {
        for (Figur f : pathIndicators) {
            f.SichtbarkeitSetzen(false);
            f.EigeneFigurLöschen();
        }
        pathIndicators.clear();
    }

    public void showPathIndicators() {
        clearPathIndicators();
        for (Node n : currentPath) {
            int worldX = tileToWorldX(n.getX());
            int worldY = tileToWorldY(n.getY());
            Figur indicator = new Figur();
            indicator.PositionSetzen(worldX, worldY);
            indicator.FigurteilFestlegenEllipse(0, 0, 10, 10, new ColorRGB(255, 255, 0).toColor());
            pathIndicators.add(indicator);
        }
    }

    // Coordinate conversion helpers with playingfield offset (match Pacman mapping)
    private int worldToTileX(int worldX) { return (worldX - Playingfield.getOffsetX()) / 10; }
    private int worldToTileY(int worldY) { return (worldY - Playingfield.getOffsetY()) / 10; }
    private int tileToWorldX(int tileX) { return tileX * 10 + Playingfield.getOffsetX(); }
    private int tileToWorldY(int tileY) { return tileY * 10 + Playingfield.getOffsetY(); }
}