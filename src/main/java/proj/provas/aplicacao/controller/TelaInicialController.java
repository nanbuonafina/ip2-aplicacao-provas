package proj.provas.aplicacao.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TelaInicialController {

    @FXML
    private void entrarComoAluno() {
        mostrarMensagem("Você escolheu entrar como Aluno.");
        // aq a gente carrega a tela do aluno
    }

    @FXML
    private void entrarComoProfessor() {
        mostrarMensagem("Você escolheu entrar como Professor.");
        // aq a gente carrega a tela do professor
    }
    @FXML
    private void mostrarMensagem(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    protected void abrirTelaCadastro(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/resources.fxml/Cadastro.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Cadastro");
        stage.show();
    }

}

