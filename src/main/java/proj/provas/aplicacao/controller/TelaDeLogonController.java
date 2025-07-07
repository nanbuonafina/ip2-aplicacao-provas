package proj.provas.aplicacao.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaDeLogonController {


    @FXML
    void abrirTelaDeCadastro(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("TelaDeAutoCadastro.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("Cadastro de Usuário"); // Opcional: mudar o título da janela

            stage.show();

        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de cadastro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}