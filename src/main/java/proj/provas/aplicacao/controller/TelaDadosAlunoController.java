package proj.provas.aplicacao.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class TelaDadosAlunoController {

    @FXML private Label labelNomeCompleto;
    @FXML private Label labelEmail;
    @FXML private Label labelMatricula;
    @FXML private Label labelTurma;
    @FXML private Button btnVoltar;


    public void carregarDados(String nomeCompleto, String email, String matricula, String turma) {
        labelNomeCompleto.setText(nomeCompleto != null ? nomeCompleto : "Não informado");
        labelEmail.setText(email != null ? email : "Não informado");
        labelMatricula.setText(matricula != null ? matricula : "Não informado");
        labelTurma.setText(turma != null ? turma : "Não informado");
    }

    // voltar tela inicial do aluno
    @FXML
    private void voltarParaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/aluno/TelaPrincipalAluno.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnVoltar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Página Principal do Aluno");

        } catch (Exception e) {
            System.err.println("Erro ao voltar: " + e.getMessage());
            // Aqui você pode adicionar um Alert para mostrar o erro ao usuário
        }

    }
}