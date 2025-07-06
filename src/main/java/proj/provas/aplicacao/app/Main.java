package proj.provas.aplicacao.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Carrega a tela inicial
        Parent root = FXMLLoader.load(getClass().getResource("/TelaInicial.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Sistema de Provas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
