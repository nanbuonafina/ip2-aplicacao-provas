package proj.provas.aplicacao.model;

import java.util.List;

public class QuestaoObjetiva extends Questao {
    private List<String> alternativas;
    private int idRespostaCorreta;

    public QuestaoObjetiva(int numero, String enunciado, List<String> alternativas, int idRespostaCorreta) {
        super(numero, enunciado);
        this.alternativas = alternativas;
        this.idRespostaCorreta = idRespostaCorreta;
    }

    public List<String> getAlternativas() { return alternativas; }
    public void setAlternativas(List<String> alternativas) { this.alternativas = alternativas; }

    public int getIdRespostaCorreta() { return idRespostaCorreta; }
    public void setIdRespostaCorreta(int idRespostaCorreta) { this.idRespostaCorreta = idRespostaCorreta; }
}
