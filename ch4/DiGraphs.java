import java.util.LinkedList;

public class DiGraphs {

  public static class DiGraph {
    private final LinkedList<Integer>[] adj;
    private int E;

    @SuppressWarnings("unchecked")
    public DiGraph(int v) {
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
      ++E;
    }

    public Iterable<Integer> adj(int v) {
      return adj[v];
    }

    public DiGraph reverse() {
      DiGraph r = new DiGraph(V());
      for (int v = 0; v < adj.length; v++) {
        for (int w : adj(v)) {
          r.addEdge(w, v);
        }
      }
      return r;
    }
  }

  public static class DirectedDFS {
    private boolean[] marked;

    public DirectedDFS(DiGraph g, int s) {
      marked = new boolean[g.V()];
      dfs(g, s);
    }

    public DirectedDFS(DiGraph g, Iterable<Integer> sources) {
      marked = new boolean[g.V()];

      for (int s: sources) {
        if (!marked[s]) dfs(g, s);
      }
    }

    private void dfs(DiGraph g, int v) {
      marked[v] = true;
      for (int w : g.adj(v)) {
        if (!marked[w]) dfs(g, w);
      }
    }

    public boolean marked(int v) {
      return marked[v];
    }
  }

  public static class Cycle {
    private boolean[] marked;
    private int[] edgeTo;
    private LinkedList<Integer> cycle;
    private boolean[] onStack;

    public Cycle(DiGraph g) {
      marked = new boolean[g.V()];
      edgeTo = new int[g.V()];
      onStack = new boolean[g.V()];

      for (int v = 0; v < edgeTo.length; v++) {
        if (!marked[v]) dfs(g, v);
      }
    }

    private void dfs(DiGraph g, int v) {
      marked[v] = true;
      onStack[v] = true;

      for (int w : g.adj(v)) {
        if (hasCycle()) return;

        if (!marked[w]) {
          edgeTo[w] = v;
          dfs(g, w);
        }
        else if (onStack[w]) {
          cycle = new LinkedList<>();
          for (int i = v; i != w; i = edgeTo[i]) {
            cycle.add(i);
          }
          cycle.add(w);
          cycle.add(v);
        }
      }

      onStack[v] = false;
    }

    public boolean hasCycle() {
      return null != cycle;
    }

    public Iterable<Integer> cycle() {
      return cycle;
    }
  }
}