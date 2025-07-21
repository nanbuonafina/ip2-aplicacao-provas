package proj.provas.aplicacao.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proj.provas.aplicacao.controller.DisciplinaController;
import proj.provas.aplicacao.model.Disciplina;
import proj.provas.aplicacao.repository.impl.DisciplinaRepositoryImpl;

public class TelaGerDisciplinasController {

    @FXML private Button btnVoltarProf;
    @FXML private TableView<Disciplina> tableDisciplinas;
    @FXML private TableColumn<Disciplina, String> colNome;
    @FXML private TableColumn<Disciplina, String> colDescricao;
    @FXML private TableColumn<Disciplina, Integer> colCarga;

    @FXML private TextField txtNome;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtCarga;

    private final DisciplinaController controller = new DisciplinaController(new DisciplinaRepositoryImpl());
    private final ObservableList<Disciplina> listaObservable = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colCarga.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));

        tableDisciplinas.setItems(listaObservable);
        atualizarTabela();

        tableDisciplinas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtNome.setText(newSel.getNome());
                txtDescricao.setText(newSel.getDescricao());
                txtCarga.setText(String.valueOf(newSel.getCargaHoraria()));
            }
        });
    }

    public void voltarProf(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaPaginaProfessor.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnVoltarProf.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Página do professor");

        } catch (Exception erro) {
            mostrarErro("Erro ao voltar:", erro.getMessage());
        }
    }

    public void cadastrarDisciplina() {
        try {
            String nome = txtNome.getText().trim();
            String desc = txtDescricao.getText().trim();
            int carga = Integer.parseInt(txtCarga.getText().trim());

            Disciplina disciplina = new Disciplina(nome, desc, carga);
            controller.cadastrarDisciplina(disciplina);
            atualizarTabela();
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarErro("Erro", "Carga horária deve ser um número.");
        } catch (Exception e) {
            mostrarErro("Erro ao cadastrar", e.getMessage());
        }
    }

    public void atualizarDisciplina() {
        try {
            String nome = txtNome.getText().trim();
            String novaDescricao = txtDescricao.getText().trim();
            controller.atualizarDescricao(nome, novaDescricao);
            atualizarTabela();
            limparCampos();
        } catch (Exception e) {
            mostrarErro("Erro ao atualizar", e.getMessage());
        }
    }

    public void removerDisciplina() {
        try {
            String nome = txtNome.getText().trim();
            controller.removerDisciplina(nome);
            atualizarTabela();
            limparCampos();
        } catch (Exception e) {
            mostrarErro("Erro ao remover", e.getMessage());
        }
    }

    public void limparCampos() {
        txtNome.clear();
        txtDescricao.clear();
        txtCarga.clear();
        tableDisciplinas.getSelectionModel().clearSelection();
    }

    private void atualizarTabela() {
        listaObservable.setAll(controller.listarDisciplinas());
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}