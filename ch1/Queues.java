import java.util.Iterator;

public class Queues {
  interface Queue<T> extends Iterable<T> {
    int size();
    void enqueue(T v);
    T dequeue();
    Iterator<T> iterator();
  }

  public static class ResizingArray<T> implements Queue<T> {
    T[] a;
    int head = -1;
    int tail = -1;

    @SuppressWarnings("unchecked")
    public ResizingArray() {
      a = (T[]) new Object[1];
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("cap=").append(a.length);
      int n = size();
      sb.append(" n=").append(n).append(' ');
      for (int i = head; n-- > 0; i = nextIndex(i)) {
        sb.append(',').append(a[i]);
      }
      return sb.toString();
    }

    int nextIndex(int i) { return i == a.length - 1 ? 0 : i + 1; }

    public int size() {
      if (head == -1) return 0;
      return tail >= head ? tail - head + 1: (tail + 1) + (a.length - head);
    }

    public void enqueue(T v) {
      int n = size();
      if (n == a.length) resize(n * 2);

      tail = nextIndex(tail);
      if (-1 == head) head = tail;
      a[tail] = v;
    }

    @SuppressWarnings("unchecked")
    void resize(int newCap) {
      int n = size();
      assert newCap >= n;
      T[] b = (T[]) new Object[newCap];

      for (int i = head, j = 0; n-- > 0; i = nextIndex(i), ++j) {
        b[j] = a[i];
      }

      a = b;
      head = 0;
      tail = size() - 1;
    }

    public T dequeue() {
      int n = size();
      if (n > 0 && n == a.length / 4) resize(a.length / 2);

      T v = a[head];
      a[head] = null;
      head = nextIndex(head);
      if (n == 1) head = tail = -1;
      return v;
    }

    @Override
    public Iterator<T> iterator() {
      return new Iter();
    }

    class Iter implements Iterator<T> {
      int i = head;
      int n = size();

      @Override
      public boolean hasNext() {
        return n-- > 0;
      }

      @Override
      public T next() {
        T v = a[i];
        i = nextIndex(i);
        return v;
      }
    }
  }

  public static class Linked<T> implements Queue<T> {
    private class Node {
      T val;
      Node next;
    }

    Node first;
    Node last;
    int n;

    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (Node i = first; i != null; i = i.next) sb.append(',').append(i.val);
      return sb.toString();
    }

    @Override
    public int size() { return n; }

    @Override
    public void enqueue(T v) {
      Node a = new Node();
      a.val = v;

      if (null == first) first = a;
      else last.next = a;
      last = a;

      ++n;
    }

    @Override
    public T dequeue() {
      if (first == null) return null;
      T v = first.val;
      first = first.next;
      if (first == null) last = null;
      --n;
      return v;
    }

    @Override
    public Iterator<T> iterator() {
      return new Iter();
    }

    public class Iter implements Iterator<T> {
      Node i = first;

      @Override
      public boolean hasNext() {
        return i != null;
      }

      @Override
      public T next() {
        T v = i.val;
        i = i.next;
        return v;
      }
    }
  }
}
