public class STsTest {
  static void testST(String name, STs.ST<Integer, Integer> st) {
    st.put(5, 50);
    st.put(2, 20);
    assert 2 == st.size();
    assert 20 == st.get(2);
    assert 50 == st.get(5);

    st.delete(5);
    assert 1 == st.size();
    assert null == st.get(5);
    assert 20 == st.get(2);

    st.put(1, 10);
    assert 2 == st.size();
    assert 10 == st.get(1);
    assert 20 == st.get(2);

    System.out.printf("%s pass\n", name);
  }

  static void testOrderedST(String name, STs.OrderedST<Integer, Integer> st) {
    testST(name, st);
    for (int i = 1; i <= 10; i++) {
      st.put(i, 10 * i);
    }
    assert 1 == st.min();
    assert 10 == st.max();
    assert 5 == st.floor(5);
    assert 10 == st.floor(11);
    assert 2 == st.ceiling(2);
    assert 1 == st.ceiling(0);
    assert 6 == st.select(5);
    assert 0 == st.rank(0);
    assert 0 == st.rank(1);
    assert 4 == st.rank(5);

    System.out.printf("%s pass\n", name);
  }

  public static void main(String[] args) {
    testST("LinkST", new STs.LinkST<Integer, Integer>());
    testST("ArrayOrderedST", new STs.ArrayOrderedST<Integer, Integer>());
    testOrderedST("BSTree", new STs.BSTree<Integer, Integer>());
    testST("SepChainHashST", new STs.SepChainHashST<Integer, Integer>(10));
    testST("LinProbeHashST", new STs.LinProbeHashST<Integer, Integer>(10));
  }
}
