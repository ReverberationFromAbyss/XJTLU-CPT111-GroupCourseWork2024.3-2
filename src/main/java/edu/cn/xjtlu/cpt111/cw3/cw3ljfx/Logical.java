package edu.cn.xjtlu.cpt111.cw3.cw3ljfx;

import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CallBackHook.Hook;
import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.QuestionManagement.QuestionManager;
import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.UserManagement.UserManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class Logical {

private static final Logical s_instance_ = new Logical();

private final UserManager     m_users_        = new UserManager();
private final QuestionManager m_questions_    = new QuestionManager();
private final List<Hook>      m_prepareHooks_ = new ArrayList<>();
private final List<Hook>      m_quitHooks_    = new ArrayList<>();

public static Logical getInstance() {
  return s_instance_;
}

public Logical RegisterStartupFunctionHook(Hook hook) {
  m_prepareHooks_.add(hook);
  return this;
}

public Logical RegisterAfterFunctionHook(Hook hook) {
  m_quitHooks_.add(hook);
  return this;
}

public boolean LoadRequiredInfo() {
  boolean success = true;
  try {
    m_users_.LoadUserInfo("resources/u.csv", "resources/s.csv");
    m_questions_.LoadQuestions("resources/questionsBank");
  } catch (IOException e) {
    Logger.getLogger("global")
          .info(e.getMessage());
    success = false;
  }
  return success;
}

public boolean SaveInfo() {
  boolean success = true;
  try {
    m_users_.SaveUserInfo("resources/u.csv", "resources/s.csv");
  } catch (IOException e) {
    Logger.getLogger("global")
          .info(e.getMessage());
    success = false;
  }
  return success;
}

public Logical StartupFunctionCall() {
  for (var fun : m_prepareHooks_) {
    if (! fun.Apply(this)) {
      Logger.getLogger("global")
            .info("1 Prepare task failed.");
    }
  }
  return this;
}

public Logical AfterFunctionCall() {
  for (var fun : m_quitHooks_) {
    if (! fun.Apply(this)) {
      Logger.getLogger("global")
            .info("1 Quit task failed.");
    }
  }
  return this;
}


}
