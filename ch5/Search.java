public class Search {
  public static int brute(String txt, String pat) {
    for (int i = 0; i <= txt.length() - pat.length(); ++i) {
      int j = 0;
      for (; j < pat.length() && txt.charAt(i + j) == pat.charAt(j); ++j);
      if (j == pat.length()) return i;
    }
    return txt.length();
  }

  public static int bruteBackup(String txt, String pat) {
    int i = 0;
    int j = 0;
    for (i = 0; i < txt.length() && j < pat.length(); ++i) {
      if (txt.charAt(i) != pat.charAt(j)) {
        i -= j;
        j = 0;
      } else {
        ++j;
      }
    }
    return j == pat.length() ? i - j : txt.length();
  }

  public static int kmp(String txt, String pat) {
    int R = 256;
    int dfa[][] = new int[R][pat.length()];
    dfa[pat.charAt(0)][0] = 1;
    for (int X = 0, j = 1; j < pat.length(); ++j) {
      for (int c = 0; c < R; ++c)
        dfa[c][j] = X;
      dfa[pat.charAt(j)][j] = j + 1;
      X = dfa[pat.charAt(j)][X];
    }

    int i = 0;
    int j = 0;
    for (; i < txt.length() && j < pat.length(); ++i) {
      j = dfa[txt.charAt(i)][j];
    }
    return j == pat.length() ? i - j : txt.length();
  }

  public static int bm(String txt, String pat) {
    int R = 256;
    int[] right = new int[R];
    for (int i = 0; i < right.length; ++i)
      right[i] = -1;
    for (int i = 0; i < pat.length(); ++i) {
      right[pat.charAt(i)] = i;
    }

    int i = 0;
    int skip = 0;
    for (; i <= txt.length() - pat.length(); i += skip) {
      int j;
      for (j = pat.length() - 1; j >= 0; --j) {
        if (txt.charAt(i + j) != pat.charAt(j)) {
          skip = j - right[txt.charAt(i + j)];
          if (skip < 1) skip = 1;
          break;
        }
      }
      if (-1 == j) return i;
    }

    return txt.length();
  }
}