package proj.provas.aplicacao.model;

import java.io.Serializable;

public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String descricao;
    private int cargaHoraria;

    public Disciplina(String nome, String descricao, int cargaHoraria) {
        this.nome = nome;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }
}
