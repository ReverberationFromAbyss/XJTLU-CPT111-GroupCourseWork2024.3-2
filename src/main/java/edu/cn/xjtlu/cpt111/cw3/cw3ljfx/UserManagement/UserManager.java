package edu.cn.xjtlu.cpt111.cw3.cw3ljfx.UserManagement;

import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CsvUtils.CsvReader;
import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CsvUtils.CsvWriter;
import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CsvUtils.Exceptions;
import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CsvUtils.Table;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import static edu.cn.xjtlu.cpt111.cw3.cw3ljfx.UserManagement.Exceptions.DuplicateUserException;

public class UserManager {

private final Set<Users> m_users_ = new HashSet<>();

public UserManager LoadUserInfo(String infofp, String scorefp) throws IOException {
  // Account info
  var file = new File(infofp);
  if (! file.exists() || file.isDirectory()) {
    throw new FileNotFoundException("No such file to be read");
  }
  var content = new byte[((int)file.length())];
  try (var inputStream = new FileInputStream(file)) {
    var v   = inputStream.read(content);
    var csv = CsvReader.ConstructTableFromCSV(new String(content));
    LoadAccountInfoFromTable(csv);
  } catch (DuplicateUserException e) {
    System.err.println(e.getMessage());
  }

  // Score
  file = new File(scorefp);
  if (! file.exists() || file.isDirectory()) {
    throw new FileNotFoundException("No such file to be read");
  }
  content = new byte[((int)file.length())];
  try (var inputStream = new FileInputStream(file)) {
    var v   = inputStream.read(content);
    var csv = CsvReader.ConstructTableFromCSV(new String(content));
    LoadScoreInfoFromTable(csv);
  } catch (DuplicateUserException | NumberFormatException e) {
    System.err.println(e.getMessage());
  }

  return this;
}

public UserManager SaveUserInfo(String infofp, String scorefp) throws IOException {
  var file = new File(infofp);
  if (! file.exists() || file.isDirectory()) {
    throw new FileNotFoundException("No such file to be read");
  }
  try (var printer = new PrintWriter(file)) {
    printer.print(CsvWriter.GenerateContent(ExportAccountInfoToTable()));
  }
  file = new File(scorefp);
  if (! file.exists() || file.isDirectory()) {
    throw new FileNotFoundException("No such file to be read");
  }
  try (var printer = new PrintWriter(file)) {
    printer.print(CsvWriter.GenerateContent(ExportScoreInfoToTable()));
  }
  return this;
}

public UserManager RegisterUser(Users user) {
  m_users_.forEach((x) -> {
    if (user.GetId()
            .equals(x.GetId())) {
      throw new DuplicateUserException("There exists a user whose id is " + user.GetId());
    }
  });
  m_users_.add(user);
  return this;
}

public Users CheckLogin(String id, String passwd) {
  AtomicReference<Users> ret = new AtomicReference<>();
  m_users_.forEach(u -> {
    if (u.GetId()
         .equals(id) && u.CheckPasswd(passwd)) {
      ret.set(u);
    }
  });
  return ret.get();
}

public UserManager AddRecord(String id, String topic, Integer score) {
  m_users_.forEach(u -> {
    if (u.GetId()
         .equals(id)) {
      u.NewRecord(topic, score);
    }
  });
  return this;
}

public Table ExportAccountInfoToTable() {
  var table = new Table();
  for (var u : m_users_) {
    table.InsertLine()
         .InsertElement(table.GetRows() - 1, u.GetId(), true)
         .InsertElement(table.GetRows() - 1, u.GetName(), true)
         .InsertElement(table.GetRows() - 1, u.GetPasswd(), true);
    Logger.getLogger("global")
          .info("Write one user's score record: id:" + u.GetId());
  }
  return table;
}

public UserManager LoadAccountInfoFromTable(Table table) {
  for (var l : table.GetTable()) {
    if (l.length <= 0) {
      continue;
    } else if (l.length < 3) {
      throw new Exceptions.IllegalSyntaxException("The Format of CSV file is not matching.");
    }
    RegisterUser(new Users(l[0], l[1], l[2]));
    Logger.getLogger("global")
          .info("Load one user: id:" + l[0]);
  }
  return this;
}

public UserManager LoadScoreInfoFromTable(Table table) {
  for (var l : table.GetTable()) {
    if (l.length <= 0) {
      continue;
    } else if (l.length < 5) {
      throw new Exceptions.IllegalSyntaxException("The Format of CSV file is not matching.");
    }
    m_users_.stream()
            .filter(x -> x.GetId()
                          .equals(l[0]))
            .toList()
            .get(0)
            .NewRecord(l[1], l[2].isEmpty() ? null : Integer.parseInt(l[2]))
            .NewRecord(l[1], l[3].isEmpty() ? null : Integer.parseInt(l[3]))
            .NewRecord(l[1], l[4].isEmpty() ? null : Integer.parseInt(l[4]));
    Logger.getLogger("global")
          .info("Load one user's score record: id:" + l[0]);
  }

  return this;
}

public Table ExportScoreInfoToTable() {
  var table = new Table();
  for (var u : m_users_) {
    for (var t : u.GetRecords()
                  .GetRecordTopics()) {
      var scores = u.GetRecords()
                    .GetRecord(t);
      table.InsertLine()
           .InsertElement(table.GetRows() - 1, u.GetId(), true)
           .InsertElement(table.GetRows() - 1, t, true)
           .InsertElement(table.GetRows() - 1, scores[0] == null ? "" : scores[0].toString(), true)
           .InsertElement(table.GetRows() - 1, scores[1] == null ? "" : scores[1].toString(), true)
           .InsertElement(table.GetRows() - 1, scores[2] == null ? "" : scores[2].toString(), true);
      Logger.getLogger("global")
            .info("Write one user's score record: id:" + u.GetId());
    }
  }
  return table;
}
}
