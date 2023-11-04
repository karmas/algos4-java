import java.util.ArrayList;
import java.util.List;

public class TrieTest {
  static void trieTest() {
    Trie.TrieST<Integer> trie = new Trie.TrieST<>();

    assert 0 == trie.size();
    assert null == trie.get("one");

    trie.put("one", 1);
    assert 1 == trie.get("one");
    trie.put("on", 2);
    assert 2 == trie.get("on");
    assert null == trie.get("o");

    trie.put("sin", 3);
    assert 3 == trie.get("sin");
    trie.put("sing", 4);
    assert 4 == trie.get("sing");
    assert null == trie.get("s");
    assert null == trie.get("si");

    assert 4 == trie.size() : "4 != " + trie.size();

    List<String> keys = new ArrayList<>();
    for (String s : trie.keys()) keys.add(s);
    List<String> expKeys = List.of("on", "one", "sin", "sing");
    assert expKeys.equals(keys) : expKeys + " != " + keys;

    List<String> kprefix = new ArrayList<>();
    for (String s : trie.keysWithPrefix("si")) kprefix.add(s);
    List<String> expKP = List.of("sin", "sing");
    assert kprefix.equals(expKP) : expKP + " != " + kprefix;

    List<String> kmatch1 = new ArrayList<>();
    for (String s : trie.keysThatMatch("o.e")) kmatch1.add(s);
    List<String> expKM1 = List.of("one");
    assert kmatch1.equals(expKM1) : expKM1 + " != " + kmatch1;

    List<String> kmatch2 = new ArrayList<>();
    for (String s : trie.keysThatMatch("..n")) kmatch2.add(s);
    List<String> expKM2 = List.of("sin");
    assert kmatch2.equals(expKM2) : expKM2 + " != " + kmatch2;

    trie.del("si");
    assert 4 == trie.size() : "4 != " + trie.size();
    trie.del("sin");
    assert 3 == trie.size() : "3 != " + trie.size();
    assert null == trie.get("sin");

    System.out.println("Trie test pass");
  }

  static void tstTest() {
    Trie.TST<Integer> trie = new Trie.TST<>();

    assert 0 == trie.size();
    assert null == trie.get("one");

    trie.put("one", 1);
    assert 1 == trie.get("one");
    trie.put("on", 2);
    assert 2 == trie.get("on");
    assert null == trie.get("o");

    trie.put("sin", 3);
    assert 3 == trie.get("sin");
    trie.put("sing", 4);
    assert 4 == trie.get("sing");
    assert null == trie.get("s");
    assert null == trie.get("si");

    assert 4 == trie.size() : "4 != " + trie.size();

    /*
    List<String> keys = new ArrayList<>();
    for (String s : trie.keys()) keys.add(s);
    List<String> expKeys = List.of("on", "one", "sin", "sing");
    assert expKeys.equals(keys) : expKeys + " != " + keys;

    List<String> kprefix = new ArrayList<>();
    for (String s : trie.keysWithPrefix("si")) kprefix.add(s);
    List<String> expKP = List.of("sin", "sing");
    assert kprefix.equals(expKP) : expKP + " != " + kprefix;

    trie.del("si");
    assert 4 == trie.size() : "4 != " + trie.size();
    trie.del("sin");
    assert 3 == trie.size() : "3 != " + trie.size();
    assert null == trie.get("sin");
    */

    System.out.println("TST test pass");
  }

  public static void main(String[] args) {
    trieTest();
    tstTest();
  }
}
