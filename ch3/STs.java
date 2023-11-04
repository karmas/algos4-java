public class STs {
  public interface ST<K, V> {
    void put(K key, V val);
    V get(K key);
    void delete(K key);
    int size();
    Iterable<K> keys();
  }

  public static abstract class OrderedST<K extends Comparable<K>, V> implements ST<K, V>{
    public abstract K min();
    public abstract K max();
    public abstract K floor(K k);
    public abstract K ceiling(K k);
    public abstract int rank(K k); // num keys less than k
    public abstract K select(int rank);

    public void deleteMin() { delete(min()); }
    public void deleteMax() { delete(max()); }

    // [lo, hi] size
    public int size(K lo, K hi) {
      int c = lo.compareTo(hi);
      if (c >= 0) return 0;
      int s = rank(hi) - rank(lo);
      if (null != get(hi)) return s + 1;
      return s;
    }

    // [lo, hi] in sorted order
    public abstract Iterable<K> keys(K lo, K hi);

    public Iterable<K> keys() {
      return keys(min(), max());
    }
  }

  public static class LinkST<K, V> implements ST<K, V> {
    class Node {
      K key;
      V val;
      Node next;

      public String toString() {
        return key + "->" + val;
      }
    }

    private Node root;
    private int size;

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("n=").append(size);
      for (Node p = root; p != null; p = p.next) {
        sb.append(" ").append(p.toString());
      }
      return sb.toString();
    }

    private Node find(K key) {
      for (Node i = root; i != null; i = i.next) {
        if (i.key.equals(key)) return i;
      }
      return null;
    }

    public void put(K key, V val) {
      Node p = find(key);
      if (null != p) {
        p.val = val;
        return;
      }

      p = new Node();
      p.key = key;
      p.val = val;
      p.next = root;

      root = p;
      ++size;
    }

    public V get(K key) {
      Node p = find(key);
      if (null == p) return null;
      else return p.val;
    }

    public void delete(K key) {
      Node prev = null;
      Node p;
      for (p = root; p != null; p = p.next) {
        if (key.equals(p.key)) {
          break;
        }
        prev = p;
      }

      if (null == p) return;

      if (null != prev) prev.next = p.next;
      else root = p.next;
      --size;
    }

    public int size() { return size; }

    public Iterable<K> keys() {
      return null;
    }
  }

  public static class ArrayOrderedST<K extends Comparable<K>, V> implements ST<K, V> {
    K[] keys;
    V[] vals;
    int size;

    @SuppressWarnings("unchecked")
    public ArrayOrderedST() {
      int n = 10;
      keys = (K[])new Comparable[n];
      vals = (V[])new Object[n];
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < size; i++) {
        sb.append(' ').append(keys[i]).append("->").append(vals[i]);
      }
      return sb.toString();
    }

    public void put(K key, V val) {
      int i = size;
      while (i > 0 && key.compareTo(keys[i-1]) < 0) {
        keys[i] = keys[i-1];
        vals[i] = vals[i-1];
        --i;
      }

      keys[i] = key;
      vals[i] = val;
      ++size;
    }

    private int find(K key) {
      int l = 0;
      int h = size - 1;
      while (l <= h) {
        int m = l + (h - l)/2;
        int c = key.compareTo(keys[m]);
        if (c == 0) return m;
        else if (c < 0) h = m - 1;
        else l = m + 1;
      }
      return -1;
    }

    public V get(K key) {
      int i = find(key);
      if (-1 == i) return null;
      return vals[i];
    }

    public void delete(K key) {
      int i = find(key);
      if (-1 == i) return;

      while (i < size - 1) {
        keys[i] = keys[i+1];
        keys[i] = keys[i+1];
      }

      --size;
    }

    public int size() { return size; }

    public Iterable<K> keys() {
      return null;
    }
  }

  public static class BSTree<K extends Comparable<K>, V> extends OrderedST<K, V> {
    public static final boolean RED = true;
    public static final boolean BLACK = false;

    protected class Node {
      K key;
      V val;
      Node left;
      Node right;
      int size;
      boolean color; // true is red, false is black

      public Node(K k, V v, int s) {
        this(k, v, s, BLACK);
      }

      public Node(K k, V v, int s, boolean c) {
        key = k;
        val = v;
        size = s;
        color = c;
      }
    }

    private Node root;

    public void put(K key, V val) { root = put(root, key, val); }
    Node put(Node p, K key, V val) {
      if (null == p) return new Node(key, val, 1);
      int c = key.compareTo(p.key);
      if (c == 0) p.val = val;
      else if (c < 0) p.left = put(p.left, key, val);
      else p.right = put(p.right, key, val);
      p.size = size(p.left) + size(p.right) + 1;
      return p;
    }

    public V get(K key) {
      Node p = get(root, key);
      return null == p ? null : p.val;
    }
    public Node get(Node p, K key) {
      if (null == p) return null;
      int c = key.compareTo(p.key);
      if (c == 0) return p;
      else if (c < 0) return get(p.left, key);
      else return get(p.right, key);
    }

    public void delete(K key) {
      root = delete(root, key);
    }

    public Node delete(Node p, K key) {
      if (null == p) return null;
      int c = key.compareTo(p.key);
      if (c < 0) p.left = delete(p.left, key);
      else if (c > 0) p.right = delete(p.right, key);
      else {
        if (null == p.left) return p.right;
        if (null == p.right) return p.left;

        Node t = p;
        p = min(t.right);
        p.right = deleteMin(t.right);
        p.left = t.left;
      }
      p.size = size(p.left) + size(p.right) + 1;
      return p;
    }

    Node deleteMin(Node p) {
      if (null == p) return null;
      else if (null == p.left) return p.right;
      p.left = deleteMin(p.left);
      p.size = size(p.left) + size(p.right) + 1;
      return p;
    }

    public int size() { return size(root); }
    public int size(Node p) {
      if (null == p) return 0;
      return p.size;
    }

    public K min() {
      Node p = min(root);
      return p == null ? null : p.key;
    }
    Node min(Node p) {
      if (null == p) return null;
      else if (p.left == null) return p;
      return min(p.left);
    }

    public K max() {
      Node p = root;
      while (p != null && p.right != null) p = p.right;
      return null == p ? null : p.key;
    }

    public K floor(K k) { return floor(root, k); }
    private K floor(Node p, K k) {
      if (null == p) return null;
      int c = k.compareTo(p.key);
      if (0 == c) return p.key;
      if (c < 0) return floor(p.left, k);
      K f = floor(p.right, k);
      if (null != f) return f;
      return p.key;
    }

    public K ceiling(K k) { return ceiling(root, k); }
    public K ceiling(Node p, K k) {
      if (null == p) return null;
      int c = k.compareTo(p.key);
      if (0 == c) return p.key;

      if (c < 0) {
        K rk = ceiling(p.left, k);
        if (null == rk) return p.key;
        if (p.key.compareTo(rk) < 0) return p.key;
        else return rk;
      } else return ceiling(p.right, k);
    }

    public int rank(K k) { return rank(root, k); }
    int rank(Node p, K k) {
      if (null == p) return 0;
      int c = k.compareTo(p.key);
      if (c == 0) return size(p.left);
      else if (c < 0) return rank(p.left, k);
      else return rank(p.right, k) + size(p.left) + 1;
    }

    public K select(int rank) { return select(root, rank); }
    K select(Node p, int rank) {
      if (null == p) return null;
      if (size(p.left) == rank) return p.key;
      if (size(p.left) < rank) return select(p.right, rank - size(p.left) - 1);
      else return select(p.left, rank);
    }

    public Iterable<K> keys(K lo, K hi) {
      return null;
    }
  }

  public static class RBTree<K extends Comparable<K>, V> extends BSTree<K, V> {
    boolean isRed(Node x) {
      if (null == x) return false;
      return x.color == RED;
    }

    Node rotateLeft(Node h) {
      if (null == h) return null;
      if (null == h.right) return h;
      Node x = h.right;
      h.right = x.left;
      x.left = h;
      x.color = h.color;
      h.color = RED;
      x.size = h.size;
      h.size = size(h.left) + size(h.right) + 1;
      return x;
    }

    Node rotateRight(Node h) {
      if (null == h) return null;
      if (null == h.left) return h;
      Node x = h.left;
      h.left = x.right;
      x.right = h;
      x.color = h.color;
      h.color = RED;
      x.size = h.size;
      h.size = size(h.left) + size(h.right) + 1;
      return x;
    }

    void flipColors(Node h) {
      h.color = RED;
      h.left.color = BLACK;
      h.right.color = BLACK;
    }

    Node put(Node p, K key, V val) {
      if (null == p) return new Node(key, val, 1, RED);
      int c = key.compareTo(p.key);
      if (c == 0) p.val = val;
      else if (c < 0) p.left = put(p.left, key, val);
      else p.right = put(p.right, key, val);

      if (isRed(p.right) && !isRed(p.left)) p = rotateLeft(p);
      if (isRed(p.left) && isRed(p.left.left)) p = rotateRight(p);
      if (isRed(p.left) && isRed(p.right)) flipColors(p);

      p.size = size(p.left) + size(p.right) + 1;
      return p;
    }
  }

  public static class SepChainHashST<K extends Comparable<K>, V> implements ST<K, V> {
    private LinkST<K, V>[] arr;
    private int size;

    @SuppressWarnings("unchecked")
    public SepChainHashST(int m) {
      arr = (LinkST<K, V>[]) new LinkST[m];
      for (int i = 0; i < arr.length; i++) {
        arr[i] = new LinkST<>();
      }
    }

    private int hash(K k) {
      return k.hashCode() % arr.length;
    }

    public void put(K key, V val) {
      LinkST<K, V> l = arr[hash(key)];
      size -= l.size;
      l.put(key, val);
      size += l.size;
    }

    public V get(K key) {
      LinkST<K, V> l = arr[hash(key)];
      return l.get(key);
    }

    public void delete(K key) {
      LinkST<K, V> l = arr[hash(key)];
      size -= l.size;
      l.delete(key);
      size += l.size;
    }

    public int size() { return size; }

    public Iterable<K> keys() {
      return null;
    }
  }

  public static class LinProbeHashST<K extends Comparable<K>, V> implements ST<K, V> {
    private int size;
    private K[] keys;
    private V[] vals;

    @SuppressWarnings("unchecked")
    public LinProbeHashST(int m) {
      keys = (K[]) new Comparable[m];
      vals = (V[]) new Comparable[m];
    }

    private int hash(K k) { return k.hashCode() % keys.length; }

    public void put(K key, V val) {
      int i;
      for (i = hash(key); keys[i] != null; i = ++i % keys.length) {
        if (keys[i].equals(key)) {
          vals[i] = val;
          return;
        }
      }

      keys[i] = key;
      vals[i] = val;
      ++size;
    }

    public V get(K key) {
      int i;
      for (i = hash(key); keys[i] != null; i = ++i % keys.length) {
        if (keys[i].equals(key)) return vals[i];
      }
      return null;
    }

    public void delete(K key) {
      int i;
      boolean found = false;
      for (i = hash(key); keys[i] != null; i = ++i % keys.length) {
        if (keys[i].equals(key)) {
          found = true;
          break;
        }
      }
      if (!found) return;

      keys[i] = null;
      vals[i] = null;
      --size;
      i = ++i % keys.length;

      while (keys[i] != null) {
        K k = keys[i];
        V v = vals[i];
        keys[i] = null;
        vals[i] = null;
        --size;
        put(k, v);
        i = ++i % keys.length;
      }
    }

    public int size() { return size; }

    public Iterable<K> keys() {
      return null;
    }
  }
}