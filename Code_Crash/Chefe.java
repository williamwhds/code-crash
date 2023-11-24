import greenfoot.*;
import java.util.List;

public class Chefe extends AtorPersonagem { 
    
    /*
     * Configura Barra de Vida
     */
    protected BarraFlex barraVida;
    protected int largura;
    protected int altura;
    protected Color corVermelha;
    
    /*
     * Força
     */
    private int forca;
    
    /*
     * Configurações Gerais
     */
    private int ladoDireito;
    private int ladoEsquerdo;
    protected boolean estaNaEsquerda;
    
    /*
     * Tempo
     */
    protected int tempoEspera;
    protected int tempoAtaque;
    private int valorTempo;
    
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
        
        largura = 1000;
        altura = 30;
        corVermelha = Color.RED;
        
        ladoDireito = 1220-100;
        ladoEsquerdo = 100;
        estaNaEsquerda = false;
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
    
    // Retorna True enquanto o tempo for maior que 0
    public boolean tempoAtaque() {
        tempoAtaque-=1;
        return tempoAtaque > 0;
    }
    
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
    
    public void ativarModoPacifico() {
        super.ativarModoPacifico();
    }
    
    public void desativarModoPacifico() {
        super.desativarModoPacifico();
    }
}
