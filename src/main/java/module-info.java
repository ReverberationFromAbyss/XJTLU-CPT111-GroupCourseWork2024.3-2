module edu.cn.xjtlu.cpt111.cw3.cw3ljfx {
  requires javafx.controls;
  requires javafx.fxml;


  opens edu.cn.xjtlu.cpt111.cw3.cw3ljfx to javafx.fxml;
  exports edu.cn.xjtlu.cpt111.cw3.cw3ljfx;
}