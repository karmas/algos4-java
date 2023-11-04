public class Sort {
  public static String toString(Object[] arr) {
    StringBuilder sb = new StringBuilder();
    for (Object o : arr) {
      if (sb.length() > 0) sb.append(',');
      sb.append(o);
    }
    return sb.toString();
  }

  public static String toString(int[] arr) {
    StringBuilder sb = new StringBuilder();
    for (Object o : arr) {
      if (sb.length() > 0) sb.append(',');
      sb.append(o);
    }
    return sb.toString();
  }

  public static void keyIndexedSort(int[] arr, int maxChars) {
    int[] counts = new int[maxChars + 2];
    for (int n : arr)
      counts[n + 1]++;
    for (int i = 0; i < counts.length - 1; ++ i)
      counts[i + 1] += counts[i];

    int[] aux = new int[arr.length];
    for (int n : arr)
      aux[counts[n]++] = n;
    for (int i = 0; i < arr.length; ++i)
      arr[i] = aux[i];
  }

  public static void lsdSort(String[] arr) {
    int w = arr[0].length();
    int r = 256;
    String[] aux = new String[arr.length];
    int[] count = new int[r + 2];

    for (int d = w - 1; d >= 0; --d) {
      for (int i = 0; i < count.length; ++i)
        count[i] = 0;
      for (String s : arr) {
        char c = s.charAt(d);
        int n = c;
        count[n + 1]++;
      }
      for (int i = 0; i < count.length - 1; ++i)
        count[i + 1] += count[i];

      for (String s : arr) {
        char c = s.charAt(d);
        int n = c;
        aux[count[n]++] = s;
      }
      for (int i = 0; i < arr.length; ++i)
        arr[i] = aux[i];
    }
  }

  public static int charAt(String s, int d) {
    if (d >= s.length()) return -1;
    return s.charAt(d) - 'a';
  }

  public static void msdSort(String[] arr) {
    String[] aux = new String[arr.length];
    for (int i = 0; i < arr.length; ++i) aux[i] = "";
    msdSort(arr, 0, arr.length - 1, 0, aux);
  }

  public static void msdSort(String[] arr, int lo, int hi, int d, String[] aux) {
    if (lo >= hi) return;

    int[] count = new int[26 + 2];
    for (int i = lo; i <= hi; ++i)
      count[charAt(arr[i], d) + 2]++;
    for (int i = 0; i < count.length - 1; ++i)
      count[i + 1] += count[i];

    for (int i = lo; i <= hi; ++i) {
      int c = charAt(arr[i], d) + 1;
      aux[count[c]++] = arr[i];
    }
    for (int i = lo; i <= hi; ++i)
      arr[i] = aux[i - lo];

    for (int i = 0; i < count.length; ++i)
      msdSort(arr, lo + count[i], lo + count[i + 1] - 1, d + 1, aux);
  }

  public static void qsort3way(String[] arr) {
    qsort3way(arr, 0, arr.length - 1, 0);
  }

  public static void swap(String[] arr, int i, int j) {
    String t = arr[i];
    arr[i] = arr[j];
    arr[j] = t;
  }

  public static void qsort3way(String[] arr, int lo, int hi, int pivot) {
    if (lo >= hi) return;

    int compare = charAt(arr[lo], pivot);
    int e = lo;
    int g = hi;
    /*
     * [lo, e) <
     * [e, i) ==
     * (g, hi] >
     */
    for (int i = lo + 1; i <= g; ) {
      int subj = charAt(arr[i], pivot);
      if (subj < compare) {
        swap(arr, i++, e++);
      } else if (subj > compare) {
        swap(arr, i, g--);
      } else {
        ++i;
      }
    }

    qsort3way(arr, lo, e - 1, pivot);
    if (compare >= 0) qsort3way(arr, e, g, pivot + 1);
    qsort3way(arr, g + 1, hi, pivot);
  }
}