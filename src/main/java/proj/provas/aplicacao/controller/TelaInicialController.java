package proj.provas.aplicacao.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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

    private void mostrarMensagem(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
