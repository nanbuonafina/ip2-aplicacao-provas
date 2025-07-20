package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import proj.provas.aplicacao.session.Sessao;

public class TelaDadosPessoaisProfessorController {

    @FXML private Label labelID;
    @FXML private Label labelNome;
    @FXML private Label labelEmail;
    @FXML private Label labelDisciplinas;
    @FXML private Button btnVoltarProf;

    private final Sessao sessao = Sessao.getInstance();

    @FXML
    public void initialize() {
        Object usuario = sessao.getUsuarioLogado();

        if (usuario instanceof proj.provas.aplicacao.model.Professor professor) {
            labelID.setText(professor.getId());
            labelNome.setText(professor.getNomeCompleto());
            labelEmail.setText(professor.getEmail());
            labelDisciplinas.setText(professor.getDisciplinasMinistradas() != null ?
                String.join(", ", professor.getDisciplinasMinistradas().stream().map(d -> d.getNome()).toList()) : "Não informado");
        } else {
            labelID.setText("Não encontrado");
            labelNome.setText("Não encontrado");
            labelEmail.setText("Não encontrado");
            labelDisciplinas.setText("Não encontrado");
        }
    }

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