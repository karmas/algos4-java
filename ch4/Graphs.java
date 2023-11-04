import java.util.LinkedList;

public class Graphs {

  public static class Graph {
    private final LinkedList<Integer>[] adj;
    private int E;

    @SuppressWarnings("unchecked")
    public Graph(int v) {
      adj = new LinkedList[v];
      for (int i = 0; i < v; i++) {
        adj[i] = new LinkedList<>();
      }
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("vertices=").append(V()).append(" edges=").append(E()).append('\n');
      for (int v = 0; v < V(); v++) {
        sb.append(v).append(":");
        for (int w : adj(v)) {
          sb.append(' ').append(w);
        }
        sb.append('\n');
      }
      return sb.toString();
    }

    public int V() { return adj.length; }
    public int E() { return E; }

    public void addEdge(int v, int w) {
      if (!adj[v].contains(w)) adj[v].addFirst(w);
      else return;
      if (!adj[w].contains(v)) adj[w].addFirst(v);
      ++E;
    }

    public Iterable<Integer> adj(int v) {
      return adj[v];
    }
  }

  public static class DFS {
    private final boolean[] marked;
    private int count;

    public DFS(Graph g, int s) {
      marked = new boolean[g.V()];
      dfs(g, s);
    }

    private void dfs(Graph g, int v) {
      marked[v] = true;
      ++count;
      for (int w : g.adj(v)) {
        if (!marked[w]) dfs(g, w);
      }
    }

    public boolean marked(int v) { return marked[v]; }
    public int count() { return count; }
  }

  public static class DFSPaths {
    private final int[] edgeTo;

    public DFSPaths(Graph g, int s) {
      edgeTo = new int[g.V()];
      for (int i = 0; i < edgeTo.length; i++) {
        edgeTo[i] = -1;
      }
      edgeTo[s] = s;
      dfs(g, s);
    }

    private void dfs(Graph g, int v) {
      for (int w : g.adj(v)) {
        if (edgeTo[w] == -1) {
          edgeTo[w] = v;
          dfs(g, w);
        }
      }
    }

    public boolean hasPath(int v) {
      return edgeTo[v] != -1;
    }

    public Iterable<Integer> path(int v) {
      LinkedList<Integer> l = new LinkedList<>();
      l.add(v);
      while (edgeTo[v] != v) {
        v = edgeTo[v];
        l.addFirst(v);
      }
      return l;
    }
  }

  public static class BFSPaths {
    private final int[] edgeTo;

    public BFSPaths(Graph g, int s) {
      edgeTo = new int[g.V()];
      for (int i = 0; i < edgeTo.length; i++) {
        edgeTo[i] = -1;
      }
      bfs(g, s);
    }

    private void bfs(Graph g, int v) {
      LinkedList<Integer> q = new LinkedList<>();
      edgeTo[v] = v;
      q.add(v);

      while (!q.isEmpty()) {
        int w = q.removeFirst();
        for (Integer i : g.adj(w)) {
          if (-1 == edgeTo[i]) {
            edgeTo[i] = w;
            q.add(i);
          }
        }
      }
    }

    public boolean hasPath(int v) {
      return edgeTo[v] != -1;
    }

    public Iterable<Integer> path(int v) {
      LinkedList<Integer> l = new LinkedList<>();
      l.add(v);
      while (edgeTo[v] != v) {
        v = edgeTo[v];
        l.addFirst(v);
      }
      return l;
    }
  }

  public static class DFSCC {
    private int count;
    private int[] id;

    public DFSCC(Graph g) {
      id = new int[g.V()];
      for (int i = 0; i < id.length; i++) id[i] = -1;

      for (int v = 0; v < id.length; v++) {
        if (-1 == id[v]) {
          dfs(g, v);
          ++count;
        }
      }
    }

    private void dfs(Graph g, int v) {
      id[v] = count;
      for (int w : g.adj(v)) {
        if (-1 == id[w]) {
          dfs(g, w);
        }
      }
    }

    public boolean connected(int v, int w) {
      return id(v) == id(w);
    }

    public int count() { return count; }
    public int id(int v) { return id[v]; }
  }
}