package proj.provas.aplicacao.view.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import proj.provas.aplicacao.controller.ProvaController;
import proj.provas.aplicacao.model.Disciplina;
import proj.provas.aplicacao.model.Professor;
import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.model.Turma;
import proj.provas.aplicacao.repository.impl.ProvaRepositoryImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;

public class TelaListaDeProvasController {

    @FXML private TableView<Prova> tabelaProvas;
    @FXML private TableColumn<Prova, String> colunaId;
    @FXML private TableColumn<Prova, String> colunaDisciplina;
    @FXML private TableColumn<Prova, String> colunaTurma;
    @FXML private TableColumn<Prova, String> colunaProfessor;
    @FXML private TableColumn<Prova, String> colunaDataHora;

    private final ProvaController provaController = new ProvaController(ProvaRepositoryImpl.getInstance());

    @FXML
    private void initialize() {
        colunaId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

        colunaDisciplina.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDisciplina().getNome()));

        colunaTurma.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTurma().getIdentificacao()));

        colunaProfessor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProfessorResponsavel().getNomeCompleto()));

        colunaDataHora.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDataAplicacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));

        carregarProvasDeExemplo();
        carregarProvas();
    }

    private void carregarProvas() {
        ObservableList<Prova> provas = FXCollections.observableArrayList(provaController.listarProvas());
        tabelaProvas.setItems(provas);
    }

    @FXML
    private void voltarParaPaginaProfessor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaPaginaProfessor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) tabelaProvas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Painel Professor");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaCadastroProva() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaDeCadastroDeProvas.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) tabelaProvas.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Cadastro de Provas");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarProvasDeExemplo() {
        if (provaController.listarProvas().isEmpty()) {
            Prova prova1 = new Prova(
                    UUID.randomUUID().toString(),
                    new Turma("Turma A", null, null, null, null),
                    new Disciplina("Matemática", null, 60),
                    new Professor("P1", "Prof. João", "joao@escola.com", null, "123"),
                    LocalDateTime.now().plusDays(3),
                    60,
                    Collections.emptyList(),
                    10.0
            );

            Prova prova2 = new Prova(
                    UUID.randomUUID().toString(),
                    new Turma("Turma B", null, null, null, null),
                    new Disciplina("História", null, 60),
                    new Professor("P2", "Prof. Maria", "maria@escola.com", null, "123"),
                    LocalDateTime.now().plusDays(5),
                    45,
                    Collections.emptyList(),
                    8.0
            );

            provaController.cadastrarProva(prova1);
            provaController.cadastrarProva(prova2);
        }
    }
}
