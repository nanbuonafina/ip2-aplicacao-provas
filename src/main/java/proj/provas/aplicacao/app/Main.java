package proj.provas.aplicacao.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL; // Importe esta classe

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaDeLogon.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        URL cssResource = getClass().getResource("/css/style.css");
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
            System.out.println("CSS carregado com sucesso: " + cssResource.toExternalForm()); // Opcional: para debug
        } else {
            System.err.println("Erro: Arquivo CSS não encontrado em /css/style.css");
        }

        stage.setTitle("Sistema de Provas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}