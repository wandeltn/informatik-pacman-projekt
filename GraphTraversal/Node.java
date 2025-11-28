package GraphTraversal;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final int x;
    private final int y;
    private final List<Node> neighbors = new ArrayList<>();

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public List<Node> getNeighbors() { return neighbors; }

    public void addNeighbor(Node n) {
        if (n != null && n != this && !neighbors.contains(n)) {
            neighbors.add(n);
        }
    }
    
    public String toString()
    {
        return "Node: [x=" + x + ", y=" + y + "]";
    }
}