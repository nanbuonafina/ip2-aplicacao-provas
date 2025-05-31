package proj.provas.aplicacao.model;

import java.util.List;

public class QuestaoObjetiva extends Questao {
    private List<String> alternativas;
    private int idRespostaCorreta;
<<<<<<< HEAD
    private double valor;

    public QuestaoObjetiva(int numero, String enunciado, List<String> alternativas, int idRespostaCorreta, double valor) {
        super(numero, enunciado, valor);
        this.alternativas = alternativas;
        this.idRespostaCorreta = idRespostaCorreta;
        this.valor = valor;
=======

    public QuestaoObjetiva(int numero, String enunciado, List<String> alternativas, int idRespostaCorreta) {
        super(numero, enunciado);
        this.alternativas = alternativas;
        this.idRespostaCorreta = idRespostaCorreta;
>>>>>>> cf3d8dd43cd9262f60e0ed4ec4d005aec6efcccd
    }

    public List<String> getAlternativas() { return alternativas; }
    public void setAlternativas(List<String> alternativas) { this.alternativas = alternativas; }

    public int getIdRespostaCorreta() { return idRespostaCorreta; }
    public void setIdRespostaCorreta(int idRespostaCorreta) { this.idRespostaCorreta = idRespostaCorreta; }
<<<<<<< HEAD

    public double getValor() { return valor;}
=======
>>>>>>> cf3d8dd43cd9262f60e0ed4ec4d005aec6efcccd
}
