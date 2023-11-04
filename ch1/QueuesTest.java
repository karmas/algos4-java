public class QueuesTest {
  static void testQueue(Queues.Queue<Integer> q) {
    q.enqueue(1);
    assert 1 == q.size();
    q.enqueue(2);
    assert 2 == q.size();
    q.enqueue(3);
    assert 3 == q.size();
    assert 1 == q.dequeue();
    assert 2 == q.size();
    assert 2 == q.dequeue();
    assert 1 == q.size();
    q.enqueue(4);
    q.enqueue(5);
    q.enqueue(6);
    assert 4 == q.size();
    assert 3 == q.dequeue();
  }

  public static void main(String[] args) {
    testQueue(new Queues.ResizingArray<>());
    testQueue(new Queues.Linked<>());
  }
}