import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class UserDashboard
    implements Initializable {

private Main alter;

@FXML
private Button historyButton;

@FXML
private Button logoutButton;

@FXML
private Button quizButton;

@FXML
void HistoryHook(ActionEvent event) {
  alter.UserHistoryView();
}

@FXML
void logoutHook(ActionEvent event) {
  Controller.getInstance()
            .LogoutUser();
  alter.MainForm();
}

@FXML
void quizHook(ActionEvent event) {
  alter.QuizTaking();
}

public void setAlter(Main alter) {
  this.alter = alter;
}


@Override
public void initialize(URL url, ResourceBundle resourceBundle) {

}
}
