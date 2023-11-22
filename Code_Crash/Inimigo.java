import greenfoot.*;
import java.util.List;

public class Inimigo extends AtorPersonagem {
    
    /*
     * Configurar Barra de Vida
     */
    //private int vida;
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
    private boolean modoPacifico = false;
    //protected boolean noChao = false;

    public Inimigo(int vida, int forca) {
        super.vida = vida;
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
            
            if (!modoPacifico) {
                atacarJogadores();
                tempoRecarga();
                moverBarra();
            }
        }
    }
    
    public void ativarModoPacifico() {
        this.modoPacifico = true;
    }
    
    public void desativarModoPacifico() {
        this.modoPacifico = false;
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
        /*
        if (vida <= 0) {
            receberAtaque(12);
            //removerDoMundo();
        }*/
    }
    
    public void receberAtaque(int dano) {
        if (!super.estaImune && estaVivo) {
            super.receberAtaque(dano);
            barraVida.diminuirValor(dano);
            if (vida == 0) {
              
                World mundo = getWorld();
                if (mundo instanceof CodeCrash) {
                    CodeCrash world = (CodeCrash) getWorld();
                    world.inimigoDerrotado();
                }
                
                getWorld().removeObject(barraVida);
                getWorld().removeObject(this);
                removidoDoMundo = true;
                //estaVivo = false;
            }
        }
        //System.out.println("Vida Inimigo: " + vida);
    }
    /*
    public void removerDoMundo() {
        if (getWorld() != null) {
            removidoDoMundo = true;
            getWorld().removeObject(barraVida);
        }
    }*/
    
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
    
    public boolean noChao() {
        if (getOneIntersectingObject(Plataforma.class) != null) {
            return true;
        }
        return getY() >= getWorld().getHeight() - getImage().getHeight() / 2;
    }
    
    public void definirVelocidade(int velocidadeX){
        this.velocidadeX = velocidadeX;
    }
    
    public void definirVida(int vida) {
        super.vida = vida;
        barraVida.redefinirValores(vida);
        // barraVida = new BarraFlex(largura, altura, vida, vida, corVermelha);
    }
    
    public void definirForca(int forca) {
        this.forca = forca;
    }
}