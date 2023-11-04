import java.util.ArrayList;
import java.util.List;

public class DiGraphsTest {
  static DiGraphs.DiGraph createTinyDG() {
    DiGraphs.DiGraph g = new DiGraphs.DiGraph(13);
    g.addEdge(4, 2);
    g.addEdge(2, 3);
    g.addEdge(3, 2);
    g.addEdge(6, 0);
    g.addEdge(0, 1);
    g.addEdge(2, 0);
    g.addEdge(11, 12);
    g.addEdge(12, 9);
    g.addEdge(9, 10);
    g.addEdge(9, 11);
    g.addEdge(8, 9);
    g.addEdge(10, 12);
    g.addEdge(11, 4);
    g.addEdge(4, 3);
    g.addEdge(3, 5);
    g.addEdge(7, 8);
    g.addEdge(8, 7);
    g.addEdge(5, 4);
    g.addEdge(0, 5);
    g.addEdge(6, 4);
    g.addEdge(6, 9);
    g.addEdge(7, 6);
    return g;
  }

  static void testDFS() {
    DiGraphs.DiGraph g = createTinyDG();
    DiGraphs.DirectedDFS dfs1 = new DiGraphs.DirectedDFS(g, 1);
    assert dfs1.marked(1);

    DiGraphs.DirectedDFS dfs2 = new DiGraphs.DirectedDFS(g, 2);
    for (int i = 0; i < 6; i++) {
      assert dfs2.marked(i) : "i=" + i;
    }

    List<Integer> sources = new ArrayList<>();
    sources.add(1);
    sources.add(2);
    sources.add(6);
    DiGraphs.DirectedDFS dfs3 = new DiGraphs.DirectedDFS(g, sources);
    for (int i = 0; i < 6; i++) {
      assert dfs3.marked(i) : "i=" + i;
    }
    for (int i = 9; i < 13; i++) {
      assert dfs3.marked(i) : "i=" + i;
    }

    System.out.println("passed dfs");
  }

  public static void main(String[] args) {
    testDFS();
  }
}
