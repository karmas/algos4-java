public class UF {
  public interface UnionFind {
    void union(int p, int q);
    int find(int p); // component
    boolean connected(int p, int q);
    int count(); // component
  }

  public static class QuickFind implements UnionFind {
    int[] id;
    int count;

    public QuickFind(int n) {
      id = new int[n];
      for (int i = 0; i < id.length; i++) {
        id[i] = i;
      }
      count = n;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < id.length; i++) {
        if (sb.length() > 0) sb.append(' ');
        sb.append(i).append(')').append(id[i]);
      }
      return sb.toString();
    }

    public void union(int p, int q) {
      int pid = find(p);
      int qid = find(q);
      if (pid == qid) return;
      for (int i = 0; i < id.length; i++) {
        if (id[i] == pid) id[i] = qid;
      }
      --count;
    }

    public int find(int p) {
      return id[p];
    }

    public boolean connected(int p, int q) {
      return find(p) == find(q);
    }

    public int count() {
      return count;
    }
  }

  public static class QuickUnion implements UnionFind {
    int[] id;
    int count;

    public QuickUnion(int n) {
      id = new int[n];
      for (int i = 0; i < id.length; i++) {
        id[i] = i;
      }
      count = n;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < id.length; i++) {
        if (sb.length() > 0) sb.append(' ');
        sb.append(i).append(')').append(id[i]);
      }
      return sb.toString();
    }

    public void union(int p, int q) {
      int pid = find(p);
      int qid = find(q);
      if (pid == qid) return;

      id[pid] = qid;

      --count;
    }

    public int find(int p) {
      int c = id[p];
      while (c != id[c]) c = id[c];
      return c;
    }

    public boolean connected(int p, int q) {
      return find(p) == find(q);
    }

    public int count() {
      return count;
    }
  }

  public static class WeightQuickUnion implements UnionFind {
    int[] id;
    int[] size;
    int count;

    public WeightQuickUnion(int n) {
      id = new int[n];
      size = new int[n];
      for (int i = 0; i < id.length; i++) {
        id[i] = i;
        size[i] = 1;
      }
      count = n;
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < id.length; i++) {
        if (sb.length() > 0) sb.append(' ');
        sb.append(i).append(')').append(id[i]);
      }
      return sb.toString();
    }

    public void union(int p, int q) {
      int pid = find(p);
      int qid = find(q);
      if (pid == qid) return;

      if (size[pid] < size[qid]) {
        id[pid] = qid;
        size[qid] += size[pid];
      }
      else {
        id[qid] = pid;
        size[pid] += size[qid];
      }

      --count;
    }

    public int find(int p) {
      int c = id[p];
      while (c != id[c]) c = id[c];
      return c;
    }

    public boolean connected(int p, int q) {
      return find(p) == find(q);
    }

    public int count() {
      return count;
    }
  }
}