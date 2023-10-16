import greenfoot.*;
import java.util.List;

public class Chefe extends Actor {
    private int vida;
    private int largura = 1000;
    private int altura = 30;
    private Color corVermelha = Color.RED;

    private int velocidadeX;
    private int causarDano;

    private int valorTempo;
    private int tempoDeEspera;

    private int distanciaBordaX = 130;
    private int invocacoes = 0;
    private int qnt;
    private int qntChancesInvocarInimigos;

    private boolean ladoEsquerdo = false;
    private boolean navegando = false;
    
    private int contadorDeAtraso = 0;

    private BarraFlex barraVida;
    private Inimigo drone;

    public Chefe(int vida, int causarDano, int valorTempo) {
        this.vida = vida;
        this.causarDano = causarDano;
        this.valorTempo = valorTempo;
    }

    public void addedToWorld(World world) {
        barraVida = new BarraFlex(vida, vida, largura, altura, corVermelha);
        getWorld().addObject(barraVida, getWorld().getWidth() / 2, 25);
    }

    public void act() {
        acao();
        verificarColisoesComJogadores();
    }

    public void controlarInvocacao(int qnt, int qntChancesInvocarInimigos) {
        this.qnt = qnt;
        this.qntChancesInvocarInimigos = qntChancesInvocarInimigos;
    }

    public void acao() {
        if (tempoDeEspera > 0) { // Se for > 0, significa que ele está parado
            tempoDeEspera--;
            navegando = false;
            
            if (invocacoes < qnt) {
                invocarInimigo();
                invocacoes++;
            }
            
        } else {
            invocacoes = 0;
        }

        if (tempoDeEspera <= 0) {
            navegando = true;
            if (ladoEsquerdo) {
                moverParaDireita();
            } else {
                moverParaEsquerda();
            }
        }
    }

    public void dano(int dano) {
        vida -= dano;
        barraVida.diminuirVida(dano);

        System.out.println("Vida BOSS: " + vida);
        if (vida < 1) {
            getWorld().removeObject(barraVida);
            getWorld().removeObject(this);
        }
    }

    public void invocarInimigo() {
        
    }

    public void moverParaEsquerda() {
        if (getX() > distanciaBordaX) {
            move(-velocidadeX);
        } else {
            ladoEsquerdo = true;
            tempoDeEspera = valorTempo;
            //setRotation(180);
        }
    }

    public void moverParaDireita() {
        if (getX() < (getWorld().getWidth()) - distanciaBordaX) {
            move(velocidadeX);
        } else {
            ladoEsquerdo = false;
            tempoDeEspera = valorTempo;
        }
    }

    public void verificarColisoesComJogadores() {
        Jogador jogador1 = (Jogador) getOneIntersectingObject(Jogador1.class);
        Jogador jogador2 = (Jogador) getOneIntersectingObject(Jogador2.class);

        if (jogador1 != null) {
            jogador1.receberAtaque(causarDano);
        }

        if (jogador2 != null) {
            jogador2.receberAtaque(causarDano);
        }
    }

    public int pegarVida() {
        return vida;
    }

    public void definirVelocidade(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }

    public void definirTempoDeEspera(int valorTempo) {
        this.valorTempo = valorTempo;
    }
}
