public class Sort {
  @SuppressWarnings("unchecked")
  public static <T> boolean less(Comparable<T> a, Comparable<T> b) {
    return a.compareTo((T)b) < 0;
  }

  public static <T> void exch(Comparable<T>[] a, int i, int j) {
    Comparable<T> t = a[i];
    a[i] = a[j];
    a[j] = t;
  }

  public static <T> String toString(Comparable<T>[] a) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < a.length; i++) {
      if (i > 0) sb.append(',');
      sb.append(a[i]);
    }
    return sb.toString();
  }

  public static <T> void selection(Comparable<T>[] a) {
    for (int i = 0; i < a.length; i++) {
      int s = i;
      for (int j = i + 1; j < a.length; j++) {
        if (less(a[j], a[s])) s = j;
      }
      exch(a, i, s);
    }
  }

  public static <T> void insertion(Comparable<T>[] a) {
    // [0, i)
    for (int i = 1; i < a.length; i++) {
      int j = i;
      Comparable<T> v = a[j];
      while (j > 0 && less(v, a[j-1])) {
        //exch(a, j, j-1);
        a[j] = a[j-1];
        --j;
      }
      a[j] = v;
    }
  }

  public static <T> void shell(Comparable<T>[] a) {
    for (int h = a.length / 3; h > 0; h /= 2) {
      // [0, i) is sorted for every hth
      for (int i = h; i < a.length; ++i) {
        int j = i;
        while (j >= h  && less(a[j], a[j - h])) {
          exch(a, j, j - h);
          j -= h;
        }
      }
    }
  }

  public static <T> void merge(Comparable<T>[] a) {
    merge(a, 0, a.length - 1);
  }

  static <T> void merge(Comparable<T>[] a, int l, int h) {
    if (l >= h) return;
    int m = l + (h - l)/2;
    merge(a, l, m);
    merge(a, m + 1, h);
    merge(a, l, m, h);
  }

  @SuppressWarnings("unchecked")
  static <T> void merge(Comparable<T>[] a, int l, int m, int h) {
    int size = h - l + 1;
    Comparable<T>[] b = (Comparable<T>[]) new Comparable[size];

    int p = l;
    int q = m + 1;
    for (int i = 0; i < b.length; i++) {
      if (p <= m && q <= h) {
        if (less(a[p], a[q])) b[i] = a[p++];
        else b[i] = a[q++];
      } else if (p <= m) b[i] = a[p++];
      else b[i] = a[q++];
    }

    for (int i = 0; i < b.length; i++) a[l + i] = b[i];
  }

  public static <T> void mergeBU(Comparable<T>[] a) {
    for (int sz = 1; sz < a.length; sz *= 2) {
      for (int l = 0; l < a.length - sz; l += sz * 2) {
        int h = l + (sz * 2) - 1;
        h = Math.min(h, a.length - 1);
        merge(a, l, l + sz - 1, h);
      }
    }
  }

  public static <T> void quick(Comparable<T>[] a) {
    quick(a, 0, a.length - 1);
  }

  static <T> void quick(Comparable<T>[] a, int l, int h) {
    if (l >= h) return;
    int m = partition(a, l, h);
    quick(a, l, m - 1);
    quick(a, m + 1, h);
  }

  static <T> int partition(Comparable<T>[] a, int l, int h) {
    // [0, j) <= m
    // [j, i) > m
    int j = l;
    for (int i = l; i < h; i++) {
      if (less(a[i], a[h])) exch(a, i, j++);
    }
    exch(a, j, h);
    return j;
  }

  public static <T> void quick3way(Comparable<T>[] a) {
    quick3way(a, 0, a.length - 1);
  }

  @SuppressWarnings("unchecked")
  static <T> void quick3way(Comparable<T>[] a, int l, int h) {
    if (l >= h) return;

    // [l, j) < m
    // [j, i) = m
    // [g, h) > m
    int j = l;
    int i = l;
    int g = h;
    while (i < g) {
      int c = a[i].compareTo((T) a[h]);
      if (c < 0) exch(a, i++, j++);
      else if (c > 0) exch(a, i, --g);
      else ++i;
    }
    exch(a, i, h);

    quick3way(a, l, j - 1);
    quick3way(a, g + 1, h);
  }

  public static int heapParent(int i) {
    return (i+1)/2 - 1;
  }

  public static int heapChild(int i) {
    return (i+1)*2 - 1;
  }

  public static <T> void heapSink(Comparable<T>[] a, int size, int i) {
    while (heapChild(i) < size) {
      int c = heapChild(i);
      if (c + 1 < size && less(a[c], a[c + 1]))
        c += 1;
      if (less(a[c], a[i]))
        break;
      exch(a, i, c);
      i = c;
    }
  }

  public static <T> void heap(Comparable<T>[] a) {
    for (int i = a.length / 2; i >= 0; i--) {
      heapSink(a, a.length, i);
    }

    for (int i = a.length - 1; i >= 0; i--) {
      exch(a, 0, i);
      heapSink(a, i, 0);
    }
  }
}