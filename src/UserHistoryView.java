import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserHistoryView
    implements Initializable {

private Launcher alter;

@FXML
private Button exitButton;

@FXML
private ListView<String> history;

@FXML
void exitHook(ActionEvent event) {
  alter.UserDashboard();
}

public void setAlter(Launcher alter) {
  this.alter = alter;
}

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {

  ObservableList<String> data = FXCollections.observableArrayList();

  var histories = Controller.getInstance()
                            .GetCurrentLoginUser()
                            .GetRecords();
  for (var topic : histories.GetRecordTopics()) {
    var scores = histories.GetRecord(topic);
    for (var rec : scores) {
      if (rec == null) {
        continue;
      }
      data.add(String.format("| %20s | %3d |", topic, rec));
    }
  }

  history.setItems(data);
}
}
