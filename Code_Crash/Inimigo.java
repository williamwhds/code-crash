import greenfoot.*;
import java.util.List;

public class Inimigo extends AtorPersonagem {
    
    /*
     * Configurar Barra de Vida
     */
    private int vida;
    private int largura = 100;
    private int altura = 10;
    private BarraFlex barraVida;
    private Color corVermelha = Color.RED;
    
    /*
     * Velocidade e Aceleração
     */
    private int forca;
    private int velocidadeX;
    private int velocidadeY;
    private int aceleracao = 1;
    private int FORCA_PULO = -30;
    
    private int tempoDeEspera = 60;
    
    protected boolean removidoDoMundo = false;
    private boolean ativarEspera = false;
    //protected boolean noChao = false;

    public Inimigo(int vida, int forca) {
        this.vida = vida;
        this.forca = forca;
    }
    
    /*
     * Método que adiciona a barra de vida após adicionar o Jogador no mundo
     */
    public void addedToWorld(World world) {
        barraVida = new BarraFlex(largura, altura, vida, vida, corVermelha);
        getWorld().addObject(barraVida, getX(), getY()-70);
    }

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

        if (!ativarEspera && jogador != null) {
            int distanciaX = Math.abs(jogador.getX() - getX());
            int distanciaY = Math.abs(jogador.getY() - getY());
    
            if (distanciaX <= 20 && distanciaY <= 20) {
                jogador.receberAtaque(forca);
                ativarEspera();
            } else {
                if (jogador.getX() > getX()) {
                    moverDireita();
                } else if (jogador.getX() < getX()) {
                    moverEsquerda();
                }
            }
        }
        
        if (vida <= 0) {
            removerDoMundo();
        }
    }
    
    public void dano(int dano) {
        vida-=dano;
        barraVida.diminuirValor(dano);
    }
    
    public void removerDoMundo() {
        if (getWorld() != null) {
            removidoDoMundo = true;
            getWorld().removeObject(barraVida);
        }
    }
    
    /*
     * Movimento da barra de vida
     */
    public void moverBarra() {
        barraVida.setLocation(getX(), getY()-70);
    }
    
    /*
     * Movimentos do Inimigo
     */
    public void moverDireita() {
        move(velocidadeX);
    }
    
    public void moverEsquerda() {
        move(-velocidadeX);
    }
    
    public void moverCima() {
        setLocation(getX(), getY()-velocidadeX);
    }
    
    public void moverBaixo() {
        setLocation(getX(), getY()+velocidadeX);
    }
    
    public void pulos() {
        if (noChao() && !ativarEspera) {
            velocidadeY = FORCA_PULO;
            ativarEspera();
        }
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
    
    public boolean noChao() {
        if (getOneIntersectingObject(Plataforma.class) != null) {
            return true;
        }
        return getY() >= getWorld().getHeight() - getImage().getHeight() / 2;
    }
    
    public Jogador procurarJogadorMaisProximo() {
        CodeCrash world = (CodeCrash)getWorld();
        List<Jogador> jogadores = world.getObjects(Jogador.class);
        Jogador jogadorMaisProximo = null;

        if (!jogadores.isEmpty()) {
            double menorDistancia = Double.MAX_VALUE;
            
            for (Jogador jogador : jogadores) {
                double distancia = pegarDistanciaParaJogador(jogador);
                if (jogador.estaVivo() && (distancia < menorDistancia)) {
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
            int inimigoX = getX();
            int inimigoY = getY();
    
            int deltaX = jogadorX - inimigoX;
            int deltaY = jogadorY - inimigoY;
    
            // Usar o teorema de Pitágoras para calcular a distância
            double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    
            return distancia;
        } else {
            return Double.MAX_VALUE;
        }
    }
 
    
    public void gravidade() 
    {
        if (noChao()) {
            velocidadeY = 0;
        } else {
            velocidadeY += aceleracao;
        }

        setLocation(getX(), getY() + velocidadeY);
    }
    
    public void definirVelocidade(int velocidadeX){
        this.velocidadeX = velocidadeX;
    }
}