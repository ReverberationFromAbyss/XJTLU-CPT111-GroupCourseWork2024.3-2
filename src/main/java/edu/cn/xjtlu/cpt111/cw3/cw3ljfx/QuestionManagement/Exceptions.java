package edu.cn.xjtlu.cpt111.cw3.cw3ljfx.QuestionManagement;

public class Exceptions {
public static class NoTopicFoundException
    extends RuntimeException {
  public NoTopicFoundException() {
    super();
  }

  public NoTopicFoundException(String e) {
    super(e);
  }
}
}
