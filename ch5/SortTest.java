import java.util.Arrays;

class SortTest {
  static void keyIndexedSortTest() {
    int[] arr = {2, 3, 3, 4, 1, 3, 4, 3, 1};
    int[] exp = Arrays.copyOf(arr, arr.length);
    Arrays.sort(exp);
    Sort.keyIndexedSort(arr, 4);
    String expStr = Sort.toString(exp);
    String gotStr = Sort.toString(arr);
    assert expStr.equals(gotStr) : expStr + " != " + gotStr;
    System.out.println("keyIndexedSort pass");
  }

  static void lsdSortTest() {
    String[] arr = {
      "4PGC938",
      "2IYE230",
      "3CIO720",
      "1ICK750",
      "1OHV845",
      "4JZY524",
      "1ICK750",
      "3CIO720",
      "1OHV845",
      "1OHV845",
      "2RLA629",
      "2RLA629",
      "3ATW723"
    };

    String[] exp = Arrays.copyOf(arr, arr.length);
    Arrays.sort(exp);
    Sort.lsdSort(arr);

    String expStr = Sort.toString(exp);
    String gotStr = Sort.toString(arr);
    assert expStr.equals(gotStr) : expStr + " != " + gotStr;
    System.out.println("lsdSort pass");
  }

  static void msdSortTest() {
    String[] arr = {
      "she", "sells", "seashells", "by", "the", "seashore", "the", "shells",
      "she", "sells", "are", "surely", "seashells"
    };
    String[] exp = Arrays.copyOf(arr, arr.length);
    Arrays.sort(exp);
    Sort.msdSort(arr);

    String expStr = Sort.toString(exp);
    String gotStr = Sort.toString(arr);
    assert expStr.equals(gotStr) : expStr + " != " + gotStr;
    System.out.println("lsdSort pass");
  }

  static void qsort3wayTest() {
    String[] arr = {
      "she", "sells", "seashells", "by", "the", "seashore", "the", "shells",
      "she", "sells", "are", "surely", "seashells"
    };
    String[] exp = Arrays.copyOf(arr, arr.length);
    Arrays.sort(exp);
    Sort.qsort3way(arr);

    String expStr = Sort.toString(exp);
    String gotStr = Sort.toString(arr);
    assert expStr.equals(gotStr) : expStr + " != " + gotStr;
    System.out.println("qsort3way pass");
  }

  public static void main(String[] args) {
    keyIndexedSortTest();
    lsdSortTest();
    qsort3wayTest();
  }
}