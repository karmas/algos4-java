
public class PQsTest {
  static void testMaxPQ(String name, PQs.PQ<Integer> pq) {
    for (int i = 0; i < 2; i++) {
      pq.insert(i);
      assert i == pq.getHigh();
      assert i + 1 == pq.size();
    }

    for (int i = 3; i >= 2; i--) {
      pq.insert(i);
      assert 3 == pq.getHigh();
    }
    assert 4 == pq.size();

    for (int i = 3; i >= 0; i--) {
      assert i == pq.delHigh();
    }
    assert 0 == pq.size();
    System.out.printf("%s pass\n", name);
  }

  static void testIndexMinPQ() {
    PQs.IndexMinPQ<Integer> pq = new PQs.IndexMinPQ<>(5);
    pq.insert(4, 50);
    pq.insert(2, 100);
    pq.insert(3, 30);
    assert 3 == pq.size();
    assert 3 == pq.minIndex();
    assert 30 == pq.min();

    assert 3 == pq.delMin();
    assert 2 == pq.size();
    assert 4 == pq.minIndex();
    assert 50 == pq.min();
    System.out.println("IndexMinPQ pass");
  }

  public static void main(String[] args) {
    testMaxPQ("MaxPQArray", new PQs.MaxPQArray<Integer>());
    testMaxPQ("MaxPQLink", new PQs.MaxPQLink<Integer>());
    testMaxPQ("MaxPQ", new PQs.MaxPQ<Integer>());
    testIndexMinPQ();
  }
}
