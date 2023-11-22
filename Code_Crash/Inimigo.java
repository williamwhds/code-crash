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
    
    /*
     * Configurar Animação
     */
    
    protected GreenfootImage[] animParadoEsq;
    protected GreenfootImage[] animParadoDir;
    protected GreenfootImage[] animAtacandoEsq;
    protected GreenfootImage[] animAtacandoDir;
    
    protected EstadoInimigo estadoAtualInimigo;

    public Inimigo(int vida, int forca) {
        super.vida = vida;
        this.forca = forca;
        
        estadoAtualInimigo = EstadoInimigo.PARADO_ESQ;
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
            
            // super.act();
            super.animar();
            animInimigo();
            
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
                estadoAtualInimigo = EstadoInimigo.ATACANDO_ESQ;
                jogador.receberAtaque(forca);
                ativarEspera();
            } else {
                if (jogador.getX() > getX()) {
                    estadoAtualInimigo = EstadoInimigo.PARADO_DIR;
                    moverDireita();
                } else if (jogador.getX() < getX()) {
                    estadoAtualInimigo = EstadoInimigo.PARADO_ESQ;
                    moverEsquerda();
                }
            }
        }
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
    
    /*
     * Localizar jogadores
     */
    
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
    
        // Pega as cordenadas do jogador mais próximo
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
 
    /*
     * Gravidade e Plataforma
     */
    
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
    
    /*
     * Maquina de Estado
     */
    public void animInimigo() {
        switch (estadoAtualInimigo) {
            case PARADO_ESQ:
                estadoParadoEsq();
                break;
                
            case PARADO_DIR:
                estadoParadoDir();
                break;
                
            case ATACANDO_ESQ:
                estadoAtacandoEsq();
                break;
                
            case ATACANDO_DIR:
                estadoAtacandoDir();
                break;
        }
    }
    
        // Animação do Inimigo Parado Direita
    public void estadoParadoDir() {
        super.setAnimacaoAtual(animParadoDir);
        //super.setTempoEntreFrames(20);
    }
    
        // Animação do Inimigo Parado Esquerda
    public void estadoParadoEsq() {
        super.setAnimacaoAtual(animParadoEsq);
        //super.setTempoEntreFrames(20);
    }
    
        // Animação do Inimigo Atacando Direita
    public void estadoAtacandoDir() {
        super.setAnimacaoAtual(animAtacandoDir);
        super.setTempoEntreFrames(5);
    }
    
        // Animação do Inimigo Atacando Esquerda
    public void estadoAtacandoEsq() {
        super.setAnimacaoAtual(animAtacandoEsq);
        super.setTempoEntreFrames(10);
    }
    
    /*
     * Get e Set
     */
    public void definirVelocidade(int velocidadeX){
        this.velocidadeX = velocidadeX;
    }
    
    public void definirVida(int vida) {
        super.vida = vida;
        barraVida.redefinirValores(vida);
    }
    
    public void definirForca(int forca) {
        this.forca = forca;
    }
}