import java.util.ArrayList;

public class UFTest {
  static void testUF(UF.UnionFind u) {
    ArrayList<String> conns = new ArrayList<>();
    conns.add("4,3");
    conns.add("3,8");
    conns.add("6,5");
    conns.add("9,4");
    conns.add("2,1");
    conns.add("8,9");
    conns.add("5,0");
    conns.add("7,2");
    conns.add("6,1");
    conns.add("1,0");
    conns.add("6,7");

    ArrayList<String> got = new ArrayList<>();
    for (String c : conns) {
      String[] sites = c.split(",");
      int p = Integer.parseInt(sites[0]);
      int q = Integer.parseInt(sites[1]);
      if (!u.connected(p, q)) {
        u.union(p, q);
        got.add(c);
      }
    }

    assert 2 == u.count();

    ArrayList<String> exp = new ArrayList<>();
    exp.add("4,3");
    exp.add("3,8");
    exp.add("6,5");
    exp.add("9,4");
    exp.add("2,1");
    exp.add("5,0");
    exp.add("7,2");
    exp.add("6,1");
    assert exp.equals(got);
    System.out.println(u);
    System.out.printf("%s passed\n", u.getClass());
  }

  public static void main(String[] args) {
    UF.QuickFind qf = new UF.QuickFind(10);
    testUF(qf);
    UF.QuickUnion qu = new UF.QuickUnion(10);
    testUF(qu);
    UF.WeightQuickUnion wu = new UF.WeightQuickUnion(10);
    testUF(wu);
  }
}
