package proj.provas.aplicacao.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

    @FXML
    private void realizarLogin() {
        String tipo = comboTipoUsuario.getValue();
        String identificador = campoIdentificador.getText();
        String senha = campoSenha.getText();

        if (tipo == null || identificador.isEmpty() || senha.isEmpty()) {
            labelMensagem.setText("Preencha todos os campos.");
            return;
        }

        // Valida credenciais
        String senhaArmazenada = sessao.getCredenciais().get(identificador);

        if (senhaArmazenada == null) {
            labelMensagem.setText("Usuário não cadastrado.");
            return;
        }

        if (!senhaArmazenada.equals(senha)) {
            labelMensagem.setText("Senha incorreta.");
            return;
        }

        labelMensagem.setStyle("-fx-text-fill: green;");
        labelMensagem.setText("Login bem-sucedido!");

        try {
            Stage stage = (Stage) comboTipoUsuario.getScene().getWindow();
            FXMLLoader loader;

            // Verifica o tipo e carrega a tela correspondente
            if (tipo.equals("Aluno")) {
                loader = new FXMLLoader(getClass().getResource("/aluno/TelaPrincipalAluno.fxml"));
            } else if (tipo.equals("Professor")) {
                loader = new FXMLLoader(getClass().getResource("/professor/TelaPaginaProfessor.fxml"));
            } else {
                labelMensagem.setText("Tipo de usuário inválido.");
                return;
            }

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel " + tipo);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void irParaCadastro() {
        try {
            Stage stage = (Stage) comboTipoUsuario.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaDeAutoCadastro.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Auto Cadastro");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
