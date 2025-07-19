package proj.provas.aplicacao.util;

import proj.provas.aplicacao.model.Prova;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtils {

    private static final String CAMINHO_ARQUIVO = "provas.dat";

    public static void salvarProva(Prova novaProva) {
        List<Prova> provasExistentes = carregarProvas(); // carrega as provas anteriores
        provasExistentes.add(novaProva); // adiciona a nova prova
        salvarTodasProvas(provasExistentes); // salva todas
    }

    public static void salvarTodasProvas(List<Prova> provas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(provas);
            System.out.println("Salvando " + provas.size() + " provas no arquivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Prova> carregarProvas() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) {
            System.out.println("Arquivo de provas não existe.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO))) {
            List<Prova> provas = (List<Prova>) ois.readObject();
            System.out.println("Provas carregadas do arquivo: " + provas.size());
            for (Prova prova : provas) {
                System.out.println("- Prova: " + prova.getDisciplina().getNome() + ", Professor: " + prova.getProfessorResponsavel().getNomeCompleto());
            }
            return provas;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar provas:");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
