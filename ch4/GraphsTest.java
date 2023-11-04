import java.util.List;

public class GraphsTest {
  static Graphs.Graph createTinyCG() {
    Graphs.Graph g = new Graphs.Graph(6);
    g.addEdge(0, 5);
    g.addEdge(2, 4);
    g.addEdge(2, 3);
    g.addEdge(1, 2);
    g.addEdge(0, 1);
    g.addEdge(3, 4);
    g.addEdge(3, 5);
    g.addEdge(0, 2);
    return g;
  }

  static Graphs.Graph createTinyG() {
    Graphs.Graph g = new Graphs.Graph(13);
    g.addEdge(0, 5);
    g.addEdge(4, 3);
    g.addEdge(0, 1);
    g.addEdge(9, 12);
    g.addEdge(6, 4);
    g.addEdge(5, 4);
    g.addEdge(0, 2);
    g.addEdge(11, 12);
    g.addEdge(9, 10);
    g.addEdge(0, 6);
    g.addEdge(7, 8);
    g.addEdge(9, 11);
    g.addEdge(5, 3);
    return g;
  }

  static void testDFSPath() {
    Graphs.Graph g = createTinyCG();
    Graphs.DFSPaths paths = new Graphs.DFSPaths(g, 0);
    assert paths.hasPath(0);
    assert paths.hasPath(1);
    assert paths.hasPath(2);
    assert paths.hasPath(3);
    assert paths.hasPath(4);
    assert paths.hasPath(5);

    Iterable<Integer> path = paths.path(0);
    assert path.equals(List.of(0)) : path;
    path = paths.path(1);
    assert path.equals(List.of(0, 2, 1)) : path;
    path = paths.path(2);
    assert path.equals(List.of(0, 2)) : path;
    path = paths.path(3);
    assert path.equals(List.of(0, 2, 3)) : path;
    path = paths.path(4);
    assert path.equals(List.of(0, 2, 3, 4)) : path;
    path = paths.path(5);
    assert path.equals(List.of(0, 2, 3, 5)) : path;

    System.out.println("pass DFSPath");
  }

  static void testBFSPath() {
    Graphs.Graph g = createTinyCG();
    Graphs.BFSPaths paths = new Graphs.BFSPaths(g, 0);
    assert paths.hasPath(0);
    assert paths.hasPath(1);
    assert paths.hasPath(2);
    assert paths.hasPath(3);
    assert paths.hasPath(4);
    assert paths.hasPath(5);

    Iterable<Integer> path = paths.path(0);
    assert path.equals(List.of(0)) : path;
    path = paths.path(1);
    assert path.equals(List.of(0, 1)) : path;
    path = paths.path(2);
    assert path.equals(List.of(0, 2)) : path;
    path = paths.path(3);
    assert path.equals(List.of(0, 2, 3)) : path;
    path = paths.path(4);
    assert path.equals(List.of(0, 2, 4)) : path;
    path = paths.path(5);
    assert path.equals(List.of(0, 5)) : path;

    System.out.println("pass BFSPath");
  }

  static void testDFSCC() {
    Graphs.Graph g = createTinyG();
    Graphs.DFSCC cc = new Graphs.DFSCC(g);
    assert cc.count() == 3;
    for (int i = 0; i < 7; i++) assert 0 == cc.id(i) : i + ") " + cc.id(i);
    for (int i = 7; i < 9; i++) assert 1 == cc.id(i) : i + ") " + cc.id(i);
    for (int i = 9; i < 13; i++) assert 2 == cc.id(i) : i + ") " + cc.id(i);

    System.out.println("pass DFSCC");
  }

  public static void main(String[] args) {
    testDFSPath();
    testBFSPath();
    testDFSCC();
  }
}
