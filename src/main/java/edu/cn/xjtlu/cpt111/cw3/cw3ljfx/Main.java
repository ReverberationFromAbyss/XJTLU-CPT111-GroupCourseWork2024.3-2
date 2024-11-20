package edu.cn.xjtlu.cpt111.cw3.cw3ljfx;

import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CsvUtils.CsvReader;
import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CsvUtils.CsvWriter;
import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CsvUtils.Table;
import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.UserManagement.UserManager;

import java.io.IOException;

public class Main {
public static void main(String[] args) {

  Table table = CsvReader.ConstructTableFromCSV("""
                                                    Name,rbq,12345
                                                    "St
                                                    upid",s"s,"5,4321"
                                                    ""\"",,
                                                    """);
  try {
    UserManager userManager = new UserManager().LoadUserInfo("resources/u.csv", "resources/s.csv")
                                               .SaveUserInfo("resources/u.csv", "resources/s.csv");

    var t = userManager.ExportAccountInfoToTable();
    System.out.println(CsvWriter.GenerateContent(t));
    t = userManager.ExportScoreInfoToTable();
    System.out.println(CsvWriter.GenerateContent(t));
  } catch (IOException e) {
    System.out.println(e.getMessage());
  }
}
}
