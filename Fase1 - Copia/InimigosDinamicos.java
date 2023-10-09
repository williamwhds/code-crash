import greenfoot.*;
import java.util.List;

public class InimigosDinamicos extends Actor {
    private int vida;
    private int forca;
    protected int velocidadeX;
    private int velocidadeY;
    
    private int tempoDeEspera = 60;
    
    private boolean ativarEspera = false;
    protected boolean noChao = false;
    protected boolean removidoDoMundo = false;
    
    private static final int FORCA_PULO = -15;
    private static final int TEMPO_ESPERA = 60;
    private static final int GRAVIDADE = 1;
    private static final int VELOCIDADE_MAXIMA_Y = 10;

    public InimigosDinamicos(int vida, int forca, int velocidade) {
        this.vida = vida;
        this.forca = forca;
        this.velocidadeX = velocidade;
    }

    public void act() {
        if (getWorld() != null) {
            gravidade();
            atacarJogadores();
            tempoRecarga();
        }
    }
    
    public void atacarJogadores() {
        AcoesJogadores Jogador = procurarJogadorMaisProximo();
        
        if (!ativarEspera) {
            if (Jogador != null) {
                int jogadorX = Jogador.getX();
                int jogadorY = Jogador.getY();
                int distanciaX = Math.abs(Jogador.getX() - getX());
                int distanciaY = Math.abs(Jogador.getY() - getY());
            
                if (distanciaX <= 5 && distanciaY <=5) {
                    Jogador.receberAtaque(forca);
                    ativarEspera(); 
                } else {
                    if (Jogador.getX() > getX()) {
                        move(velocidadeX); // Move para a direita
                    } else if (Jogador.getX() < getX()) {
                        move(-velocidadeX); // Move para a esquerda
                        setLocation(getX(), Jogador.getY());
                    }
                }
            }
        }
        
        if (vida <= 0) {
            removerDoMundo();
        }
    }
    
    public void dano(int dano) {
        vida-=dano;
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
    
    public AcoesJogadores procurarJogadorMaisProximo() {
        CodeCrash world = (CodeCrash)getWorld();
        List<AcoesJogadores> jogadores = world.getObjects(AcoesJogadores.class);
        //AcoesJogadores jogador1 = world.getObjects(Jogador1.class).get(0);
        //AcoesJogadores jogador2 = world.getObjects(Jogador2.class).get(0);
        AcoesJogadores jogadorMaisProximo = null;

        if (!jogadores.isEmpty()) {
            double menorDistancia = Double.MAX_VALUE;
            
            for (AcoesJogadores jogador : jogadores) {
                double distancia = pegarDistanciaParaJogador(jogador);
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    jogadorMaisProximo = jogador;
                }
            }
        }

        return jogadorMaisProximo;
    }
    
    public double pegarDistanciaParaJogador(AcoesJogadores jogador) {

       if (jogador != null) {
            int jogadorX = jogador.getX();
            int jogadorY = jogador.getY();
            return Math.sqrt(Math.pow(getX() - jogadorX, 2) + Math.pow(getY() - jogadorY, 2));
        } else {
            return Double.MAX_VALUE;
        }
    }
    
    public void removerDoMundo() {
        if (getWorld() != null) {
            removidoDoMundo = true;
        }
    }
    
    public boolean estaNoChao() {
        if (isTouching(plataformaPedra.class)) {
            return true;
        }

        if (getY() >= getWorld().getHeight() - getImage().getHeight() / 2) {
            return true;
        }
        return false;
    }

    public void gravidade() {
        if (!estaNoChao()) {
            if (velocidadeY < VELOCIDADE_MAXIMA_Y) {
                velocidadeY += GRAVIDADE;
            }
            setLocation(getX(), getY() + velocidadeY);
        } else {
            velocidadeY = 0;
            setLocation(getX(), getWorld().getHeight() - getImage().getHeight() / 2);
        }
        /*
        if (getY() < getWorld().getHeight() - getImage().getHeight() / 2) {
            velocidadeY += 0.5;
            setLocation(getX(), getY() + (int) velocidadeY);
            if (getY() >= getWorld().getHeight() - getImage().getHeight() / 2) {
                velocidadeY = 0;
                setLocation(getX(), getWorld().getHeight() - getImage().getHeight() / 2);
            }
        }
        */
    }
}