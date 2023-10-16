import greenfoot.*;
import java.util.List;

public class Chefe extends Actor
{
    private int vida;
    private int largura = 1000;
    private int altura = 30;
    private Color corVermelha = Color.RED;
    
    private int velocidadeX;
    private int causarDano;
    
    private int valorTempo;
    private int esperar = valorTempo;
    
    private int distanciaBordaX = 130;
    private int invocacoes = 0;
    private int qnt; // Conta a quantidade de inimigos eu desejo derrotar para executar algo.
    private int qntChancesInvocarInimigos;

    private boolean ladoEsquerdo = false;
    private boolean navegando = false;
    
    private BarraFlex barraVida;
    
    public Chefe(int vida, int causarDano) {
        this.vida = vida;
        this.causarDano = causarDano;
        //this.barraVida = barraVida;
    }
    
    public void addedToWorld(World world) {
        barraVida = new BarraFlex(vida, vida, largura, altura, corVermelha);
        getWorld().addObject(barraVida, 1220/2, 25);
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
        if (esperar > 0) {
            esperar--;
            navegando = false;
            if (invocacoes < qnt && Greenfoot.getRandomNumber(100) < qnt && !navegando) {
                //invocarInimigo1();
            }
        } else {
            invocacoes = 0;
        }
        
        if (esperar <= 0) {
            navegando = true;
            if (ladoEsquerdo) {
                moverParaDireita();
            } else {
                moverParaEsquerda();
            }
        }
    }
    
    public void dano(int dano) {
        vida-=dano;
        barraVida.diminuirVida(dano);
        
        System.out.println("Vida BOSS: " + vida);
        if (vida < 1) {
            getWorld().removeObject(barraVida);
            getWorld().removeObject(this);
        }
    }
    /*
    public void invocarInimigo1() {
        BarraFlex barraVida = new BarraFlex(20, 20, 100, 10, Color.RED);
        List<Inimigo1> inimigos = getWorld().getObjects(Inimigo1.class);
        int quantidadeInimigos = inimigos.size();
        
        if (quantidadeInimigos < qnt) {
            Inimigo inimigo1 = new Inimigo1();
            getWorld().addObject(inimigo1, getX(), getY());
            invocacoes++;
            }
        }
    */
    public void moverParaEsquerda() {
        if (getX() > distanciaBordaX) {
            setLocation(getX() - velocidadeX, getY());
        } else {
            ladoEsquerdo = true;
            esperar = valorTempo;
        }
    }
    
    public void moverParaDireita() {
        if (getX() < (getWorld().getWidth()) - distanciaBordaX) {
            setLocation(getX() + velocidadeX, getY());
        } else {
            ladoEsquerdo = false;
            esperar = valorTempo;
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
    
    /*
     * Métodos Definir - Pegar
     */
    public int pegarVida(){
        return vida;
    }
    
    public void definirVelocidade(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }
    
    public void definirTempoDeEspera(int valorTempo) {
        this.valorTempo = valorTempo;
    }
}
