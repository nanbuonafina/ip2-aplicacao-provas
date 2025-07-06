package proj.provas.aplicacao.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;

public class CadastroController {

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoMatricula;
    @FXML
    private ChoiceBox<String> tipoUsuario;
    @FXML
    private TextField campodisciplinasMinistradas;
    @FXML
    private TextField campoid;


    @FXML
    public void cadastrarAluno() {
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String matricula = campoMatricula.getText();
        String tipo = tipoUsuario.getValue();

        System.out.println("Cadastrado: " + nome + " - " + tipo);
    }

    @FXML
    public void cadastrarProfessor() {
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String id = campoid.getText();
        String tipo = tipoUsuario.getValue();

    }
}
