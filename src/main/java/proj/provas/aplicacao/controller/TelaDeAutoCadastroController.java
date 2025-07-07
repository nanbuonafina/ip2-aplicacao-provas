package proj.provas.aplicacao.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.model.Professor;
import proj.provas.aplicacao.repository.impl.AlunoRepositoryImpl;
import proj.provas.aplicacao.repository.impl.ProfessorRepositoryImpl;
import proj.provas.aplicacao.session.Sessao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaDeAutoCadastroController {

    @FXML
    private ComboBox<String> comboTipoUsuario;

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoEmail;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private TextField campoIdentificador;

    @FXML
    private Label labelMensagem;

    private final AlunoController alunoController;
    private final ProfessorController professorController;

    // Armazenamento local temporário (simulação)
    private final List<Aluno> alunosCadastrados = new ArrayList<>();
    private final List<Professor> professoresCadastrados = new ArrayList<>();

    // Mapeamento de credenciais: id/matricula -> senha
    private final Map<String, String> credenciais = new HashMap<>();

    public TelaDeAutoCadastroController() {
        this.alunoController = new AlunoController(new AlunoRepositoryImpl());
        this.professorController = new ProfessorController(new ProfessorRepositoryImpl());
    }

    @FXML
    private void cadastrarUsuario() {
        String tipo = comboTipoUsuario.getValue();
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String senha = campoSenha.getText();
        String identificador = campoIdentificador.getText();

        if (tipo == null || nome.isEmpty() || email.isEmpty() || senha.isEmpty() || identificador.isEmpty()) {
            labelMensagem.setText("Preencha todos os campos.");
            labelMensagem.setStyle("-fx-text-fill: red;");
            return;
        }

        if (tipo.equals("Aluno")) {
            Aluno aluno = new Aluno(identificador, nome, email, null);
            alunoController.cadastrarAluno(aluno);
            Sessao.getInstance().getAlunosCadastrados().add(aluno);
            Sessao.getInstance().getCredenciais().put(identificador, senha);
            labelMensagem.setText("Aluno cadastrado com sucesso!");
            labelMensagem.setStyle("-fx-text-fill: green;");
        } else if (tipo.equals("Professor")) {
            Professor professor = new Professor(identificador, nome, email, null);
            professorController.cadastrarProfessor(professor);
            Sessao.getInstance().getProfessoresCadastrados().add(professor);
            Sessao.getInstance().getCredenciais().put(identificador, senha);
            labelMensagem.setText("Professor cadastrado com sucesso!");
            labelMensagem.setStyle("-fx-text-fill: green;");
        } else {
            labelMensagem.setText("Selecione um tipo válido.");
            labelMensagem.setStyle("-fx-text-fill: red;");
        }

        limparCampos();
    }

    @FXML
    private void voltarParaLogin() {
        try {
            // Obtém a janela atual
            Stage stage = (Stage) comboTipoUsuario.getScene().getWindow();

            // Carrega o FXML da tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaDeLogon.fxml"));
            Parent root = loader.load();

            // Troca a cena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Tela de Login");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        comboTipoUsuario.setValue(null);
        campoNome.clear();
        campoEmail.clear();
        campoSenha.clear();
        campoIdentificador.clear();
    }

    // Getters para acesso em outras telas (ex: login)
    public List<Aluno> getAlunosCadastrados() {
        return alunosCadastrados;
    }

    public List<Professor> getProfessoresCadastrados() {
        return professoresCadastrados;
    }

    public Map<String, String> getCredenciais() {
        return credenciais;
    }
}
