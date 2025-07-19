package proj.provas.aplicacao.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.io.Serializable;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import com.itextpdf.layout.borders.SolidBorder;

public class Relatorio implements Serializable {

    private static final long serialVersionUID = 1L;

    // ============================
    // METODO: Relatório por Aluno
    // ============================
    public static void gerarRelatorioPorAluno(Aluno aluno, List<Resultado> resultados) {
        System.out.println("\n=== RELATÓRIO DE DESEMPENHO POR ALUNO ===");
        System.out.println("Aluno: " + aluno.getNomeCompleto() + " (" + aluno.getMatricula() + ")");
        Map<String, List<Resultado>> agrupadoPorDisciplina = resultados.stream()
                .filter(r -> r.getAluno().equals(aluno))
                .collect(Collectors.groupingBy(r -> r.getProva().getDisciplina().getNome()));

        for (String disciplina : agrupadoPorDisciplina.keySet()) {
            List<Resultado> res = agrupadoPorDisciplina.get(disciplina);
            double media = res.stream().mapToDouble(Resultado::getNotaFinal).average().orElse(0.0);

            System.out.println("Disciplina: " + disciplina);
            for (Resultado r : res) {
                System.out.println("- Prova " + r.getProva().getId() + ": " + r.getNotaFinal() + " / " + r.getProva().getNotaTotal());
            }
            System.out.println("Média na disciplina: " + media);
            System.out.println("--------------------------------------");
        }
    }

    // ============================
    // METODO: Relatório por Turma
    // ============================
    public static void gerarRelatorioPorTurma(Turma turma, List<Resultado> resultados) {
        System.out.println("\n=== RELATÓRIO DE DESEMPENHO POR TURMA ===");
        System.out.println("Turma: " + turma.getIdentificacao());

        List<Resultado> resultadosTurma = resultados.stream()
                .filter(r -> r.getProva().getTurma().equals(turma))
                .collect(Collectors.toList());

        Map<String, List<Resultado>> agrupadoPorDisciplina = resultadosTurma.stream()
                .collect(Collectors.groupingBy(r -> r.getProva().getDisciplina().getNome()));

        for (String disciplina : agrupadoPorDisciplina.keySet()) {
            List<Resultado> res = agrupadoPorDisciplina.get(disciplina);
            double media = res.stream().mapToDouble(Resultado::getNotaFinal).average().orElse(0.0);

            System.out.println("Disciplina: " + disciplina);
            for (Resultado r : res) {
                System.out.println("- Aluno " + r.getAluno().getNomeCompleto() + ": " + r.getNotaFinal() + " / " + r.getProva().getNotaTotal());
            }
            System.out.println("Média da turma na disciplina: " + media);
            System.out.println("--------------------------------------");
        }
    }

    // ============================
    // METODO: Relatório por Disciplina
    // ============================
    public static void gerarRelatorioPorDisciplina(Disciplina disciplina, List<Resultado> resultados) {
        System.out.println("\n=== RELATÓRIO DE DESEMPENHO POR DISCIPLINA ===");
        System.out.println("Disciplina: " + disciplina.getNome());

        List<Resultado> resultadosDisciplina = resultados.stream()
                .filter(r -> r.getProva().getDisciplina().equals(disciplina))
                .collect(Collectors.toList());

        Map<Turma, List<Resultado>> agrupadoPorTurma = resultadosDisciplina.stream()
                .collect(Collectors.groupingBy(r -> r.getProva().getTurma()));

        for (Turma turma : agrupadoPorTurma.keySet()) {
            List<Resultado> res = agrupadoPorTurma.get(turma);
            double media = res.stream().mapToDouble(Resultado::getNotaFinal).average().orElse(0.0);

            System.out.println("Turma: " + turma.getIdentificacao());
            for (Resultado r : res) {
                System.out.println("- Aluno " + r.getAluno().getNomeCompleto() + ": " + r.getNotaFinal() + " / " + r.getProva().getNotaTotal());
            }
            System.out.println("Média da turma na disciplina: " + media);
            System.out.println("--------------------------------------");
        }
    }

    // ==========================================
    // EXPORTAÇÃO DE RELATÓRIOS EM CSV
    // ==========================================
    public static void exportarParaCSV(List<Resultado> resultados, String caminhoArquivo) {
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            writer.append("Aluno,Matricula,Turma,Disciplina,Prova,Nota Obtida,Nota Máxima,Situação,Resumo\n");

            for (Resultado r : resultados) {
                writer.append(r.getAluno().getNomeCompleto()).append(",");
                writer.append(r.getAluno().getMatricula()).append(",");
                writer.append(r.getProva().getTurma().getIdentificacao()).append(",");
                writer.append(r.getProva().getDisciplina().getNome()).append(",");
                writer.append(r.getProva().getId()).append(",");
                writer.append(String.valueOf(r.getNotaFinal())).append(",");
                writer.append(String.valueOf(r.getProva().getNotaTotal())).append(",");
                writer.append(r.getSituacao()).append(",");
                writer.append(r.getResumo().replace(",", ";")).append("\n");
            }

            System.out.println("Relatório exportado com sucesso para: " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao exportar o relatório: " + e.getMessage());
        }
    }

    // ============================
    // METODO: Relatório em PDF
    // ============================

    public static void exportarParaPDF(List<Resultado> resultados, String caminhoArquivo) {
        try {
            PdfWriter writer = new PdfWriter(caminhoArquivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Relatório de Desempenho")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            // Cabeçalho da tabela
            Table tabela = new Table(UnitValue.createPercentArray(
                    new float[]{3, 2, 2, 2, 2, 2, 2, 2, 4}))
                    .useAllAvailableWidth();

            tabela.addHeaderCell(new Cell().add(new Paragraph("Aluno")).setBold());
            tabela.addHeaderCell(new Cell().add(new Paragraph("Matrícula")).setBold());
            tabela.addHeaderCell(new Cell().add(new Paragraph("Turma")).setBold());
            tabela.addHeaderCell(new Cell().add(new Paragraph("Disciplina")).setBold());
            tabela.addHeaderCell(new Cell().add(new Paragraph("Prova")).setBold());
            tabela.addHeaderCell(new Cell().add(new Paragraph("Nota Obtida")).setBold());
            tabela.addHeaderCell(new Cell().add(new Paragraph("Nota Máxima")).setBold());
            tabela.addHeaderCell(new Cell().add(new Paragraph("Situação")).setBold());
            tabela.addHeaderCell(new Cell().add(new Paragraph("Resumo")).setBold());

            // Dados da tabela
            for (Resultado r : resultados) {
                tabela.addCell(r.getAluno().getNomeCompleto());
                tabela.addCell(r.getAluno().getMatricula());
                tabela.addCell(r.getProva().getTurma().getIdentificacao());
                tabela.addCell(r.getProva().getDisciplina().getNome());
                tabela.addCell(r.getProva().getId());
                tabela.addCell(String.valueOf(r.getNotaFinal()));
                tabela.addCell(String.valueOf(r.getProva().getNotaTotal()));
                tabela.addCell(r.getSituacao());
                tabela.addCell(r.getResumo());
            }

            document.add(tabela);

            document.close();
            System.out.println("Relatório PDF exportado com sucesso para: " + caminhoArquivo);
        } catch (Exception e) {
            System.out.println("Erro ao gerar PDF: " + e.getMessage());
        }
    }



}
