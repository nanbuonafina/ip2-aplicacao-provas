package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.session.Sessao;

public class TelaDadosAlunoController {

    @FXML
    private Label labelNome, labelEmail, labelMatricula;

    private final Sessao sessao = Sessao.getInstance();

    @FXML
    public void initialize() {
        Object usuario = sessao.getUsuarioLogado();

        if (usuario instanceof Aluno aluno) {
            labelNome.setText(aluno.getNomeCompleto());
            labelEmail.setText(aluno.getEmail());
            labelMatricula.setText(aluno.getMatricula());
        } else {
            labelNome.setText("Não encontrado");
            labelEmail.setText("Não encontrado");
            labelMatricula.setText("Não encontrado");
        }
    }

    @FXML
    private void voltarTelaPrincipal() {
        try {
            Stage stage = (Stage) labelNome.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aluno/TelaPrincipalAluno.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel do Aluno");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
