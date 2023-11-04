import java.util.Stack;

public class ExprEval {
  Stack<Integer> operands = new Stack<>();
  Stack<Integer> operators = new Stack<>();

  public int eval(String s) {
    for (int i = 0; i < s.length(); i++) {
      process(s.charAt(i));
    }
    binaryEval();
    return operands.pop();
  }

  // c is number, operator or parenthesis
  private void process(int c) {
    if (Character.isDigit(c)) {
      int op = c - '0';
      operands.add(op);
    } else if (c == '+') {
      operators.add(c);
    } else if (c == '(') {

    } else if (c == ')') {
      binaryEval();
    } else throw new IllegalArgumentException();
  }

  private void binaryEval() {
    int op = operators.pop();
    if (op == '+') operands.push(operands.pop() + operands.pop());
  }

  public static void main(String[] args) {
    ExprEval ee = new ExprEval();
    int r = ee.eval("2+2");
    System.out.println(r);
    assert 4 == r;
  }
}
