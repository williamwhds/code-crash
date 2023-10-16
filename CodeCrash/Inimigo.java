import greenfoot.*;
import java.util.List;

public class Inimigo extends Actor {
    private int vida;
    private int forca;
    private int velocidadeX = 2;
    private int velocidadeY;
    private int aceleracao = 1;
    
    private int largura = 100;
    private int altura = 10;
    private Color corVermelha = Color.RED;
    
    private int tempoDeEspera = 60;
    
    private boolean ativarEspera = false;
    //protected boolean noChao = false;
    protected boolean removidoDoMundo = false;
    
    private static final int FORCA_PULO = -30;
    
    private BarraFlex barraVida;

    public Inimigo(int vida, int forca, int velocidade, BarraFlex barraVida) {
        this.vida = vida;
        this.forca = forca;
        this.velocidadeX = velocidade;
        
        this.barraVida = barraVida;
        
        //BarraFlex barraVida = new BarraFlex(vida, vida, 100, 10, Color.RED);
        
        //adicionarBarraVida(barraVida);
    }
    /*
    public void moverBarra(BarraFlex barraVida) {
        barraVida.setLocation(getX(), getY());
    }
    */

    public void act() {
        if (getWorld() != null) {
            gravidade();
            atacarJogadores();
            tempoRecarga();
            moverBarra();
        }
    }
    
    public void atacarJogadores() {
        Jogador jogador = procurarJogadorMaisProximo();
        
        if (!ativarEspera) {
            if (jogador != null) {
                int distanciaX = jogador.getX() - getX();
                int distanciaY = jogador.getY() - getY();
            
                if (distanciaX == (jogador.getX() - 5) && distanciaY == jogador.getY()) {
                    jogador.receberAtaque(forca);
                    ativarEspera(); 
                } else {
                    if (jogador.getX() > getX()) {
                         moverDireita();
                    } else if (jogador.getX() < getX()) {
                        moverEsquerda();
                    }
                    
                    if (jogador.getY() < getY() && jogador.getX() == getX()) {
                        pulos();
                    }
                }
            }
        }
        
        if (vida <= 0) {
            removerDoMundo();
        }
    }
    
    public void moverDireita() {
        move(velocidadeX);
    }
    
    public void moverEsquerda() {
        move(-velocidadeX);
    }
    
    public void moverBarra() {
        barraVida.setLocation(getX(), getY()-70);
    }
    
    public void pulos() {
        if (noChao() && !ativarEspera) {
            velocidadeY = FORCA_PULO;
            ativarEspera();
        }
    }
    
    public void dano(int dano) {
        vida-=dano;
        barraVida.diminuirVida(dano);
        System.out.println("Vida Inimigo: " + vida);
    }
    
    public void ativarEspera() {
        ativarEspera = true;
    }
    
    public void tempoRecarga() {
        if (ativarEspera) {
            if (tempoDeEspera > 0) {
                tempoDeEspera--;
            }
            
            if (ativarEspera && tempoDeEspera <= 0) {
                ativarEspera = false;
                tempoDeEspera = 60;
            }
        }
    }
    
    public void estaNaPlataforma() {
        List<Plataforma> plataformas = getIntersectingObjects(Plataforma.class);

        if (!plataformas.isEmpty()) {
            int alturaDoAtor = getImage().getHeight() / 2;
            int alturaDaPlataforma = plataformas.get(0).getImage().getHeight() / 2;
            int novaPosicaoY = plataformas.get(0).getY() - alturaDaPlataforma - alturaDoAtor;
            setLocation(getX(), novaPosicaoY);
        }
    }
    
    public Jogador procurarJogadorMaisProximo() {
        CodeCrash world = (CodeCrash)getWorld();
        List<Jogador> jogadores = world.getObjects(Jogador.class);
        Jogador jogadorMaisProximo = null;

        if (!jogadores.isEmpty()) {
            double menorDistancia = Double.MAX_VALUE;
            
            for (Jogador jogador : jogadores) {
                double distancia = pegarDistanciaParaJogador(jogador);
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    jogadorMaisProximo = jogador;
                }
            }
        }

        return jogadorMaisProximo;
    }
    
    public double pegarDistanciaParaJogador(Jogador jogador) {

       if (jogador != null) {
            int jogadorX = jogador.getX();
            int jogadorY = jogador.getY();
            return (getX() - jogadorX) + (getY() - jogadorY);
        } else {
            return Double.MAX_VALUE;
        }
    }
    
    public void removerDoMundo() {
        if (getWorld() != null) {
            removidoDoMundo = true;
            getWorld().removeObject(barraVida);
        }
    }
    
    public void gravidade() 
    {
        estaNaPlataforma();
        
        if (noChao()) {
            velocidadeY = 0;
        } else {
            velocidadeY += aceleracao;
        }

        setLocation(getX(), getY() + velocidadeY);
    }
    
    public boolean noChao() 
    {
        return getY() >= getWorld().getHeight() - getImage().getHeight() / 2;
    }
}