package proj.provas.aplicacao.session;

public class Sessao {

    private static Sessao instancia;

    private Object usuarioLogado;

    private Sessao() {}

    public static Sessao getInstance() {
        if (instancia == null) {
            instancia = new Sessao();
        }
        return instancia;
    }

    public void setUsuarioLogado(Object usuario) {
        this.usuarioLogado = usuario;
    }

    public Object getUsuarioLogado() {
        return usuarioLogado;
    }

    public void encerrarSessao() {
        usuarioLogado = null;
    }
}
