package proj.provas.aplicacao.view.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import proj.provas.aplicacao.controller.ProvaController;
import proj.provas.aplicacao.controller.QuestaoController;
import proj.provas.aplicacao.model.*;
import proj.provas.aplicacao.repository.impl.ProvaRepositoryImpl;
import proj.provas.aplicacao.repository.impl.QuestaoRepositoryImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TelaDeCadastroDeProvasController {

    @FXML
    private TextField campoTitulo;

    @FXML
    private TextField campoValor;

    @FXML
    private TextField campoAlternativas;

    @FXML
    private TextField campoRespostaCorreta;

    @FXML
    private ComboBox<String> campoTipo;

    @FXML
    private ListView<Questao> listaQuestoes;

    @FXML
    private TextField campoDisciplina;

    @FXML
    private TextField campoTurma;

    @FXML
    private TextField campoProfessor;

    @FXML
    private TextField campoDuracao;

    private final ProvaController provaController =
            new ProvaController(ProvaRepositoryImpl.getInstance());

    private final QuestaoController questaoController =
            new QuestaoController(QuestaoRepositoryImpl.getInstance());

    // Agora usando ArrayList comum, pois é serializável
    private List<Questao> questoesAdicionadas = new ArrayList<>();

    @FXML
    private void initialize() {
        campoTipo.setItems(FXCollections.observableArrayList("Objetiva", "Dissertativa"));
        listaQuestoes.setItems(FXCollections.observableArrayList(questoesAdicionadas));
        listaQuestoes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void adicionarQuestao() {
        String tipo = campoTipo.getValue();
        String enunciado = campoTitulo.getText();
        double valor;

        try {
            valor = Double.parseDouble(campoValor.getText());
        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "Valor da questão inválido.");
            return;
        }

        if (tipo == null || enunciado.isBlank()) {
            exibirAlerta("Erro", "Preencha todos os campos obrigatórios.");
            return;
        }

        Questao novaQuestao;

        if (tipo.equals("Objetiva")) {
            List<String> alternativas = Arrays.stream(campoAlternativas.getText().split(";"))
                    .map(String::trim)
                    .collect(Collectors.toList());
            int respostaCorreta;
            try {
                respostaCorreta = Integer.parseInt(campoRespostaCorreta.getText());
            } catch (NumberFormatException e) {
                exibirAlerta("Erro", "Índice da resposta correta inválido.");
                return;
            }
            novaQuestao = new QuestaoObjetiva(questoesAdicionadas.size() + 1, enunciado, alternativas, respostaCorreta, valor);
        } else {
            novaQuestao = new QuestaoDissertativa(questoesAdicionadas.size() + 1, enunciado, valor);
        }

        questoesAdicionadas.add(novaQuestao);
        atualizarListaVisual();
        questaoController.adicionarQuestao(novaQuestao);
        limparCamposQuestao();
    }

    @FXML
    private void cadastrarProva() {
        String nomeDisciplina = campoDisciplina.getText();
        String nomeTurma = campoTurma.getText();
        String nomeProfessor = campoProfessor.getText();
        String duracaoStr = campoDuracao.getText();
        String titulo = campoTitulo.getText();

        if (titulo.isBlank() || nomeDisciplina.isBlank() || nomeTurma.isBlank() || nomeProfessor.isBlank()) {
            exibirAlerta("Erro", "Preencha todos os campos obrigatórios da prova.");
            return;
        }

        int duracao;
        try {
            duracao = Integer.parseInt(duracaoStr);
        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "A duração deve ser um número inteiro.");
            return;
        }

        Disciplina disciplina = new Disciplina(nomeDisciplina, null, 160);
        Turma turma = new Turma(nomeTurma, null, null, null, null);
        Professor professor = new Professor(null, nomeProfessor, null, null, null);

        Prova prova = new Prova(
                UUID.randomUUID().toString(),
                turma,
                disciplina,
                professor,
                LocalDateTime.now(),
                duracao,
                new ArrayList<>(questoesAdicionadas), // usar lista simples
                calcularNotaTotal()
        );

        provaController.cadastrarProva(prova);
        limparTudo();
        exibirAlerta("Sucesso", "Prova cadastrada com sucesso!");
    }

    private double calcularNotaTotal() {
        return questoesAdicionadas.stream()
                .mapToDouble(Questao::getValor)
                .sum();
    }

    private void atualizarListaVisual() {
        listaQuestoes.setItems(FXCollections.observableArrayList(questoesAdicionadas));
    }

    private void limparCamposQuestao() {
        campoTipo.getSelectionModel().clearSelection();
        campoValor.clear();
        campoAlternativas.clear();
        campoRespostaCorreta.clear();
    }

    private void limparTudo() {
        campoTitulo.clear();
        limparCamposQuestao();
        questoesAdicionadas.clear();
        atualizarListaVisual();
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void voltarParaLista() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaListaDeProvas.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) campoTitulo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Lista de Questões");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            exibirAlerta("Erro", "Não foi possível voltar para a lista de questões.");
        }
    }
}
