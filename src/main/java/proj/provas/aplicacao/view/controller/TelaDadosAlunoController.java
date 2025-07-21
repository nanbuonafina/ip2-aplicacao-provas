package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.session.Sessao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class TelaDadosAlunoController {

    @FXML
    private Label labelNome, labelEmail, labelMatricula;

    @FXML
    private TableView<NotaSimulada> tabelaNotas;

    @FXML
    private TableColumn<NotaSimulada, String> colProva;

    @FXML
    private TableColumn<NotaSimulada, Double> colNota;

    @FXML
    private TableColumn<NotaSimulada, String> colData;


    private final Sessao sessao = Sessao.getInstance();

    @FXML
    public void initialize() {
        Object usuario = sessao.getUsuarioLogado();

        if (usuario instanceof Aluno aluno) {
            labelNome.setText(aluno.getNomeCompleto());
            labelEmail.setText(aluno.getEmail());
            labelMatricula.setText(aluno.getMatricula());
        } else {
            labelNome.setText("Não encontrado");
            labelEmail.setText("Não encontrado");
            labelMatricula.setText("Não encontrado");
        }

        // Configurar colunas da tabela
        colProva.setCellValueFactory(new PropertyValueFactory<>("prova"));
        colNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        // Dados simulados
        ObservableList<NotaSimulada> dadosNotas = FXCollections.observableArrayList(
                new NotaSimulada("Matemática", 8.5, "01/04/2025"),
                new NotaSimulada("História", 7.0, "15/04/2025"),
                new NotaSimulada("Português", 9.2, "30/04/2025")
        );

        tabelaNotas.setItems(dadosNotas);
    }

    @FXML
    private void voltarTelaPrincipal() {
        try {
            Stage stage = (Stage) labelNome.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aluno/TelaPrincipalAluno.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Painel do Aluno");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class NotaSimulada {
        private final String prova;
        private final double nota;
        private final String data;

        public NotaSimulada(String prova, double nota, String data) {
            this.prova = prova;
            this.nota = nota;
            this.data = data;
        }

        public String getProva() {
            return prova;
        }

        public double getNota() {
            return nota;
        }

        public String getData() {
            return data;
        }
    }
}