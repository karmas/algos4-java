public class PQs {
  public interface PQ<T extends Comparable<T>> {
    public T getHigh();
    public T delHigh();
    public void insert(T v);
    public int size();
  }

  public static class MaxPQArray<T extends Comparable<T>> implements PQ<T> {
    private T[] arr;
    private int size;

    public MaxPQArray() {
      this(1);
    }

    @SuppressWarnings("unchecked")
    public MaxPQArray(int max) {
      arr = (T[]) new Comparable[max];
    }

    public MaxPQArray(T[] a) {
      this(a.length);
      for (int i = 0; i < a.length; i++) {
        insert(a[i]);
      }
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("n=").append(size).append(" arr=");
      for (int i = 0; i < size; i++) {
        sb.append(' ').append(arr[i]);
      }
      return sb.toString();
    }

    public T getHigh() {
      return arr[size - 1];
    }

    public T delHigh() {
      T h = arr[size-- - 1];
      arr[size] = null;
      return h;
    }

    public void insert(T v) {
      if (size == arr.length) resize(size * 2);

      int i = size;
      while (i > 0 && Sort.less(v, arr[i-1])) {
        arr[i] = arr[i-1];
        --i;
      }

      arr[i] = v;
      ++size;
    }

    @SuppressWarnings("unchecked")
    private void resize(int n) {
      assert n >= size;
      T[] b = (T[]) new Comparable[n];
      for (int i = 0; i < size; i++) b[i] = arr[i];
      arr = b;
    }

    public int size() { return size; }
  }

  public static class MaxPQLink<T extends Comparable<T>> implements PQ<T> {
    class Node {
      T val;
      Node next;
    }

    private Node top;
    private int size;

    public MaxPQLink() {
    }

    public MaxPQLink(T[] a) {
      for (int i = 0; i < a.length; i++) {
        insert(a[i]);
      }
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("n=").append(size).append(" vals=");
      for (Node p = top; p != null; p = p.next) {
        sb.append(' ').append(p.val);
      }
      return sb.toString();
    }

    public T getHigh() {
      return top.val;
    }

    public T delHigh() {
      T h = top.val;
      top = top.next;
      --size;
      return h;
    }

    public void insert(T v) {
      Node p = new Node();
      p.val = v;

      Node prev = null;
      Node next = null;

      for (prev = top; prev != null; prev = prev.next) {
        if (Sort.less(v, prev.val)) {
          next = prev;
        } else break;
      }

      p.next = prev;
      if (null != next) next.next = p;
      else top = p;

      ++size;
    }

    public int size() { return size; }
  }

  public static class MaxPQ<T extends Comparable<T>> implements PQ<T> {
    private T[] arr;
    private int size;

    public MaxPQ() {
      this(10);
    }

    @SuppressWarnings("unchecked")
    public MaxPQ(int max) {
      arr = (T[]) new Comparable[max + 1]; 
    }

    public MaxPQ(T[] a) {
      for (int i = 0; i < a.length; i++) {
        insert(a[i]);
      }
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("n=").append(size).append(" vals=");
      for (int i = 1; i <= size; ++i) {
        sb.append(' ').append(arr[i]);
      }
      return sb.toString();
    }

    public T getHigh() {
      return arr[1];
    }

    public T delHigh() {
      T h = arr[1];
      Sort.exch(arr, 1, size--);
      sink(1);
      return h;
    }

    private void sink(int i) {
      while (2*i <= size) {
        int c = 2*i;
        if (c + 1 <= size && Sort.less(arr[c], arr[c+1])) c+= 1;
        if (Sort.less(arr[c], arr[i])) break;
        Sort.exch(arr, i, c);
        i = c;
      }
    }

    public void insert(T v) {
      arr[++size] = v;
      swim(size);
    }

    private void swim(int i) {
      while (i > 1 && Sort.less(arr[i/2], arr[i])) {
        Sort.exch(arr, i, i/2);
        i = i/2;
      }
    }

    public int size() { return size; }
  }

  public static class IndexMinPQ<Item extends Comparable<Item>> {
    private int size;
    private Item[] vals;
    private int[] pq; // binary heap
    private int[] qp; // pq[i] = j then pq[j] = i

    @SuppressWarnings("unchecked")
    public IndexMinPQ(int n) {
      int m = n + 1;
      vals = (Item[]) new Comparable[m];
      pq = new int[m];
      qp = new int[m];
      for (int i = 0; i < qp.length; i++) {
        qp[i] = -1;
      }
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("size=").append(size).append("\n");
      for (int i = 0; i < vals.length; i++) {
        sb.append(String.format("%5d", i));
      }
      sb.append('\n');
      for (int i = 0; i < vals.length; i++) {
        sb.append(String.format("%5d", pq[i]));
      }
      sb.append('\n');
      for (int i = 0; i < vals.length; i++) {
        sb.append(String.format("%5d", qp[i]));
      }
      sb.append('\n');
      for (int i = 0; i < vals.length; i++) {
        sb.append(String.format("%5d", vals[i]));
      }
      sb.append('\n');
      return sb.toString();
    }

    private void exch(int i, int j) {
      int t = pq[i];
      pq[i] = pq[j];
      pq[j] = t;
    }

    private boolean less(int i, int j) {
      return vals[pq[i]].compareTo((Item) vals[pq[j]]) < 0;
    }

    private void swim(int i) {
      while (i/2 >= 1 && less(i, i/2)) {
        exch(i, i/2);
        i = i/2;
      }
    }

    private void sink(int i) {
      while (i*2 <= size) {
        int c = i*2;
        if (c+1 <= size && less(c+1, c)) c+=1;
        if (less(i, c)) break;
        exch(i, c);
        i = c;
      }
    }

    public void insert(int k, Item v) {
      pq[++size] = k;
      vals[k] = v;
      qp[k] = size;
      swim(size);
    }

    boolean contains(int k) {
      return qp[k] != -1;
    }

    Item min() {
      return vals[pq[1]];
    }

    int minIndex() {
      return pq[1];
    }

    int delMin() {
      int i = pq[1];
      exch(1, size);
      --size;
      sink(1);
      vals[pq[size + 1]] = null;
      qp[pq[size + 1]] = -1;
      return i;
    }

    int size() { return size; }
  }
}
