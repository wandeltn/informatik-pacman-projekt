package GraphTraversal;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Arrays;


public class AStarTest
{
    public AStarTest() { }

    @BeforeEach
    public void setUp() { }

    @AfterEach
    public void tearDown() { }

    // Helper to set a rectangular field (rows x cols) using the provided values.
    private void setField(int rows, int cols, int[][] values) {
        ArrayList<ArrayList<Integer>> field = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = 0; x < cols; x++) {
                row.add(values[y][x]);
            }
            field.add(row);
        }
        GraphTraversal.field = field;
        GraphTraversal.wallDistance = null; // disable clearance checks for tests unless needed
        // ensure nodes are rebuilt based on new field
        GraphTraversal.getNodes().clear();
    }

    @Test
    public void testFindPathStraightHorizontal() {
        // 1 row, 3 columns: all free (0)
        int[][] vals = {{0, 0, 0}};
        setField(1, 3, vals);

        List<Node> path = AStar.findPath(0, 0, 2, 0);
        assertNotNull(path, "Path should not be null for a clear straight line");
        assertFalse(path.isEmpty(), "Path should exist for clear straight line");
        // smoothing should compress the middle node, leaving only start and goal
        assertEquals(2, path.size(), "Smoothed straight-line path should contain only start and goal");
        assertEquals(0, path.get(0).getX());
        assertEquals(0, path.get(0).getY());
        assertEquals(2, path.get(path.size() - 1).getX());
        assertEquals(0, path.get(path.size() - 1).getY());
    }

    @Test
    public void testFindPathBlockedMiddle() {
        // 1 row, 3 columns: middle cell is a wall (1)
        int[][] vals = {{0, 1, 0}};
        setField(1, 3, vals);

        List<Node> path = AStar.findPath(0, 0, 2, 0);
        // No alternative routes exist in this tiny field, so expect no path
        assertNotNull(path, "Path result should not be null even when no path is found");
        assertTrue(path.isEmpty(), "Path should be empty when the direct route is blocked and no alternative exists");
    }

    // Tests for the private smoothPath method using reflection.

    @Test
    public void testSmoothPathCollinearCompression() throws Exception {
        // horizontal line 0,0 -> 1,0 -> 2,0 should compress to 0,0 -> 2,0
        int[][] vals = {{0,0,0}};
        setField(1, 3, vals);

        // Force graph build
        AStar.findPath(0,0,0,0);

        Node n0 = GraphTraversal.getNode(0,0);
        Node n1 = GraphTraversal.getNode(1,0);
        Node n2 = GraphTraversal.getNode(2,0);
        List<Node> raw = Arrays.asList(n0, n1, n2);

        Method m = AStar.class.getDeclaredMethod("smoothPath", List.class);
        m.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Node> smoothed = (List<Node>) m.invoke(null, raw);

        assertNotNull(smoothed);
        assertEquals(2, smoothed.size(), "Collinear horizontal nodes should be compressed to start and goal");
        assertEquals(0, smoothed.get(0).getX());
        assertEquals(0, smoothed.get(1).getX());
        assertEquals(2, smoothed.get(1).getX());
    }

    @Test
    public void testSmoothPathDiagonalNoSkip() throws Exception {
        // diagonal nodes: smoothing should not skip because only axis-aligned LOS supported
        int[][] vals = {
            {0,0,0},
            {0,0,0},
            {0,0,0}
        };
        setField(3, 3, vals);

        // Force graph build
        AStar.findPath(0,0,0,0);

        Node a = GraphTraversal.getNode(0,0);
        Node b = GraphTraversal.getNode(1,1);
        Node c = GraphTraversal.getNode(2,2);
        List<Node> raw = Arrays.asList(a, b, c);

        Method m = AStar.class.getDeclaredMethod("smoothPath", List.class);
        m.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Node> smoothed = (List<Node>) m.invoke(null, raw);

        assertNotNull(smoothed);
        // No compression via LOS should occur for diagonal jump; size should remain 3
        assertEquals(3, smoothed.size(), "Diagonal path should not be LOS-skipped (only axis-aligned supported)");
        assertEquals(a, smoothed.get(0));
        assertEquals(b, smoothed.get(1));
        assertEquals(c, smoothed.get(2));
    }

    @Test
    public void testSmoothPathLineOfSightSkipping() throws Exception {
        // Construct a path that can be LOS-skipped from start to final x-aligned node.
        // Path: (0,0)->(1,0)->(2,0)->(2,1)->(3,1)->(4,1)->(4,0)
        int[][] vals = {
            {0,0,0,0,0},
            {0,0,0,0,0}
        };
        setField(2, 5, vals);

        // Force graph build
        AStar.findPath(0,0,0,0);

        List<Node> raw = Arrays.asList(
            GraphTraversal.getNode(0,0),
            GraphTraversal.getNode(1,0),
            GraphTraversal.getNode(2,0),
            GraphTraversal.getNode(2,1),
            GraphTraversal.getNode(3,1),
            GraphTraversal.getNode(4,1),
            GraphTraversal.getNode(4,0)
        );

        Method m = AStar.class.getDeclaredMethod("smoothPath", List.class);
        m.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Node> smoothed = (List<Node>) m.invoke(null, raw);

        assertNotNull(smoothed);
        // Expect LOS skipping to allow a direct jump from (0,0) to (4,0) -> final smoothed should be [start, goal]
        assertEquals(2, smoothed.size(), "Line-of-sight skipping should reduce path to start and final aligned goal");
        assertEquals(0, smoothed.get(0).getX());
        assertEquals(0, smoothed.get(0).getY());
        assertEquals(4, smoothed.get(1).getX());
        assertEquals(0, smoothed.get(1).getY());
    }

    @Test
    public void testHasLineOfSightHorizontalClear() throws Exception {
        int[][] vals = {{0,0,0,0,0}};
        setField(1, 5, vals);
        // force build
        AStar.findPath(0,0,0,0);

        Node a = GraphTraversal.getNode(0,0);
        Node b = GraphTraversal.getNode(4,0);
        Method m = AStar.class.getDeclaredMethod("hasLineOfSight", Node.class, Node.class);
        m.setAccessible(true);
        boolean res = (Boolean) m.invoke(null, a, b);
        assertTrue(res, "Horizontal clear line should have LOS");
    }

    @Test
    public void testHasLineOfSightHorizontalBlocked() throws Exception {
        int[][] vals = {{0,0,1,0,0}};
        setField(1, 5, vals);
        AStar.findPath(0,0,0,0);

        Node a = GraphTraversal.getNode(0,0);
        Node b = GraphTraversal.getNode(4,0);
        Method m = AStar.class.getDeclaredMethod("hasLineOfSight", Node.class, Node.class);
        m.setAccessible(true);
        boolean res = (Boolean) m.invoke(null, a, b);
        assertFalse(res, "Horizontal LOS should be blocked by a wall");
    }

    @Test
    public void testHasLineOfSightVerticalClear() throws Exception {
        int[][] vals = {
            {0},
            {0},
            {0},
            {0},
            {0}
        };
        setField(5, 1, vals);
        AStar.findPath(0,0,0,0);

        Node a = GraphTraversal.getNode(0,0);
        Node b = GraphTraversal.getNode(0,4);
        Method m = AStar.class.getDeclaredMethod("hasLineOfSight", Node.class, Node.class);
        m.setAccessible(true);
        boolean res = (Boolean) m.invoke(null, a, b);
        assertTrue(res, "Vertical clear line should have LOS");
    }

    @Test
    public void testHasLineOfSightVerticalBlocked() throws Exception {
        int[][] vals = {
            {0},
            {0},
            {1},
            {0},
            {0}
        };
        setField(5, 1, vals);
        AStar.findPath(0,0,0,0);

        Node a = GraphTraversal.getNode(0,0);
        Node b = GraphTraversal.getNode(0,4);
        Method m = AStar.class.getDeclaredMethod("hasLineOfSight", Node.class, Node.class);
        m.setAccessible(true);
        boolean res = (Boolean) m.invoke(null, a, b);
        assertFalse(res, "Vertical LOS should be blocked by a wall");
    }

    @Test
    public void testHasLineOfSightDiagonalUnsupported() throws Exception {
        int[][] vals = {
            {0,0,0},
            {0,0,0},
            {0,0,0}
        };
        setField(3, 3, vals);
        AStar.findPath(0,0,0,0);

        Node a = GraphTraversal.getNode(0,0);
        Node b = GraphTraversal.getNode(2,2);
        Method m = AStar.class.getDeclaredMethod("hasLineOfSight", Node.class, Node.class);
        m.setAccessible(true);
        boolean res = (Boolean) m.invoke(null, a, b);
        assertFalse(res, "Diagonal LOS is unsupported and should return false");
    }

    @Test
    public void testHasLineOfSightBlockedByClearance() throws Exception {
        int[][] vals = {{0,0,0}};
        setField(1, 3, vals);
        // prepare wallDistance such that middle cell is below CLEARANCE
        int rows = 1, cols = 3;
        int[][] wd = new int[rows][cols];
        for (int y = 0; y < rows; y++) for (int x = 0; x < cols; x++) wd[y][x] = GraphTraversal.CLEARANCE + 5;
        // set middle to value < CLEARANCE to block by clearance rule
        wd[0][1] = GraphTraversal.CLEARANCE - 1;
        GraphTraversal.wallDistance = wd;
        GraphTraversal.getNodes().clear();
        AStar.findPath(0,0,0,0);

        Node a = GraphTraversal.getNode(0,0);
        Node b = GraphTraversal.getNode(2,0);
        Method m = AStar.class.getDeclaredMethod("hasLineOfSight", Node.class, Node.class);
        m.setAccessible(true);
        boolean res = (Boolean) m.invoke(null, a, b);
        assertFalse(res, "LOS should be blocked when an intervening cell's wallDistance is below CLEARANCE");
    }
}
