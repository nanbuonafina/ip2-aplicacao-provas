package proj.provas.aplicacao.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AplicacaoProva {
    private String id;
    private Prova prova;
    private Aluno aluno;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private boolean finalizada;
    private List<Resposta> respostas;
    private Timer autosaveTimer;

    public AplicacaoProva(String id, Prova prova, Aluno aluno, LocalDateTime dataHoraInicio) {
        this.id = id;
        this.prova = prova;
        this.aluno = aluno;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = null; // Inicialmente nulo, será definido quando a prova for finalizada
        this.finalizada = false; // Inicialmente não finalizada
        this.respostas = new ArrayList<>();

        iniciarAutoSave();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Prova getProva() { return prova; }
    public void setProva(Prova prova) { this.prova = prova; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }

    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }


    public boolean isProvaDisponivel() {
        LocalDateTime agora = LocalDateTime.now();
        return !finalizada && agora.isBefore(dataHoraInicio.plusMinutes(prova.getDuracaoMinutos()));}

    public void isTempoEsgotado() {
        LocalDateTime limite = dataHoraInicio.plusMinutes(prova.getDuracaoMinutos());
        if (LocalDateTime.now().isAfter(limite)) {
            System.out.println("O tempo para a prova foi esgotado.");
            finalizarAplicacao();
        } else {
            System.out.println("Ainda há tempo para responder a prova.");
        }
    }

    private boolean podeEnviar() {
        if (finalizada) {
            return false; // Se a prova já foi finalizada, não pode enviar
        }
        LocalDateTime agora = LocalDateTime.now();
        return agora.isAfter(dataHoraInicio) && !agora.isAfter(dataHoraInicio.plusMinutes(prova.getDuracaoMinutos()));
    }

    public void salvarRespostas() {
        if (!finalizada) {
            // Simula o salvamento das respostas em um banco de dados ou arquivo
            System.out.println("Salvando respostas para a aplicação de prova: " + id);
            for (Resposta resposta : respostas) {
                System.out.println("Respostas do aluno " + resposta.getaluno().getNomeCompleto() + ":");
                System.out.println("Objetivas: " + resposta.getRespostasObjetivas());
                System.out.println("Dissertativas: " + resposta.getRespostasDissertativas());
            }
        } else {
            System.out.println("A prova já foi finalizada, não é possível salvar mais respostas.");
        }
    }

    private void iniciarAutoSave() {
        autosaveTimer = new Timer();
        autosaveTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                salvarRespostas();
            }
        }, 0, 300000);
    }

    private void pararAutoSave() {
        if (autosaveTimer != null) {
            autosaveTimer.cancel();
            autosaveTimer = null;
        }
    }

    public void finalizarAplicacao() {
      if (podeEnviar()) {
          this.dataHoraFim = LocalDateTime.now();
          this.finalizada = true;
          pararAutoSave();
          System.out.println("Aplicação de prova finalizada com sucesso.");
      } else {
            System.out.println("Não é possível finalizar a prova. Verifique se o tempo está esgotado ou se a prova já foi finalizada.");
      }
    }

    public boolean isFinalizada() { return finalizada; }
}
