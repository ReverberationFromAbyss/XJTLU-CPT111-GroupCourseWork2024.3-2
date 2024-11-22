import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Logger;

public class Main
    extends Application {

private Stage stage;

public static void main(String[] args) {
  Controller.getInstance()
            .RegisterStartupFunctionHook(Controller::LoadRequiredInfo)
            .RegisterAfterFunctionHook(Controller::SaveInfo)
            .StartFunction(() -> Application.launch(Main.class, args));
}

@Override
public void init() throws Exception {
}

@Override
public void start(Stage primaryStage) throws Exception {

  Parent root = FXMLLoader.load(
      Objects.requireNonNull(
          getClass().getClassLoader()
                    .getResource("MainForm.fxml")));
  primaryStage.setTitle(
      "XJTLU CPT111 Group Course Work 3 for year 2024/2025 Sem.1");
  stage = primaryStage;
  MainForm();
  stage.show();
}

@Override
public void stop() throws Exception {
}

public void MainForm() {
  try {
    MainForm mainForm = (MainForm)switchScene("MainForm.fxml");
    mainForm.setApp(this);
  } catch (Exception e) {
    Logger.getLogger("global")
          .info(e.getMessage());
  }
}

public void LoginForm() {
  try {
    LoginForm mainForm = (LoginForm)switchScene("LoginForm.fxml");
    mainForm.setAlter(this);
  } catch (Exception e) {
    Logger.getLogger("global")
          .info(e.getMessage());
  }
}

public void RankList() {
  try {
    RankList mainForm = (RankList)switchScene(
        "RankList.fxml");
    mainForm.setAlter(this);
  } catch (Exception e) {
    Logger.getLogger("global")
          .info(e.getMessage());
  }
}

public void Register() {
  try {
    Register mainForm = (Register)switchScene("Register.fxml");
    mainForm.setAlter(this);
  } catch (Exception e) {
    Logger.getLogger("global")
          .info(e.getMessage());
  }
}

public void UserDashboard() {
  try {
    UserDashboard mainForm = (UserDashboard)switchScene(
        "Userdashboard.fxml");
    mainForm.setAlter(this);
  } catch (Exception e) {
    Logger.getLogger("global")
          .info(e.getMessage());
  }
}

public void UserHistoryView() {
  try {
    UserHistoryView mainForm = (UserHistoryView)switchScene(
        "UserHistoryView.fxml");
    mainForm.setAlter(this);
  } catch (Exception e) {
    Logger.getLogger("global")
          .info(e.getMessage());
  }
}

public void QuizTaking() {
  try {
    QuizTaking mainForm = (QuizTaking)switchScene(
        "QuizTaking.fxml");
    mainForm.setAlter(this);
  } catch (Exception e) {
    Logger.getLogger("global")
          .info(e.getMessage());
  }
}

public Initializable switchScene(String fxmlp) throws Exception {
  FXMLLoader loader = new FXMLLoader();
  InputStream in = Objects.requireNonNull(
      Main.class.getResourceAsStream(fxmlp));
  loader.setBuilderFactory(new JavaFXBuilderFactory());
  loader.setLocation(Objects.requireNonNull(Main.class.getResource(fxmlp)));
  try {
    AnchorPane page  = loader.load(in);
    Scene      scene = new Scene(page);
    stage.setScene(scene);
    stage.sizeToScene();
  } catch (Exception e) {
    Logger.getLogger("global")
          .info(e.getMessage());
  }
  return loader.getController();
}

}
