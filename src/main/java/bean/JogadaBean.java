package bean;


import dao.JogadaDao;
import model.Jogada;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.*;
	
@ManagedBean
@ViewScoped
public class JogadaBean {
    private Jogada jogada = new Jogada();
    private JogadaDao dao = new JogadaDao();
    private List<Jogada> jogadas;

    private static final String[] MOVES = {"Pedra", "Papel", "Tesoura"};

    public void salvar() {
        Random random = new Random();
        jogada.setJogada1(MOVES[random.nextInt(3)]);
        jogada.setJogada2(MOVES[random.nextInt(3)]);
        jogada.setResultado(determinarResultado(jogada.getJogada1(), jogada.getJogada2()));
        jogada.setData(new Date());

        dao.salvar(jogada);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Jogada salva!"));
        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(jogada.getJogador1() + " jogou: " + jogada.getJogada1() ));
        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(jogada.getJogador2() + " jogou: " + jogada.getJogada2() ));
        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Resultado do Jogo: " + jogada.getResultado()));
        
        jogada = new Jogada();
    }

    public void editar(Jogada jogada) {
        dao.editar(jogada);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Objeto editado com sucesso"));
    }

    public void excluir(Integer id) {
        dao.excluir(id);
        jogadas = null; // Força recarregar a lista
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Objeto excluído com sucesso"));
    }

    public List<Jogada> getJogadas() {
        if (jogadas == null) {
            jogadas = dao.listar();
        }
        return jogadas;
    }

    private String determinarResultado(String jogada1, String jogada2) {
        if (jogada1.equals(jogada2)) return "Empate";
        if ((jogada1.equals("Pedra") && jogada2.equals("Tesoura")) ||
            (jogada1.equals("Tesoura") && jogada2.equals("Papel")) ||
            (jogada1.equals("Papel") && jogada2.equals("Pedra"))) {
            return "Jogador 1";
        }
        return "Jogador 2";
    }

    public Jogada getJogada() { return jogada; }
    public void setJogada(Jogada jogada) { this.jogada = jogada; }
    
}
