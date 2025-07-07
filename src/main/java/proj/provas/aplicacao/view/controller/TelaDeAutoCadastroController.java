package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import proj.provas.aplicacao.controller.AlunoController;
import proj.provas.aplicacao.controller.ProfessorController;
import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.model.Professor;
import proj.provas.aplicacao.repository.impl.AlunoRepositoryImpl;
import proj.provas.aplicacao.repository.impl.ProfessorRepositoryImpl;

public class TelaDeAutoCadastroController {

    @FXML
    private ComboBox<String> comboTipoUsuario;

    @FXML
    private TextField campoNome, campoEmail, campoIdentificador;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Label labelMensagem;

    private final AlunoController alunoController;
    private final ProfessorController professorController;

    public TelaDeAutoCadastroController() {
        this.alunoController = new AlunoController(AlunoRepositoryImpl.getInstancia());
        this.professorController = new ProfessorController(ProfessorRepositoryImpl.getInstancia());
    }

    @FXML
    private void initialize() {
        comboTipoUsuario.getItems().addAll("Aluno", "Professor");
    }

    @FXML
    private void cadastrarUsuario() {
        String tipo = comboTipoUsuario.getValue();
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String senha = campoSenha.getText();
        String identificador = campoIdentificador.getText();

        if (tipo == null || nome.isBlank() || email.isBlank() || senha.isBlank() || identificador.isBlank()) {
            setMensagem("Preencha todos os campos.", false);
            return;
        }

        try {
            if (tipo.equals("Aluno")) {
                Aluno aluno = new Aluno(identificador, nome, email, null, senha);
                alunoController.cadastrarAluno(aluno);
                setMensagem("Aluno cadastrado com sucesso!", true);
                limparCampos();

            } else if (tipo.equals("Professor")) {
                Professor professor = new Professor(identificador, nome, email, null, senha);
                professorController.cadastrarProfessor(professor);
                setMensagem("Professor cadastrado com sucesso!", true);
                limparCampos();

            } else {
                setMensagem("Tipo de usuário inválido.", false);
            }
        } catch (Exception e) {
            setMensagem(e.getMessage(), false);
        }
    }

    @FXML
    private void voltarParaLogin() {
        try {
            Stage stage = (Stage) comboTipoUsuario.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaDeLogon.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela de Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            setMensagem("Erro ao retornar para tela de login.", false);
        }
    }

    private void limparCampos() {
        comboTipoUsuario.setValue(null);
        campoNome.clear();
        campoEmail.clear();
        campoSenha.clear();
        campoIdentificador.clear();
    }

    private void setMensagem(String mensagem, boolean sucesso) {
        labelMensagem.setText(mensagem);
        labelMensagem.setStyle(sucesso ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }
}
