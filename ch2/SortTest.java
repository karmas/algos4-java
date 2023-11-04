import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

public class SortTest {
  @SuppressWarnings("unchecked")
  public static <T> boolean isSorted(Comparable<T>[] a) {
    for (int i = 1; i < a.length; i++) {
      if (a[i].compareTo((T)a[i-1]) < 0) return false;
    }
    return true;
  }

  public static void testSort(Consumer<Comparable<Integer>[]> sorter, String name) {
    int bound = 100;
    Random rand = new Random();

    Integer[] a = new Integer[5];
    for (int i = 0; i < a.length; i++) a[i] = rand.nextInt(bound);
    Integer[] e = Arrays.copyOf(a, a.length);
    sorter.accept(a);

    String got = Sort.toString(a);
    assert isSorted(a) : "not sorted. " + got;
    Arrays.sort(e);
    String exp = Sort.toString(e);
    assert exp.equals(got) : exp + " != " + got;


    a = new Integer[6];
    for (int i = 0; i < a.length; i++) a[i] = rand.nextInt(bound);
    e = Arrays.copyOf(a, a.length);
    sorter.accept(a);

    got = Sort.toString(a);
    assert isSorted(a) : "not sorted. " + got;
    Arrays.sort(e);
    exp = Sort.toString(e);
    assert exp.equals(got) : exp + " != " + got;

    System.out.printf("%s pass\n", name);
  }

  public static void main(String[] args) {
    testSort(Sort::insertion, "insertion");
    testSort(Sort::selection, "selection");
    testSort(Sort::shell, "shell");
    testSort(Sort::merge, "merge");
    testSort(Sort::mergeBU, "mergeBU");
    testSort(Sort::quick, "quick");
    testSort(Sort::quick3way, "quick3way");
    testSort(Sort::heap, "heap");
  }
}
