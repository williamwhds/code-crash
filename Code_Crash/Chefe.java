import greenfoot.*;
import java.util.List;

public class Chefe extends AtorPersonagem { 
    
    /*
     * Configura Barra de Vida
     */
    protected BarraFlex barraVida;
    protected int largura = 1000;
    protected int altura = 30;
    protected Color corVermelha = Color.RED;
    
    /*
     * Configura Caracteristicas Físicas
     */
    private int velocidadeX;
    private int forca;
    
    /*
     * Configurações Gerais
     */
    private int ladoDireito = 1220-100;
    private int ladoEsquerdo = 100;
    protected boolean estaNaEsquerda = false;
    
    /*
     * REMOVER
     */
    protected int tempoEspera;
    protected int tempoAtaque;
    
    
    private int valorTempo;
    private int tempoDeEspera;
    private int distanciaBordaX = 130;
    private int invocacoes = 0;
    private int qnt;
    private int qntChancesInvocarInimigos;
    //private boolean ladoEsquerdo = false;
    private boolean navegando = false;
    
    /*
     * Configurar Animação
     */
    protected GreenfootImage[] animParadoDir;
    protected GreenfootImage[] animParadoEsq;
    protected GreenfootImage[] animAtacandoDir;
    protected GreenfootImage[] animAtacandoEsq;
    protected GreenfootImage[] animCloneDir;
    protected GreenfootImage[] animCloneEsq;
    
    protected EstadoChefe estadoChefeAtual;

    public Chefe(int vida, int forca) {
        super.vida = vida;
        this.forca = forca;
    }
    
    /*
     * Adiciona objetos referentes ao Chefe no mundo
     */
    public void addedToWorld(World world) {
        barraVida = new BarraFlex(largura, altura, vida, vida, corVermelha);
        getWorld().addObject(barraVida, getWorld().getWidth() / 2, 25);
    }
    
    public void act() {
        super.gerenciarImunidade();
        animChefe();
        //acao();
        //verificarColisoesComJogadores();
    }
    
    /*
     * Verifica se o Chefe colidiu com o Jogador e dar dano neles
     */
    public void verificarColisoesComJogadores() {
        Jogador jogador1 = (Jogador) getOneIntersectingObject(Jogador1.class);
        Jogador jogador2 = (Jogador) getOneIntersectingObject(Jogador2.class);

        if (jogador1 != null) {
            jogador1.receberAtaque(forca);
        }

        if (jogador2 != null) {
            jogador2.receberAtaque(forca);
        }
    } 
    
    /*
     * Recebe dano
     */
    public void receberAtaque(int dano) {
        
        if (!super.estaImune && estaVivo) {
            super.vida-=dano;
            barraVida.diminuirValor(dano);
            super.efeitoSangue();            
            if (vida <= 0 ) {
                //CodeCrash world = (CodeCrash) getWorld();
                //world.chefeDerrotado();
                
                efeitoFumaca(10);
                vida = 0;
                getWorld().removeObject(barraVida);
                getWorld().removeObject(this);
            }
        }
    }
    
    public void ficarImune() {
        super.ficarImune();
    }
    
    public void tornarVulneravel() {
        super.tornarVulneravel();
    }
    
    /*
     * Maquina de Estado
     */
    public void animChefe() {
        switch (estadoChefeAtual) {
            case PARADO_DIR:
                estadoParadoDir();
                break;
                
            case PARADO_ESQ:
                estadoParadoEsq();
                break;
                
            case ATACANDO_DIR:
                estadoAtacandoDir();
                break;
                
            case ATACANDO_ESQ:
                estadoAtacandoEsq();
                break;
        }
    }
    
        // Animação do Chefe Parado Direita
    public void estadoParadoDir() {
        super.setAnimacaoAtual(animParadoDir);
        super.setTempoEntreFrames(20);
    }
    
        // Animação do Chefe Parado Esquerda
    public void estadoParadoEsq() {
        super.setAnimacaoAtual(animParadoEsq);
        super.setTempoEntreFrames(20);
    }
    
        // Animação do Chefe Atacando Direita
    public void estadoAtacandoDir() {
        super.setAnimacaoAtual(animAtacandoDir);
        super.setTempoEntreFrames(5);
    }
    
        // Animação do Chefe Atacando Esquerda
    public void estadoAtacandoEsq() {
        super.setAnimacaoAtual(animAtacandoEsq);
        super.setTempoEntreFrames(10);
    }
        
    /*
     * Pegar e Definir
     */
    
    public int pegarVida() {
        return vida;
    }
    
    public int pegarLadoEsquerdo() {
        return ladoEsquerdo;
    }
    
    public int pegarLadoDireito() {
        return ladoDireito;
    }
    
    public void definirEstaNaEsquerda(boolean estaNaEsquerda) {
        this.estaNaEsquerda = estaNaEsquerda;
    }
    
    // Retorna True se tempo chegar a 0
    public boolean tempoEspera() {
        this.tempoEspera-=1;
        if (tempoEspera<0) tempoEspera=0;
        
        return tempoEspera==0;
    }
    
    // Retorna True enquanto o tempo for maior que 0
    public boolean tempoAtaque() {
        tempoAtaque-=1;
        return tempoAtaque > 0;
    }
    
        
    /*
    
    public void definirValorTempo() {
        this.valorTempo = valorTempo;
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
    
    public void definirVelocidade(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }

    public void definirTempoDeEspera(int valorTempo) {
        this.valorTempo = valorTempo;
    } */
    
    public void gravidade() 
    {
        if (!noChao()) {
            setLocation(getX(), getY() + 20);
        }
    }
    
    public boolean noChao() {
        if (getOneIntersectingObject(Plataforma.class) != null) {
            return true;
        }
        return getY() >= getWorld().getHeight() - getImage().getHeight() / 2;
    }
    
}
