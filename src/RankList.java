import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class RankList
    implements Initializable {

@FXML
public  ListView<String> randList;
private Launcher         alter;

@FXML
private Button backButton;

@FXML
void BackHook(ActionEvent event) {
  alter.MainForm();
}

public void setAlter(Launcher alter) {
  this.alter = alter;
}

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
  ObservableList<String> data = FXCollections.observableArrayList();

  var history = Controller.getInstance()
                          .GetScores();
  for (var topic : history.keySet()) {
    var scores = new java.util.ArrayList<>(history.get(topic)
                                                  .stream()
                                                  .filter(x -> x.score != null)
                                                  .toList());
    scores.sort((x, y) -> y.score - x.score);
    if (! scores.isEmpty()) {
      if (scores.get(0).score != null) {
        data.add(
            String.format("| %20s | %3d | %10s |", topic, scores.get(0).score,
                          scores.get(0).id));
      }
    }
  }

  randList.setItems(data);
}
}
