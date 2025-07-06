package proj.provas.aplicacao.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TelaDadosAlunoController {

    @FXML private Label labelNomeCompleto;
    @FXML private Label labelEmail;
    @FXML private Label labelMatricula;
    @FXML private Label labelTurma;

    public void carregarDados(String nomeCompleto, String email, String matricula, String turma) {
        labelNomeCompleto.setText(nomeCompleto != null ? nomeCompleto : "Não informado");
        labelEmail.setText(email != null ? email : "Não informado");
        labelMatricula.setText(matricula != null ? matricula : "Não informado");
        labelTurma.setText(turma != null ? turma : "Não informado");
    }
}
