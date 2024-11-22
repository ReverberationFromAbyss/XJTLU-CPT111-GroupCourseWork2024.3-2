import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import xjtlu.cpt111.assignment.quiz.model.Option;
import xjtlu.cpt111.assignment.quiz.model.Question;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

public class QuizTaking
    implements Initializable {

private Main alter;

@FXML
private ListView<String> topicList;
@FXML
private Button           chooseButton;
@FXML
private Button           backButton;
@FXML
private Label            label;
@FXML
private Label            questionStatement;


private Question[] questions       = null;
private int        currentQuestion = 0;
private int        totallyCorrect  = 0;
private String     topic;

@FXML
void chooseHook(ActionEvent event) {
  var choices = topicList.getSelectionModel()
                         .getSelectedItems();
  String                 choose = null;
  ObservableList<String> data   = FXCollections.observableArrayList();
  if (! choices.isEmpty()) {
    choose = choices.get(0); // for single selection
  }

  if (questions == null && choose != null) {
    questions = Controller.getInstance()
                          .GetQuestions(choose);
    topic     = choose;

    questionStatement.setText(
        questions[currentQuestion].getQuestionStatement());
    var options = new java.util.ArrayList<>(
        Arrays.stream(questions[currentQuestion].getOptions())
              .map(Option::getAnswer)
              .toList());
    Collections.shuffle(options);
    data.addAll(options);
    topicList.setItems(data);
    currentQuestion++;
    return;
  } else if (choose == null) {
    label.setText("!" + label.getText());
    return;
  }
  if (currentQuestion > questions.length) {
    return;
  }
  for (var o : questions[currentQuestion - 1].getOptions()) {
    if (o.getAnswer()
         .equals(choose) && o.isCorrectAnswer()) {
      totallyCorrect++;
    }
  }
  if (currentQuestion == questions.length) {
    var score = 100 * totallyCorrect / questions.length;

    data.addAll("Congratulate", "You got:", Integer.toString(score),
                "scores!");
    Controller.getInstance()
              .RecoredScore(topic, score);
    topicList.setItems(data);
    currentQuestion++;
    return;
  }
  questionStatement.setText(questions[currentQuestion].getQuestionStatement());
  var options = new java.util.ArrayList<>(
      Arrays.stream(questions[currentQuestion].getOptions())
            .map(Option::getAnswer)
            .toList());
  Collections.shuffle(options);
  data.addAll(options);
  topicList.setItems(data);
  currentQuestion++;
}

@FXML
void backHook(ActionEvent event) {
  alter.UserDashboard();
}

public void setAlter(Main alter) {
  this.alter = alter;
}

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {

  ObservableList<String> data = FXCollections.observableArrayList();
  data.addAll(Controller.getInstance()
                        .GetQuizTopics());
  topicList.getSelectionModel()
           .setSelectionMode(SelectionMode.SINGLE);
  topicList.setItems(data);
}

}