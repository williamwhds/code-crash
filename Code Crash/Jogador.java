import greenfoot.*;

public class Jogador extends ObjetoAnimado
{
    /*
     * Teclas chaves dos movimentos do personagem
     */
    private String teclaMoverEsquerda;
    private String teclaMoverDireita;
    private String teclaPular;
    
    private String teclaAtirar;
    private String teclaAtirarParaCima;
    
    private String teclaRegarregar;
    
    /*
     * Mecânicas de movimentos
     */
    private double velocidadeX = 0;
    private double velocidadeY = 0;
    private double forcaSalto = 13;
    
    /*
     * Travar teclas
     */
    private boolean travarTecla = false;
    private boolean travarTeclaAtirar = false;
    private boolean travarTeclaAtirarParaCima = false;

    
    /*
     * Mecânicas de disparos
     */
    private int municao = 15;
    private int danoTiro = 5;
    private int tempoRecargaAtual = 180;
    
    /*
     * Mecânica de verificação
     */
    private boolean estaPulando = false;
    private boolean movendo_Esquerda = false;
    private boolean estaNaPlataforma = false;
    protected boolean receberAtaque = false;
    private boolean recarregando = false;
    
    /*
     * Vida e Imunidade
     */
    private int vida = 10;
    private boolean estaImune = false;
    private int tempoImunidade = 120;
    Coracao coracao;
    
    /*
     * Armazena as animações
     */
    private GreenfootImage[] animCorrendoDir;
    private GreenfootImage[] animCorrendoEsq;
    
    private GreenfootImage[] animEstaticoDir;
    private GreenfootImage[] animEstaticoEsq;
    
    private GreenfootImage[] animProjetil;
    /*
     * Outro
     */
    private Plataforma plataforma;
    
    GreenfootSound somDisparo = new GreenfootSound("Disparo.mp3");
    
    /*
     * Métodos herdados
     */
    public void animar() {
        super.animar();
    }
    
    public void setAnimacaoAtual(GreenfootImage[] anim) {
        super.setAnimacaoAtual(anim);
    }
    
    public void setTempoEntreFrames (int tempo) {
        super.setTempoEntreFrames(tempo);
    }
    
    /*
     * Efeitos sonoros
     */
    public void tocarSomDisparo () {
        if (somDisparo.isPlaying()) {
            somDisparo.stop();
        }
        somDisparo.play();
    }
    
    /*
     * Define teclas do jogador
     */ 
    public Jogador(Coracao coracao) {    
        this.coracao = coracao;
        
        // Gerando animações
        animCorrendoDir = super.gerarAnimacao("player1_correndo", 14);
        animCorrendoEsq = super.espelharAnimacao(animCorrendoDir);
        
        animEstaticoDir = super.gerarAnimacao("player1_estatico", 4);
        animEstaticoEsq = super.espelharAnimacao(animEstaticoDir);
        
        animProjetil = super.gerarAnimacao("escudo", 6);
    }
    
    public void configurarTeclas(String teclaMoverEsquerda, String teclaMoverDireita, String teclaPular, 
                                 String teclaAtirar, String teclaRegarregar, String teclaAtirarParaCima) 
    {
                       
        this.teclaMoverEsquerda = teclaMoverEsquerda;
        this.teclaMoverDireita = teclaMoverDireita;
        this.teclaPular = teclaPular;
        this.teclaAtirar = teclaAtirar;
        this.teclaAtirarParaCima = teclaAtirarParaCima;
        this.teclaRegarregar = teclaRegarregar;
    }
    
    public void act() {
        tempoRecarga();
        movimentos();
        pulos();
        gravidade();
        controlarTiros();
        gerenciarImunidade();
        animar();
    }
    
    /*
     * Controle de movimentos
     */
    public void movimentos() {
        if (Greenfoot.isKeyDown(teclaMoverEsquerda)) {
            moverEsquerda();
        }
        
        if (Greenfoot.isKeyDown(teclaMoverDireita)) {
            moverDireita();
        }
        
        if (!Greenfoot.isKeyDown(teclaMoverEsquerda) && !Greenfoot.isKeyDown(teclaMoverDireita)){
            parado();
        }
        setLocation(getX() + (int) velocidadeX, getY());
    }
    
    public void moverEsquerda() {
        setAnimacaoAtual(animCorrendoEsq);
        setTempoEntreFrames(5);
        
        velocidadeX = -5;
        movendo_Esquerda = true;
    }
    
    public void moverDireita() {
        setAnimacaoAtual(animCorrendoDir);
        setTempoEntreFrames(5);
        
        velocidadeX = 5;
        movendo_Esquerda = false;
    }
    
    public void parado() {
        if (movendo_Esquerda) {
                setAnimacaoAtual(animEstaticoEsq);
                setTempoEntreFrames(7);
            } else {
                setAnimacaoAtual(animEstaticoDir);
                setTempoEntreFrames(7);
            }
            velocidadeX = 0;
    }
    
    /*
     * Ação de pulo do personagem
     */
    public void pulos() {
        if (Greenfoot.isKeyDown(teclaPular) && !estaPulando && !travarTecla) {
            velocidadeY = -forcaSalto;
            estaPulando = true;
            travarTecla = true;
        }
        
        if (!Greenfoot.isKeyDown(teclaPular)) {
            travarTecla = false;
        }
        
        if (estaPulando) {
            int newY = getY() + (int) velocidadeY; 
            velocidadeY += 0.5;
            
            if (newY >= getWorld().getHeight() - getImage().getHeight() / 2) {
                velocidadeY = 0;
                estaPulando = false;
                int landingHeight = getWorld().getHeight() - getImage().getHeight() / 2;
                newY = landingHeight - 15;
            }
            setLocation(getX(), newY); 
        }
    }
    
    /*
     * Estabelece a física de queda do personagem no mundo
     */
    public void gravidade() {
        
        estaNaPlataforma();
        
        if (!estaPulando && getY() < getWorld().getHeight() - getImage().getHeight() / 2) {
            
            velocidadeY += 0.5;
            setLocation(getX(), getY() + (int) velocidadeY);
            
            if (getY() >= getWorld().getHeight() - getImage().getHeight() / 2) {
                velocidadeY = 0;
                setLocation(getX(), getWorld().getHeight() - getImage().getHeight() / 2);
            }
        }
    }
    
    public void estaNaPlataforma() {
        
        Plataforma plataforma = (Plataforma) getOneIntersectingObject(Plataforma.class);
    
        if (plataforma != null) {
            int alturaDoAtor = getImage().getHeight() / 2;
            int alturaDaPlataforma = plataforma.getImage().getHeight() / 2;
            int novaPosicaoY = plataforma.getY() - alturaDaPlataforma - alturaDoAtor;
            setLocation(getX(), novaPosicaoY);
            estaPulando = false;
        }
        
    }
    
    /*
     * Método de controle de tiros
     */
    public void controlarTiros() {
        if (Greenfoot.isKeyDown(teclaAtirar) && !travarTeclaAtirar){
            atirar();
        }
        
        if (!Greenfoot.isKeyDown(teclaAtirar)) {
            travarTeclaAtirar = false;
        }
    }
        
    public void atirar() {
        if (!recarregando) {
            if (municao > 0) {
                tocarSomDisparo();
                Projetil projetil;
                int alcanceDoTiro = 900;
                
                if (!movendo_Esquerda) {
                    projetil = new Projetil(20, alcanceDoTiro, danoTiro, animProjetil);
                    getWorld().addObject(projetil, getX()+30, getY()+30);
                } else {
                    projetil = new Projetil(-20, alcanceDoTiro, danoTiro, animProjetil);
                    getWorld().addObject(projetil, getX()-30, getY()+30);
                }
        
                //projetil.setRotation(getRotation());
                municao--;
                travarTeclaAtirar = true;
            } else {
                recarregando = true;
            }
        } 
    }
    
    /*
    public void atirarParaCima() {
        if (!recarregando) {
            if (municao > 0) {
                Projetil projetil;
                int alcanceDoTiro = 500; // Defina a altura máxima do tiro para atirar para cima.
    
                if (!movendo_Esquerda) {
                    projetil = new Projetil(0, -alcanceDoTiro, danoTiro); // Ajuste o ângulo para atirar para cima.
                    getWorld().addObject(projetil, getX(), getY());
                } else {
                    projetil = new Projetil(0, -alcanceDoTiro, danoTiro); // Ajuste o ângulo para atirar para cima.
                    getWorld().addObject(projetil, getX(), getY());
                }
    
                municao--;
                travarTeclaAtirarParaCima = true;
            } else {
                recarregando = true;
            }
        }
    }


    public void controlarTiroParaCima() {
        if (Greenfoot.isKeyDown(teclaAtirarParaCima) && !travarTeclaAtirarParaCima){
            atirarParaCima();
        }
    
        if (!Greenfoot.isKeyDown(teclaAtirarParaCima)) {
            travarTeclaAtirarParaCima = false;
        }
    }
    */
    
    public void tempoRecarga() {
        if (recarregando) {
            if (tempoRecargaAtual > 0) {
                tempoRecargaAtual--;
            }
            
            if (municao == 0 && recarregando && tempoRecargaAtual <= 0) {
                recarregando();
            }
        }
        
        if ((Greenfoot.isKeyDown(teclaRegarregar) && municao<15) && municao!=0) {
            recarregando();
        }
    }
    
    public void recarregando() {
        municao = 15;
        recarregando = false;
        tempoRecargaAtual = 180;
    }
    
    /*
     * Receber dano dos inimigos
     */
    public void receberAtaque(int dano) {
        if (!estaImune && verificaVida()) {
            vida-=dano;
            tornarImune();
            receberAtaque = true;
            
            coracao.atualizarVida(getWorld(), dano);
        }
        //System.out.println(vida); // Imprime Vida
    }
    
    /*
     * Verifica a vida do personagem
     */
    public boolean verificaVida() {
        return vida > 0;
    }
    
    /*
     * Torna o jogador imune após levar dano
     */
    public void gerenciarImunidade() {
        if (estaImune) {
            tempoImunidade--;
            if (tempoImunidade <= 0) {
                estaImune = false;
            }
        }
    }

    public void tornarImune() {
        estaImune = true;
        tempoImunidade = 180;
    }
    
    public boolean noChao() {
        if (isTouching(Plataforma.class)) {
            return true;
        }
        return false;
    }
}
