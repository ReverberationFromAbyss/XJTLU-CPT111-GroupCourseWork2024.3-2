package edu.cn.xjtlu.cpt111.cw3.cw3ljfx;

import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CsvUtils.CsvReader;
import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CsvUtils.CsvWriter;
import edu.cn.xjtlu.cpt111.cw3.cw3ljfx.CsvUtils.Table;

public class Main {
public static void main(String[] args) {

  Table table = CsvReader.ConstructTableFromCSV("""
                                                    Name,rbq,12345
                                                    "St
                                                    upid",s"s,"5,4321"
                                                    ""\"",,
                                                    """);
  System.out.print(CsvWriter.GenerateContent(table));
}
}
