package proj.provas.aplicacao.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;


public class TelaPaginaProfessorController {

    public void trocarParaDadosProf(ActionEvent event){
        try {
            Parent novaTela = FXMLLoader.load(getClass().getResource("TelaDadosPessoaisProfessor.fxml"));
            //Carrega o arquivo FXML da próxima tela

            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            //Obtem palco atual (Stage)

            Scene scene = new Scene(novaTela);
            stage.setScene(scene);
            stage.show();
            //Define e exibe nova cena

        }catch (Exception erro){
            erro.printStackTrace();
        }
    }
}