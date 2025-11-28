package GraphTraversal;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * The test class GraphTraversalTest.
 */
public class GraphTraversalTest
{
    public GraphTraversalTest() { }

    @BeforeEach
    public void setUp() { }

    @AfterEach
    public void tearDown()
    {
        // reset static state to avoid test interference
        GraphTraversal.field = null;
        GraphTraversal.wallDistance = null;
        if (GraphTraversal.nodeMap != null) GraphTraversal.nodeMap.clear();
        if (GraphTraversal.nodes != null) GraphTraversal.nodes.clear();
    }

    @Test
    public void testPrecomputeWallDistances_singleWallCenter()
    {
        // 3x3 grid with center wall
        int rows = 3, cols = 3;
        ArrayList<ArrayList<Integer>> field = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = 0; x < cols; x++) {
                row.add((x == 1 && y == 1) ? 1 : 0);
            }
            field.add(row);
        }
        // add the extra empty last row as expected by implementation
        field.add(new ArrayList<>());

        GraphTraversal.field = field;
        GraphTraversal.precomputeWallDistances();

        int[][] expected = {
            {2,1,2},
            {1,0,1},
            {2,1,2}
        };

        assertNotNull(GraphTraversal.wallDistance);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                assertEquals(expected[y][x], GraphTraversal.wallDistance[y][x],
                    "Mismatch at (" + x + "," + y + ")");
            }
        }
    }

    @Test
    public void testPrecomputeWallDistances_allZeros()
    {
        // 2x4 grid with no walls -> distances should be INF = rows+cols+5
        int rows = 2, cols = 4;
        ArrayList<ArrayList<Integer>> field = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = 0; x < cols; x++) {
                row.add(0);
            }
            field.add(row);
        }
        field.add(new ArrayList<>());

        GraphTraversal.field = field;
        GraphTraversal.precomputeWallDistances();

        int INF = rows + cols + 5;
        assertNotNull(GraphTraversal.wallDistance);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                assertEquals(INF, GraphTraversal.wallDistance[y][x],
                    "Expected INF at (" + x + "," + y + ")");
            }
        }
    }

    @Test
    public void testPrecomputeWallDistances_allOnes()
    {
        // 4x2 grid with all walls -> distances should be 0
        int rows = 4, cols = 2;
        ArrayList<ArrayList<Integer>> field = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = 0; x < cols; x++) {
                row.add(1);
            }
            field.add(row);
        }
        field.add(new ArrayList<>());

        GraphTraversal.field = field;
        GraphTraversal.precomputeWallDistances();

        assertNotNull(GraphTraversal.wallDistance);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                assertEquals(0, GraphTraversal.wallDistance[y][x],
                    "Expected 0 at (" + x + "," + y + ")");
            }
        }
    }

    @Test
    public void testFindNearestValid_startIsValid_returnsStart()
    {
        // small grid where INF >= CLEARANCE so all cells are valid
        int rows = 3, cols = 3;
        ArrayList<ArrayList<Integer>> field = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = 0; x < cols; x++) {
                row.add(0);
            }
            field.add(row);
        }
        field.add(new ArrayList<>());

        GraphTraversal.field = field;
        // starting on a valid cell should return the same coordinates
        int sx = 1, sy = 1;
        int[] res = GraphTraversal.findNearestValid(sx, sy);
        assertNotNull(res);
        assertEquals(sx, res[0]);
        assertEquals(sy, res[1]);
    }

    @Test
    public void testFindNearestValid_fromWall_returnsDistance8()
    {
        // large grid with a single wall at the start position.
        // Nearest valid cell should be at Manhattan distance 8 (CLEARANCE).
        int rows = 20, cols = 20;
        ArrayList<ArrayList<Integer>> field = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = 0; x < cols; x++) {
                row.add(0);
            }
            field.add(row);
        }
        // place a wall at center
        int sx = 10, sy = 10;
        field.get(sy).set(sx, 1);
        field.add(new ArrayList<>());

        GraphTraversal.field = field;
        int[] res = GraphTraversal.findNearestValid(sx, sy);
        assertNotNull(res);
        int dist = Math.abs(res[0] - sx) + Math.abs(res[1] - sy);
        assertEquals(8, dist, "Expected nearest valid at Manhattan distance 8");
    }

    @Test
    public void testFindNearestValid_noValid_returnsNull()
    {
        // tiny grid where INF < CLEARANCE so no valid cells exist
        int rows = 1, cols = 1;
        ArrayList<ArrayList<Integer>> field = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = 0; x < cols; x++) {
                row.add(0);
            }
            field.add(row);
        }
        field.add(new ArrayList<>());

        GraphTraversal.field = field;
        int[] res = GraphTraversal.findNearestValid(0, 0);
        assertNull(res);
    }

    @Test
    public void testTraverse_allZeros_createsAllNodes() {
        int rows = 10, cols = 10;
        ArrayList<ArrayList<Integer>> field = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = 0; x < cols; x++) row.add(0);
            field.add(row);
        }
        // add extra row (must have same width to avoid index issues in traverse)
        ArrayList<Integer> extra = new ArrayList<>();
        for (int x = 0; x < cols; x++) extra.add(0);
        field.add(extra);

        GraphTraversal.field = field;
        GraphTraversal.traverse(5, 5);

        assertEquals(rows * cols, GraphTraversal.getNodes().size(), "Expected all walkable cells to become nodes");
        assertNotNull(GraphTraversal.getNode(5, 5));
        assertNotNull(GraphTraversal.getNode(0, 0));
        assertNotNull(GraphTraversal.getNode(cols - 1, rows - 1));
    }

    @Test
    public void testTraverse_startOnWall_noNodesCreated() {
        int rows = 5, cols = 5;
        ArrayList<ArrayList<Integer>> field = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = 0; x < cols; x++) row.add(0);
            field.add(row);
        }
        // set start as wall
        int sx = 2, sy = 2;
        field.get(sy).set(sx, 1);

        // add extra row matching width to avoid traverse index errors
        ArrayList<Integer> extra = new ArrayList<>();
        for (int x = 0; x < cols; x++) extra.add(0);
        field.add(extra);

        GraphTraversal.field = field;
        GraphTraversal.traverse(sx, sy);

        assertTrue(GraphTraversal.getNodes().isEmpty(), "Traverse should not create nodes when start is not walkable");
    }


        @Test
        public void testBuildFullGraph_allZeros_createsAllNodes() {
            int rows = 5, cols = 5;
            ArrayList<ArrayList<Integer>> field = new ArrayList<>();
            for (int y = 0; y < rows; y++) {
                ArrayList<Integer> row = new ArrayList<>();
                for (int x = 0; x < cols; x++) row.add(0);
                field.add(row);
            }
            // add the extra empty last row as expected by implementation
            field.add(new ArrayList<>());

            GraphTraversal.field = field;
            GraphTraversal.buildFullGraph();

            assertEquals(rows * cols, GraphTraversal.getNodes().size(), "Expected all walkable cells to become nodes");
            assertNotNull(GraphTraversal.getNode(0, 0));
            assertNotNull(GraphTraversal.getNode(cols - 1, rows - 1));
            assertNotNull(GraphTraversal.getNode(2, 2));
        }

        @Test
        public void testBuildFullGraph_allWalls_createsNoNodes() {
            int rows = 4, cols = 3;
            ArrayList<ArrayList<Integer>> field = new ArrayList<>();
            for (int y = 0; y < rows; y++) {
                ArrayList<Integer> row = new ArrayList<>();
                for (int x = 0; x < cols; x++) row.add(1);
                field.add(row);
            }
            field.add(new ArrayList<>());

            GraphTraversal.field = field;
            GraphTraversal.buildFullGraph();

            assertTrue(GraphTraversal.getNodes().isEmpty(), "No nodes should be created when all cells are walls");
        }

        @Test
        public void testBuildFullGraph_centerTooCloseToWalls_noNodesCreated() {
            // 3x3 with center walkable but surrounded by walls -> clearance requirement prevents node creation
            int rows = 3, cols = 3;
            ArrayList<ArrayList<Integer>> field = new ArrayList<>();
            for (int y = 0; y < rows; y++) {
                ArrayList<Integer> row = new ArrayList<>();
                for (int x = 0; x < cols; x++) {
                    row.add((x == 1 && y == 1) ? 0 : 1);
                }
                field.add(row);
            }
            field.add(new ArrayList<>());

            GraphTraversal.field = field;
            GraphTraversal.buildFullGraph();

            assertTrue(GraphTraversal.getNodes().isEmpty(), "Center cell should not be considered valid due to insufficient clearance");
        }
}
