import java.util.Iterator;

public class Stacks {
  interface Stack<T> extends Iterable<T>{
    int size();
    void push(T v);
    T pop();
    Iterator<T> iterator();
  }

  public static class ResizingArray<T> implements Stack<T> {
    T[] a;
    int n;

    @SuppressWarnings("unchecked")
    public ResizingArray() {
      a = (T[]) new Object[1];
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("cap=").append(a.length);
      sb.append(" n=").append(n).append(' ');
      for (int i = 0; i < n; i++) {
        sb.append(',').append(a[i]);
      }
      return sb.toString();
    }

    public int size() {
      return n;
    }

    public void push(T v) {
      if (a.length == n)
        resize(n * 2);
      a[n++] = v;
    }

    @SuppressWarnings("unchecked")
    void resize(int newCap) {
      assert newCap >= n;
      T[] b = (T[]) new Object[newCap];
      for (int i = 0; i < n; i++)
        b[i] = a[i];
      a = b;
    }

    public T pop() {
      if (n > 0 && n == a.length / 4)
        resize(n * 2);
      T v = (T) a[--n];
      a[n] = null;
      return v;
    }

    @Override
    public Iterator<T> iterator() {
      return new Iter();
    }

    public class Iter implements Iterator<T> {
      int i = n;

      @Override
      public boolean hasNext() {
        return i != 0;
      }

      @Override
      public T next() {
        return a[--i];
      }
    }
  }

  static class Linked<T> implements Stack<T> {
    private class Node {
      T val;
      Node next;
    }

    Node top;
    int n;

    public String toString() {
      StringBuilder sb = new StringBuilder();
      for (Node i = top; i != null; i = i.next) {
        sb.append(',');
        sb.append(i.val);
      }
      return sb.toString();
    }

    @Override
    public int size() { return n; }

    @Override
    public void push(T v) {
      Node a = new Node();
      a.val = v;
      a.next = top;
      top = a;
      ++n;
    }

    @Override
    public T pop() {
      T v = top.val;
      top = top.next;
      --n;
      return v;
    }

    @Override
    public Iterator<T> iterator() {
      return new Iter();
    }

    class Iter implements Iterator<T> {
      Node i = top;

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
