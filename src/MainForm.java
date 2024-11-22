import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainForm
    implements Initializable {

private Main   alter;
@FXML
private Button historyButton;

@FXML
private Button loginButton;

@FXML
private Button registerButton;


@FXML
void historyhook(ActionEvent event) {
  alter.RankList();
}

@FXML
void loginhook(ActionEvent event) {
  alter.LoginForm();
}

@FXML
void registerhook(ActionEvent event) {
  alter.Register();
}

public void setApp(Main alter) {
  this.alter = alter;
}

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
}
}