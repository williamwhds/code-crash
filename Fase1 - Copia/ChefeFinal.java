import greenfoot.*;
import java.util.List;

public class ChefeFinal extends Actor
{
    private int vida;
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
    
    public ChefeFinal(int vida, int velocidadeX, int causarDano, int qnt, int valorTempo, int qntChancesInvocarInimigos) {
        this.vida = vida;
        this.velocidadeX = velocidadeX;
        this.causarDano = causarDano;
        this.qnt = qnt;
        this.valorTempo = valorTempo;
        this.qntChancesInvocarInimigos = qntChancesInvocarInimigos;

    }

    public void act() {
        acao();
        verificarColisoesComJogadores();
    }

    public void acao() {
        if (esperar > 0) {
            esperar--;
            navegando = false;
            if (invocacoes < qnt && Greenfoot.getRandomNumber(100) < qnt && !navegando) {
                invocarInimigo1();
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
        System.out.println("Vida BOSS: " + vida);
        if (vida < 1) {
            getWorld().removeObject(this);
        }
    }
    
    public void invocarInimigo1() {
        List<Inimigo1> inimigos = getWorld().getObjects(Inimigo1.class);
        int quantidadeInimigos = inimigos.size();
        
        if (quantidadeInimigos < qnt) {
            InimigosDinamicos inimigo1 = new Inimigo1();
            getWorld().addObject(inimigo1, getX(), getY());
            invocacoes++;
        }
    }

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
        AcoesJogadores jogador1 = (AcoesJogadores) getOneIntersectingObject(Jogador1.class);
        AcoesJogadores jogador2 = (AcoesJogadores) getOneIntersectingObject(Jogador2.class);

        if (jogador1 != null) {
            jogador1.receberAtaque(causarDano);
        }

        if (jogador2 != null) {
            jogador2.receberAtaque(causarDano);
        }
    }
}
