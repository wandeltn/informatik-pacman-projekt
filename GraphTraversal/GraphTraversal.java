package GraphTraversal;
import java.util.ArrayList;


/**
 * Write a description of class GraphTraversal here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GraphTraversal
{
    static ArrayList<ArrayList<Integer>> field;
    // Map coordinates to Node for quick lookup while building the graph
    static java.util.HashMap<String, Node> nodeMap = new java.util.HashMap<>();
    static ArrayList<Node> nodes = new ArrayList<>();
    static int[][] wallDistance; // Manhattan distance to nearest wall (1)
    static final int CLEARANCE = 31;

    public GraphTraversal(ArrayList<ArrayList<Integer>> field)
    {
        GraphTraversal.field = field;
    }

    public static void traverse(int x, int y)
    {
        if (field == null || field.isEmpty()) return;
        if (!isValid(x, y)) return; // starting point invalid
        long t0 = System.nanoTime();
        LoggerWrapper.log("Traverse start at (" + x + "," + y + ")", "INFO");

        java.util.ArrayDeque<int[]> queue = new java.util.ArrayDeque<>();
        java.util.HashSet<String> visited = new java.util.HashSet<>();
        queue.add(new int[]{x, y});
        visited.add(key(x, y));

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];

            Node currentNode = getOrCreateNode(cx, cy);
            LoggerWrapper.log("Visiting node (" + cx + "," + cy + ")", "TRACE");

            // 4-directional movement (can extend to 8 if desired)
            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
            for (int[] d : dirs) {
                int nx = cx + d[0];
                int ny = cy + d[1];
                if (inBounds(nx, ny) && isValid(nx, ny)) {
                    String k = key(nx, ny);
                    Node neighbor = getOrCreateNode(nx, ny);
                    currentNode.addNeighbor(neighbor);
                    neighbor.addNeighbor(currentNode); // undirected
                    if (!visited.contains(k)) {
                        visited.add(k);
                        queue.add(new int[]{nx, ny});
                        LoggerWrapper.log("Queued neighbor (" + nx + "," + ny + ")", "TRACE");
                    }
                }
            }
        }
        long t1 = System.nanoTime();
        LoggerWrapper.log("Traverse completed. Nodes: " + nodes.size() + " in " + ((t1 - t0)/1_000_000.0) + "ms", "SUCCESS");
    }

    static boolean isValid(int x, int y) {
        if (!inBounds(x, y)) return false;
        if (field.get(y).get(x) != 0) return false; // must be walkable
        ensureWallDistance();
        return wallDistance[y][x] >= CLEARANCE;
    }

    // Ensure distance map computed
    private static void ensureWallDistance() {
        if (wallDistance == null) {
            precomputeWallDistances();
        }
    }

    // Two-pass Manhattan distance transform from walls (value 1)
    public static void precomputeWallDistances() {
        if (field == null || field.isEmpty()) return;
        int rows = field.size();
        int cols = field.get(0).size();
        long t0 = System.nanoTime();
        LoggerWrapper.log("Precomputing wall distances (" + rows + "x" + cols + ")", "INFO");
        wallDistance = new int[rows][cols];
        int INF = rows + cols + 5; // upper bound
        // init
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                wallDistance[y][x] = (field.get(y).get(x) == 1) ? 0 : INF;
            }
        }
        // forward pass
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                int v = wallDistance[y][x];
                if (x > 0) v = Math.min(v, wallDistance[y][x-1] + 1);
                if (y > 0) v = Math.min(v, wallDistance[y-1][x] + 1);
                wallDistance[y][x] = v;
            }
        }
        // backward pass
        for (int y = rows - 1; y >= 0; y--) {
            for (int x = cols - 1; x >= 0; x--) {
                int v = wallDistance[y][x];
                if (x + 1 < cols) v = Math.min(v, wallDistance[y][x+1] + 1);
                if (y + 1 < rows) v = Math.min(v, wallDistance[y+1][x] + 1);
                wallDistance[y][x] = v;
            }
        }
        long t1 = System.nanoTime();
        LoggerWrapper.log("Wall distance precompute finished in " + ((t1 - t0)/1_000_000.0) + "ms", "SUCCESS");
    }

    private static boolean inBounds(int x, int y) {
        return y >= 0 && y < field.size() && x >= 0 && x < field.get(0).size();
    }

    private static String key(int x, int y) { return x + "," + y; }

    private static Node getOrCreateNode(int x, int y) {
        String k = key(x, y);
        Node n = nodeMap.get(k);
        if (n == null) {
            n = new Node(x, y);
            nodeMap.put(k, n);
            nodes.add(n);
        }
        return n;
    }

    public static ArrayList<Node> getNodes() { return nodes; }

    public static Node getNode(int x, int y) {
        return nodeMap.get(key(x, y));
    }

    // Build the full graph of all valid nodes (clearance satisfied) across the entire field.
    // Safe to call multiple times; existing nodes will be reused.
    public static void buildFullGraph() {
        if (field == null || field.isEmpty()) return;
        ensureWallDistance();
        int rows = field.size();
        int cols = field.get(0).size();
        long t0 = System.nanoTime();
        LoggerWrapper.log("Building full graph", "INFO");
        // First pass: create nodes for valid cells
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (isValid(x, y)) {
                    getOrCreateNode(x, y);
                }
            }
        }
        // Second pass: connect neighbors (4-directional)
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (Node n : nodes) {
            int cx = n.getX();
            int cy = n.getY();
            for (int[] d : dirs) {
                int nx = cx + d[0];
                int ny = cy + d[1];
                Node neighbor = getNode(nx, ny);
                if (neighbor != null) {
                    n.addNeighbor(neighbor);
                }
            }
        }
        long t1 = System.nanoTime();
        LoggerWrapper.log("Graph build complete. Node count: " + nodes.size() + " in " + ((t1 - t0)/1_000_000.0) + "ms", "SUCCESS");
    }

}