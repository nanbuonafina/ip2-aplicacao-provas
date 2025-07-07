package proj.provas.aplicacao.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TelaGerDisciplinasController {

    @FXML private Button btnVoltarProf;

    @FXML
    public void voltarProf(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaPaginaProfessor.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnVoltarProf.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Página do professor");

        }catch (Exception erro) {
            mostrarErro("Erro ao voltar:", erro.getMessage());
        }
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
