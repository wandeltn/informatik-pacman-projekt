package GraphTraversal;
import Logger.*;
import java.util.*;

public class AStar {

    public static List<Node> findPath(int startX, int startY, int goalX, int goalY) {
        // Ensure graph exists; if empty build full graph.
        if (GraphTraversal.getNodes().isEmpty()) {
            GraphTraversal.buildFullGraph();
        }
        
        int numTraversedNodes = 0;
        long t0 = System.nanoTime();
        Logger.log("A* findPath start: (" + startX + "," + startY + ") -> (" + goalX + "," + goalY + ")", LogLevel.INFO);

        Node start = GraphTraversal.getNode(startX, startY);
        Node goal = GraphTraversal.getNode(goalX, goalY);
        
        if (start == null || goal == null) {
            Logger.log("A* abort: start or goal invalid", LogLevel.ERROR);
            start = GraphTraversal.getNearestNode(startX, startY);
            goal = GraphTraversal.getNearestNode(goalX, goalY);
            if (start != null && goal != null) {
                Logger.log("Maybe you meant to start at: " + start.toString() + " or end at: " + goal.toString(), LogLevel.WARN);
            } else {
                Logger.log("A* abort: could not locate nearest valid start or goal", LogLevel.ERROR);
                return Collections.emptyList();
            }
            Logger.log("Trying pathfind again", LogLevel.WARN);
            //List<Node> onlyDestination = new ArrayList<>();
            //onlyDestination.add(nearestEnd);
            //return onlyDestination;
        }

        // if (start == null || goal == null) {
            // Logger.log("A* abort: start or goal invalid", LogLevel.ERROR);
            // Node nearestStart = GraphTraversal.getNearestNode(startX, startY);
            // Node nearestEnd = GraphTraversal.getNearestNode(goalX, goalY);
            // Logger.log("Maybe you ment to start at: " + nearestStart.toString() + " or end at: " + nearestEnd.toString(), LogLevel.WARN);
            // List<Node> onlyDestination = new ArrayList<>();
            // //onlyDestination.add(nearestEnd);
            // return onlyDestination;
        // }

        // A* data structures
        HashMap<Node, Integer> gCost = new HashMap<>();
        HashMap<Node, Integer> fCost = new HashMap<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();

        Comparator<Node> cmp = Comparator.comparingInt(n -> fCost.getOrDefault(n, Integer.MAX_VALUE));
        PriorityQueue<Node> openSet = new PriorityQueue<>(cmp);
        HashSet<Node> openSetHash = new HashSet<>();
        HashSet<Node> closedSet = new HashSet<>();

        gCost.put(start, 0);
        fCost.put(start, heuristic(start, goal));
        openSet.add(start);
        openSetHash.add(start);

        int expansions = 0;
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            openSetHash.remove(current);

            if (current == goal) {
                List<Node> rawPath = reconstructPath(cameFrom, current);
                long smoothStart = System.nanoTime();
                List<Node> path = smoothPath(rawPath);
                long t1 = System.nanoTime();
                Logger.log(
                    "A* success: rawLength=" + rawPath.size() +
                    " smoothedLength=" + path.size() +
                    " expansions=" + expansions +
                    " searchTime=" + ((smoothStart - t0)/1_000_000.0) + "ms" +
                    " smoothingTime=" + ((t1 - smoothStart)/1_000_000.0) + "ms" +
                    " totalTime=" + ((t1 - t0)/1_000_000.0) + "ms",
                    LogLevel.SUCCESS);
                return path;
            }

            closedSet.add(current);

            for (Node neighbor : current.getNeighbors()) {
                if (closedSet.contains(neighbor)) continue;
                int tentativeG = gCost.get(current) + 1; // uniform cost
                int currentG = gCost.getOrDefault(neighbor, Integer.MAX_VALUE);
                if (tentativeG < currentG) {
                    cameFrom.put(neighbor, current);
                    gCost.put(neighbor, tentativeG);
                    fCost.put(neighbor, tentativeG + heuristic(neighbor, goal));
                    if (!openSetHash.contains(neighbor)) {
                        openSet.add(neighbor);
                        openSetHash.add(neighbor);
                    }
                }
            }
            expansions++;
        }
        long t1 = System.nanoTime();
        Logger.log("A* failed: no path found after expansions=" + expansions + " time=" + ((t1 - t0)/1_000_000.0) + "ms", LogLevel.ERROR);
        return Collections.emptyList(); // no path
    }

    // Compress path by removing unnecessary intermediate nodes.
    // Strategy:
    // 1. Collinear compression: remove nodes that lie on straight horizontal/vertical segments.
    // 2. Line-of-sight skipping: attempt to jump further along segment if all intervening tiles are valid.
    private static List<Node> smoothPath(List<Node> path) {
        if (path == null) return Collections.emptyList();
        // Remove any accidental nulls in the supplied path
        List<Node> cleaned = new ArrayList<>();
        for (Node n : path) if (n != null) cleaned.add(n);
        if (cleaned.size() < 3) return cleaned; // nothing to smooth
        path = cleaned;
        // Step 1: collinear compression
        List<Node> collinear = new ArrayList<>();
        collinear.add(path.get(0));
        for (int i = 1; i < path.size() - 1; i++) {
            Node prev = path.get(i - 1);
            Node curr = path.get(i);
            Node next = path.get(i + 1);
            if (prev == null || curr == null || next == null) continue;
            boolean sameX = prev.getX() == curr.getX() && curr.getX() == next.getX();
            boolean sameY = prev.getY() == curr.getY() && curr.getY() == next.getY();
            if (!(sameX || sameY)) {
                collinear.add(curr);
            }
        }
        collinear.add(path.get(path.size() - 1));

        if (collinear.size() < 3) return collinear; // nothing more to smooth

        // Step 2: line-of-sight skipping (only horizontal/vertical segments allowed)
        List<Node> smoothed = new ArrayList<>();
        int anchorIndex = 0;
        smoothed.add(collinear.get(anchorIndex));
        while (anchorIndex < collinear.size() - 1) {
            int furthest = anchorIndex + 1;
            for (int j = anchorIndex + 2; j < collinear.size(); j++) {
                Node a = collinear.get(anchorIndex);
                Node b = collinear.get(j);
                if (a == null || b == null) break;
                if (hasLineOfSight(a, b)) {
                    furthest = j;
                } else {
                    break;
                }
            }
            smoothed.add(collinear.get(furthest));
            anchorIndex = furthest;
        }
        return smoothed;
    }

    private static boolean hasLineOfSight(Node a, Node b) {
        int ax = a.getX();
        int ay = a.getY();
        int bx = b.getX();
        int by = b.getY();
        // Only horizontal or vertical straight segments supported.
        if (ax != bx && ay != by) return false;
        int dx = Integer.compare(bx, ax);
        int dy = Integer.compare(by, ay);
        int x = ax;
        int y = ay;
        while (x != bx || y != by) {
            // advance
            if (x != bx) x += dx;
            if (y != by) y += dy;
            if (!inBounds(x, y)) return false;
            if (GraphTraversal.field.get(y).get(x) != 0) return false; // wall
            if (GraphTraversal.wallDistance != null && GraphTraversal.wallDistance[y][x] < GraphTraversal.CLEARANCE) return false;
        }
        return true;
    }

    private static boolean inBounds(int x, int y) {
        return y >= 0 && y < GraphTraversal.field.size() && x >= 0 && x < GraphTraversal.field.get(0).size();
    }

    private static int heuristic(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private static List<Node> reconstructPath(HashMap<Node, Node> cameFrom, Node current) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            Node next = cameFrom.get(current);
            if (next == null) break; // defensive: avoid adding nulls which cause downstream NPEs
            current = next;
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }
}