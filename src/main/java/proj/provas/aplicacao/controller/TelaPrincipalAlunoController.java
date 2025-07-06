package proj.provas.aplicacao.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TelaPrincipalAlunoController {

    @FXML
    public void abrirDadosPessoais(ActionEvent event) {
        abrirTela("TelaDadosAluno.fxml");
    }

    @FXML
    public void abrirProvas(ActionEvent event) {
        abrirTela("TelaProvasAluno.fxml");
    }

    @FXML
    public void abrirNotas(ActionEvent event) {
        abrirTela("TelaNotasAluno.fxml");
    }

    @FXML
    public void sair(ActionEvent event) {
        abrirTela("TelaInicial.fxml");
    }

    private void abrirTela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources.fxml/" + fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema de Provas");
            stage.show();
            // TODO: fechar janela atual, se quiser
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
