package proj.provas.aplicacao.view.controller;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TelaDeRelatorioDeDesempenhoController {

    private static class DesempenhoAluno {
        String nome;
        double nota;
        List<String> provas;
        String comentario;

        DesempenhoAluno(String nome, double nota, List<String> provas, String comentario) {
            this.nome = nome;
            this.nota = nota;
            this.provas = provas;
            this.comentario = comentario;
        }
    }

    private List<DesempenhoAluno> mockarDados() {
        return Arrays.asList(
                new DesempenhoAluno("Ana Souza", 8.5, Arrays.asList("Prova 1", "Prova 2"), "Bom desempenho."),
                new DesempenhoAluno("Bruno Lima", 6.2, Arrays.asList("Prova 1"), "Pode melhorar em questões dissertativas."),
                new DesempenhoAluno("Carlos Dias", 9.7, Arrays.asList("Prova 2", "Prova 3"), "Excelente participação.")
        );
    }

    @FXML
    private void gerarRelatorioPDF(ActionEvent event) {
        List<DesempenhoAluno> dados = mockarDados();
        String caminho = "relatorio_desempenho.pdf";

        try (PdfWriter writer = new PdfWriter(new FileOutputStream(caminho));
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            document.add(new Paragraph("Relatório de Desempenho")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 3, 4}))
                    .useAllAvailableWidth();

            table.addHeaderCell(new Cell().add(new Paragraph("Aluno")).setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Nota")).setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Provas")).setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Comentário")).setBold());


            for (DesempenhoAluno aluno : dados) {
                table.addCell(aluno.nome);
                table.addCell(String.valueOf(aluno.nota));
                table.addCell(String.join(", ", aluno.provas));
                table.addCell(aluno.comentario);
            }

            document.add(table);
            System.out.println("Relatório PDF gerado com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gerarRelatorioCSV(ActionEvent event) {
        List<DesempenhoAluno> dados = mockarDados();
        String caminho = "relatorio_desempenho.csv";

        try (FileWriter writer = new FileWriter(caminho)) {
            writer.write("Aluno,Nota,Provas,Comentario\n");
            for (DesempenhoAluno aluno : dados) {
                writer.write(String.format("%s,%.2f,\"%s\",\"%s\"\n",
                        aluno.nome, aluno.nota,
                        String.join(" | ", aluno.provas),
                        aluno.comentario));
            }

            System.out.println("Relatório CSV gerado com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voltarParaPaginaProfessor(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaPaginaProfessor.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Página do Professor");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
