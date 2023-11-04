import java.util.ArrayList;
import java.util.List;

public class Trie {
  public static class TrieST<V> {
    static final int R = 256;

    static class Node {
      Object val;
      Node[] links = new Node[R];
    }

    private int size;
    private Node root; 

    public int size() {
      return size;
    }

    @SuppressWarnings("unchecked")
    public V get(String k) {
      Node p = get(root, k, 0);
      return null == p ? null : (V) p.val;
    }

    private Node get(Node p, String k, int d) {
      if (null == p) return null;
      if (k.length() == d) return p;

      p = p.links[k.charAt(d)];
      return get(p, k, d + 1);
    }

    public void put(String k, V v) {
      root = putNode(root, k, v, 0);
    }

    private Node putNode(Node p, String k, V v, int d) {
      if (null == p) p = new Node();
      if (k.length() == d) {
        if (null == p.val) ++size;
        p.val = v;
        return p;
      }

      int i = k.charAt(d);
      p.links[i] = putNode(p.links[i], k, v, d + 1);
      return p;
    }
  
    public Iterable<String> keys() {
      return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String s) {
      List<String> k = new ArrayList<>();
      Node p = get(root, s, 0);
      collect(p, s, k);
      return k;
    }

    private void collect(Node o, String path, List<String> keys) {
      if (null == o) return;
      if (null != o.val) keys.add(path);
      for (int a = 0; a < R; ++a) {
        char c = (char)a;
        collect(o.links[a], path + c, keys);
      }
    }
    
    public Iterable<String> keysThatMatch(String s) {
      List<String> res = new ArrayList<>();
      collect(root, "", s, res);
      return res;
    }

    private void collect(Node o, String pre, String pat, List<String> keys) {
      if (null == o) return;
      if (pre.length() == pat.length()) {
        if (o.val != null) keys.add(pre);
        return;
      }
      int ch = pat.charAt(pre.length());
      for (char i = 0; i < R; i++) {
        if (ch == '.' || ch == i) collect(o.links[i], pre + i, pat, keys);
      }
    }

    public void del(String k) {
      root = del(root, k, 0);
    }

    private Node del(Node x, String k, int d) {
      if (null == x) return null;
      if (k.length() == d) {
        if (x.val != null) --size;
        x.val = null;
      } else {
        int i = k.charAt(d);
        x.links[i] = del(x.links[i], k, d + 1);
      }

      if (x.val != null) return x;
      for (int a = 0; a < R; ++a) {
        if (x.links[a] != null) return x;
      }
      return null;
    }
  }

  public static class TST<V> {
    static class Node {
      char ch;
      Object val;
      Node[] links = new Node[3];
    }

    private Node root;
    private int size;

    public int size() {
      return size;
    }

    @SuppressWarnings("unchecked")
    public V get(String k) {
      Node o = getNode(root, k, 0);
      return null == o ? null : (V) o.val;
    }

    private Node getNode(Node o, String k, int d) {
      if (null == o) return null;
      if (k.length() - 1 == d) return o;

      char kc = k.charAt(d);
      if (kc < o.ch) {
        return getNode(o.links[0], k, d);
      } else if (kc > o.ch) {
        return getNode(o.links[2], k, d);
      } else {
        return getNode(o.links[1], k, d + 1);
      }
    }

    public void put(String k, V v) {
      root = putNode(root, k, v, 0);
    }

    private Node putNode(Node o, String k, V v, int d) {
      if (null == o) {
        o = new Node();
        o.ch = k.charAt(d);
      }
      if (d == k.length() - 1) {
        if (null == o.val) ++size;
        o.val = v;
        return o;
      }

      char kc = k.charAt(d);
      if (kc < o.ch) {
        o.links[0] = putNode(o.links[0], k, v, d);
      } else if (kc > o.ch) {
        o.links[2] = putNode(o.links[2], k, v, d);
      } else {
        o.links[1] = putNode(o.links[1], k, v, d + 1);
      }
      return o;
    }
  }
}
