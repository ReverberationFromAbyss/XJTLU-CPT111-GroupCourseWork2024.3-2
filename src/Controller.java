import QuestionManagement.QuestionManager;
import UserManagement.Exceptions;
import UserManagement.UserManager;
import UserManagement.Users;
import xjtlu.cpt111.assignment.quiz.model.Difficulty;
import xjtlu.cpt111.assignment.quiz.model.Question;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;


public class Controller {

private static final Controller s_instance_ = new Controller();
// singleton pattern

private final UserManager     m_users_        = new UserManager();
private final QuestionManager m_questions_    = new QuestionManager();
private final List<Hook>      m_prepareHooks_ = new ArrayList<>();
private final List<Hook>      m_quitHooks_    = new ArrayList<>();
private       Users           m_currentLoginUser_;

public static Controller getInstance() {
  return s_instance_;
}

public Controller RegisterStartupFunctionHook(Hook hook) {
  m_prepareHooks_.add(hook);
  return this;
}

public Controller RegisterAfterFunctionHook(Hook hook) {
  m_quitHooks_.add(hook);
  return this;
}

public boolean LoadRequiredInfo() {
  boolean success = true;
  try {
    m_users_.LoadUserInfo(Misc.UserInfomationsDatabasePath,
                          Misc.UserScoreDatabasePathh);
    m_questions_.LoadQuestions(Misc.QuestionsDatabasePath);
  } catch (IOException e) {
    Logger.getLogger("global")
          .info("Cannot fetch required sources");
    Logger.getLogger("global")
          .info(e.getMessage());
    success = false;
  }
  return success;
}

public boolean SaveInfo() {
  boolean success = true;
  try {
    m_users_.SaveUserInfo(Misc.UserInfomationsDatabasePath,
                          Misc.UserScoreDatabasePathh);
  } catch (IOException e) {
    Logger.getLogger("global")
          .info(e.getMessage());
    success = false;
  }
  return success;
}

public Controller StartupFunctionCall() {
  for (var fun : m_prepareHooks_) {
    if (! fun.Apply(this)) {
      Logger.getLogger("global")
            .info("1 Prepare task failed.");
    }
  }
  return this;
}

public Controller AfterFunctionCall() {
  for (var fun : m_quitHooks_) {
    if (! fun.Apply(this)) {
      Logger.getLogger("global")
            .info("1 Quit task failed.");
    }
  }
  return this;
}

public Controller StartFunction(CallBackHook.FunctionHook hook) {
  StartupFunctionCall();
  try {
    hook.Apply();
  } catch (RuntimeException e) {
    Logger.getLogger("global")
          .info(e.getMessage());
  }
  AfterFunctionCall();
  return this;
}

public Map<String, List<UserManager.ScoreEntry>> GetScores() {
  return m_users_.GetRecords();
}

/**
 * Try to log in user
 *
 * @param id     id of user to be logged in
 * @param passwd input password
 * @return self, for chain-call
 */
public Controller LoginUser(String id, String passwd) {
  m_currentLoginUser_ = m_users_.CheckLogin(id, passwd);
  return this;
}

/**
 * Register new user
 *
 * @param id     new user id
 * @param name   new user name
 * @param passwd password
 * @return self, for chain-call
 * @throws Exceptions.UserInformationInvalidException if the user exists
 */
public Controller RegisterUser(String id, String name, String passwd) throws Exceptions.UserInformationInvalidException {
  m_users_.RegisterUser(new Users(id, name, passwd));
  return this;
}

/**
 * Try to log out the user
 *
 * @return self, for chain-call
 */
public Controller LogoutUser() {
  m_currentLoginUser_ = null;
  return this;
}

/**
 * Detect weather the user whose id is `id' exists
 *
 * @param id id of user who is needed to be checked
 * @return weather has the user
 */
public boolean HasUser(String id) {
  return m_users_.HasUser(id);
}

/**
 * get current login user
 *
 * @return current login user
 */
public Users GetCurrentLoginUser() {
  return m_currentLoginUser_;
}

/**
 * Get topics
 *
 * @return topics
 */
public String[] GetQuizTopics() {
  return m_questions_.GetTopics();
}

/**
 * Get questions of specific topic
 *
 * @param topic specific topic
 * @return questions
 */
public Question[] GetQuestions(String topic) {
  List<Question> questions = new ArrayList<>();
  var unsorted = new ArrayList<Question>(
      Arrays.stream(m_questions_.GetQuestions(topic))
            .toList());
  int[] questionNumbOfEachDifficult = new int[Difficulty.values().length];
  Collections.shuffle(unsorted);
  for (var q : unsorted) {
    if (questionNumbOfEachDifficult[q.getDifficulty()
                                     .ordinal()] == 0) {
      questions.add(q);
      questionNumbOfEachDifficult[q.getDifficulty()
                                   .ordinal()]++;
    }
  }
  Collections.shuffle(questions);

  return questions.toArray(new Question[0]);
}

/**
 * Add record to current managed user
 *
 * @param topic what topic
 * @param score how many the user got
 * @return self, for chain-call
 */
public Controller RecoredScore(String topic, int score) {
  m_users_.AddRecord(m_currentLoginUser_.GetId(), topic, score);
  return this;
}

public interface Hook {
  boolean Apply(Controller o);
}

}
