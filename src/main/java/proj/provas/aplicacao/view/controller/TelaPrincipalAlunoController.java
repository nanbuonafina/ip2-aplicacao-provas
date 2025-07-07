package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.session.Sessao;

import java.io.InputStream;

public class TelaPrincipalAlunoController {

    @FXML
    private ImageView fotoAluno;

    @FXML
    private Button botaoPerfil, botaoProvas, botaoFeedback;

    @FXML
    private Label labelBemVindo;

    private final Sessao sessao = Sessao.getInstance();

    @FXML
    public void initialize() {
        carregarImagemPadrao();
        configurarMensagemBoasVindas();
    }

    private void carregarImagemPadrao() {
        InputStream imagemStream = getClass().getResourceAsStream("/assets/BannerDadosAluno.png");
        if (imagemStream != null) {
            Image imagem = new Image(imagemStream);
            fotoAluno.setImage(imagem);
        }
    }

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
        abrirTela("/aluno/TelaDadosAluno.fxml", "Perfil do Aluno");
    }

    @FXML
    private void abrirProvas() {
        abrirTela("/aluno/TelaDeMenuDeProvas.fxml", "Provas");
    }

    @FXML
    private void abrirFeedback() {
        abrirTela("/aluno/TelaDeConsultaDeNotas.fxml", "Feedback");
    }

    private void abrirTela(String caminhoFXML, String titulo) {
        try {
            Stage stage = (Stage) fotoAluno.getScene().getWindow();
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