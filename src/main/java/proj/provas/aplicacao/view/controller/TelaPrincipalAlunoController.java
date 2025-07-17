package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.session.Sessao;

// Importações de imagem não são mais necessárias
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import java.io.InputStream;

public class TelaPrincipalAlunoController {

    // Removido: @FXML
    // Removido: private ImageView fotoAluno;

    @FXML
    private Button botaoPerfil, botaoProvas, botaoFeedback;

    @FXML
    private Label labelBemVindo;

    private final Sessao sessao = Sessao.getInstance();

    @FXML
    public void initialize() {
        // Removido: carregarImagemPadrao();
        configurarMensagemBoasVindas();
    }

    // Removido: private void carregarImagemPadrao() { ... }

    private void configurarMensagemBoasVindas() {
        Object usuario = sessao.getUsuarioLogado();

        if (usuario instanceof Aluno aluno) {
            labelBemVindo.setText("Bem-vindo(a), " + aluno.getNomeCompleto() + "!");
        } else {
            labelBemVindo.setText("Bem-vindo(a)!");
        }
    }

    @FXML
    private void abrirPerfil() {
        // Para obter o Stage, podemos usar um dos botões que sempre estarão presentes
        // ou passar o Stage no momento da criação da tela (se for o caso)
        // Usarei um botão como referência para obter a cena e o stage.
        abrirTela( (Stage) botaoPerfil.getScene().getWindow(), "/aluno/TelaDadosAluno.fxml", "Perfil do Aluno");
    }

    @FXML
    private void abrirProvas() {
        abrirTela((Stage) botaoProvas.getScene().getWindow(), "/aluno/TelaDeMenuDeProvas.fxml", "Provas");
    }

    @FXML
    private void abrirFeedback() {
        abrirTela((Stage) botaoFeedback.getScene().getWindow(), "/aluno/TelaDeConsultaDeNotas.fxml", "Feedback");
    }

    // Método abrirTela modificado para receber o Stage diretamente
    private void abrirTela(Stage stage, String caminhoFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}