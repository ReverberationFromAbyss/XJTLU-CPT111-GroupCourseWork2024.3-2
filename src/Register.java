import UserManagement.Exceptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Register
    implements Initializable {

private Main alter;

@FXML
private Button cancelButton;

@FXML
private TextField idText;

@FXML
private TextField nameText;

@FXML
private PasswordField passwdCheckText;

@FXML
private PasswordField passwdText;

@FXML
private Button registerUserButton;

@FXML
void cancelHook(ActionEvent event) {
  alter.MainForm();
}

@FXML
void registerHook(ActionEvent event) {
  var id          = idText.getText();
  var name        = nameText.getText();
  var passwd      = passwdText.getText();
  var passwdCheck = passwdCheckText.getText();

  if (! passwdCheck.equals(passwd)) {
    passwdCheckText.setText("");
    passwdCheckText.setPromptText("Password not matching.");
    return;
  }

  try {
    Controller.getInstance()
              .RegisterUser(id, name, passwd);
    alter.MainForm();
  } catch (Exceptions.DuplicateUserException |
           Exceptions.UserInformationInvalidException e) {
    idText.setText("");
    idText.setPromptText(e.getMessage());
  }
}

public void setAlter(Main alter) {
  this.alter = alter;
}

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {

}

}
