package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class TelaPaginaProfessorController {

    @FXML private Button btnDadosProf;
    @FXML private Button btnGerenciamento;
    @FXML private Button btnCadastroProvas;
    @FXML private Button btnQuestoes;
    @FXML private Button btnSair;

    @FXML
    public void trocarParaDadosProf(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaDadosPessoaisProfessor.fxml"));
            Parent root = loader.load();
            //Carrega o arquivo FXML da próxima tela

            TelaDadosPessoaisProfessorController controller = loader.getController();
            controller.carregarDadosProf("12345-67", "Fernanda Buonafina", "fernanda@escola.com", "Matemática, Inglês");
            //Obtem palco atual (Stage) e carrega dados do aluno (exemplo)

            Stage stage = (Stage) btnDadosProf.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Meus Dados Pessoais");
            //Define e exibe nova cena

        }catch (Exception erro){
            mostrarErro("Erro ao abrir dados pessoais", erro.getMessage());
        }
    }

    @FXML
    public void trocarParaGerenciamento(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaGerDisciplinas.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnGerenciamento.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gerenciamento de Disciplinas");

        }catch (Exception erro){
            mostrarErro("Erro ao abrir gerenciamento de disciplina", erro.getMessage());
        }
    }

    @FXML
    public void trocarParaListaProvas(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaListaDeProvas.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnCadastroProvas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Listagem de Provas");

        }catch (Exception erro){
            mostrarErro("Erro ao abrir cadastro de provas", erro.getMessage());
        }
    }

    @FXML
    public void trocarParaQuestoes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaDeQuestoes.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnQuestoes.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gerenciador de Questões");

        } catch (Exception erro) {
            mostrarErro("Erro ao abrir a tela de questões", erro.getMessage());
        }
    }

    @FXML
    public void sairParaLogon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaDeLogon.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnSair.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela de Logon");

        } catch (Exception erro) {
            mostrarErro("Erro ao sair", erro.getMessage());
        }
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}