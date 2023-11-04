public class StacksTest {
  static void testStack(Stacks.Stack<Integer> s) {
    s.push(1);
    assert 1 == s.size();
    s.push(2);
    assert 2 == s.size();
    // for (Integer i : s) System.out.println(i);
    assert 2 == s.pop();
    assert 1 == s.size();
    assert 1 == s.pop();
    assert 0 == s.size();
  }

  public static void main(String[] args) {
    testStack(new Stacks.ResizingArray<>());
    testStack(new Stacks.Linked<>());
  }
}