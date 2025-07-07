package proj.provas.aplicacao.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TelaPrincipalAlunoController {

    @FXML
    private Button btnDadosPessoais;

    @FXML
    private Button btnAbrirProvas;

    @FXML
    private Button btnAbrirNotas;

    @FXML
    private Button btnSair;

    // Metodo para abrir a tela de dados pessoais
    @FXML
    private void abrirDadosPessoais() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaDadosAluno.fxml"));
            Parent root = loader.load();

            // Obter o controller e carregar os dados do aluno (exemplo)
            TelaDadosAlunoController controller = loader.getController();
            controller.carregarDados("João Silva", "joao@escola.com", "20230001", "3º Ano A");

            Stage stage = (Stage) btnDadosPessoais.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Meus Dados Pessoais");

        } catch (Exception e) {
            mostrarErro("Erro ao abrir dados pessoais", e.getMessage());
        }
    }

    // Metodo para abrir as provas
    @FXML
    private void abrirProvas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaProvasAluno.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnAbrirProvas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Minhas Provas");

        } catch (Exception e) {
            mostrarErro("Erro ao abrir provas", e.getMessage());
        }
    }

    // Metodo para abrir as notas
    @FXML
    private void abrirNotas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaNotasAluno.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnAbrirNotas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Minhas Notas");

        } catch (Exception e) {
            mostrarErro("Erro ao abrir notas", e.getMessage());
        }
    }

    // Metodo para sair do sistema -- ta funcionando
    @FXML
    private void sair() {
        try {
            // Confirmação antes de sair
            Alert confirmacao = new Alert(AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação");
            confirmacao.setHeaderText("Deseja realmente sair do sistema?");
            confirmacao.setContentText("Clique em OK para sair ou Cancelar para continuar.");

            if (confirmacao.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
                Stage stage = (Stage) btnSair.getScene().getWindow();
                stage.close();
            }

        } catch (Exception e) {
            mostrarErro("Erro ao sair", e.getMessage());
        }
    }

    // Metodo auxiliar para mostrar erros
    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}