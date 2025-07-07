package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import proj.provas.aplicacao.controller.AlunoController;
import proj.provas.aplicacao.controller.ProfessorController;
import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.model.Professor;
import proj.provas.aplicacao.repository.impl.AlunoRepositoryImpl;
import proj.provas.aplicacao.repository.impl.ProfessorRepositoryImpl;
import proj.provas.aplicacao.session.Sessao;

public class TelaDeLogonController {

    @FXML
    private ComboBox<String> comboTipoUsuario;

    @FXML
    private TextField campoIdentificador;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Label labelMensagem;

    private final Sessao sessao = Sessao.getInstance();

    private final AlunoController alunoController = new AlunoController(AlunoRepositoryImpl.getInstancia());
    private final ProfessorController professorController = new ProfessorController(ProfessorRepositoryImpl.getInstancia());

    @FXML
    private void initialize() {
        comboTipoUsuario.getItems().addAll("Aluno", "Professor");
    }

    @FXML
    private void realizarLogin() {
        String tipo = comboTipoUsuario.getValue();
        String identificador = campoIdentificador.getText();
        String senha = campoSenha.getText();

        if (tipo == null || identificador.isBlank() || senha.isBlank()) {
            setMensagem("Preencha todos os campos.", false);
            return;
        }

        try {
            if (tipo.equals("Aluno")) {
                Aluno aluno = alunoController.buscarAlunoPorMatricula(identificador);
                if (aluno == null || !aluno.getSenha().equals(senha)) {
                    setMensagem("Matrícula ou senha incorreta.", false);
                    return;
                }

                sessao.setUsuarioLogado(aluno);
                carregarTela("/aluno/TelaPrincipalAluno.fxml", "Painel do Aluno");

            } else if (tipo.equals("Professor")) {
                Professor professor = professorController.buscarProfessor(identificador);
                if (professor == null || !professor.getSenha().equals(senha)) {
                    setMensagem("ID ou senha incorreta.", false);
                    return;
                }

                sessao.setUsuarioLogado(professor);
                carregarTela("/professor/TelaPaginaProfessor.fxml", "Painel do Professor");

            } else {
                setMensagem("Tipo de usuário inválido.", false);
            }

        } catch (Exception e) {
            setMensagem("Erro ao realizar login: " + e.getMessage(), false);
            e.printStackTrace();
        }
    }

    @FXML
    private void irParaCadastro() {
        try {
            Stage stage = (Stage) comboTipoUsuario.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaDeAutoCadastro.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Auto Cadastro");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            setMensagem("Erro ao abrir tela de cadastro.", false);
        }
    }

    private void carregarTela(String caminhoFXML, String titulo) throws Exception {
        Stage stage = (Stage) comboTipoUsuario.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle(titulo);
        stage.show();
    }

    private void setMensagem(String texto, boolean sucesso) {
        labelMensagem.setText(texto);
        labelMensagem.setStyle(sucesso ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }
}
