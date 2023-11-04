import java.util.function.BiFunction;

public class SearchTest {
  static void run(String name, BiFunction<String, String, Integer> search) {
    String txt = "abracadabbra";

    int got = search.apply(txt, "z");
    int exp = txt.length();
    assert exp == got : exp + " != " + got;
    got = search.apply(txt, "ade");
    assert exp == got : exp + " != " + got;
    got = search.apply(txt, "rat");
    assert exp == got : exp + " != " + got;

    String pat = "dab";
    got = search.apply(txt, pat);
    exp = txt.indexOf(pat);
    assert exp == got : exp + " != " + got;

    pat = "abr";
    got = search.apply(txt, pat);
    exp = txt.indexOf(pat);
    assert exp == got : exp + " != " + got;

    pat = "abbra";
    got = search.apply(txt, pat);
    exp = txt.indexOf(pat);
    assert exp == got : exp + " != " + got;

    System.out.println(name + " pass");
  }

  public static void main(String[] args) {
    run("brute", Search::brute);
    run("brute backup", Search::bruteBackup);
    run("kmp", Search::kmp);
    run("bm", Search::bm);
  }
}
