import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginForm
    implements Initializable {

private Main alter;

@FXML
private Button cancelButton;

@FXML
private Button loginButton;

@FXML
private PasswordField passwdInputField;

@FXML
private TextField userIdField;

@FXML
void CancelHook(ActionEvent event) {
  alter.MainForm();
}

@FXML
void LoginHook(ActionEvent event) {
  var id     = userIdField.getText();
  var passwd = passwdInputField.getText();

  if (Controller.getInstance()
                .HasUser(id)) {
    userIdField.setText("");
    userIdField.setPromptText("No such user");
  }
  if (Controller.getInstance()
                .LoginUser(id, passwd)
                .GetCurrentLoginUser() != null) {
    alter.UserDashboard();
  } else {
    passwdInputField.setText("");
    passwdInputField.setPromptText(
        "Either user id is not correct or passwd is not matching");
  }
}

public void setAlter(Main alter) {
  this.alter = alter;
}

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {

}
}
