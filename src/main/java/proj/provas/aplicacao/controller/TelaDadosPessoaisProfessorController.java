package proj.provas.aplicacao.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TelaDadosPessoaisProfessorController {

    @FXML private Label labelID;
    @FXML private Label labelNome;
    @FXML private Label labelEmail;
    @FXML private Label labelDisciplinas;
    @FXML private Button btnVoltarProf;

    public void carregarDadosProf(String ID, String nome, String email, String disciplinas) {
        labelID.setText(ID != null ? ID : "Não informado");
        labelNome.setText(nome != null ? nome : "Não informado");
        labelEmail.setText(email != null ? email : "Não informado");
        labelDisciplinas.setText(disciplinas != null ? disciplinas : "Não informado");
    }
}
