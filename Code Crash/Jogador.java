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
    protected int municao = 15;
    private int danoTiro = 5;
    private int tempoRecargaAtual = 180;
    
    /*
     * Configuração Barra de Recarga
     */
    int largura = 100;
    int altura = 10;
    BarraFlex barraRecarga;
    
    /*
     * Mecânica de verificação
     */
    private boolean estaPulando = false;
    private boolean movendo_Esquerda = false;
    private boolean estaNaPlataforma = false;
    protected boolean receberAtaque = false;
    private boolean recarregando = false;
    
    /*
     * Vida
     */
    Coracao coracao;
    private int vida = 10;
    private boolean estaVivo = true;
    
    /*
     * Imunidade
     */
    private boolean estaImune = false;
    private int tempoImunidade = 120;
    
    /*
     * Armazena as animações
     */
    private GreenfootImage[] animCorrendoDir;
    private GreenfootImage[] animCorrendoEsq;
    
    private GreenfootImage[] animEstaticoDir;
    private GreenfootImage[] animEstaticoEsq;
    
    private GreenfootImage[] animProjetil;
    
    
    /*
     * Som
     */
    GreenfootSound somDisparo = new GreenfootSound("Disparo.mp3");
    GreenfootSound somRecarregando = new GreenfootSound("Recarregando.mp3");
    
    public void act() { 
        controlarTiros();
        tempoRecarga();
        movimentos();
        pulos();
        gravidade();
        gerenciarImunidade();
        animar();
        moverBarra();
        
        if (vida==0) {
            tirarDoMundo();
            encerrarSons();
        }
        
    }
    
    /*
     * Define teclas do jogador
     */ 
    public Jogador() {    
        // Gerando animações
        animCorrendoDir = super.gerarAnimacao("player1_correndo", 14);
        animCorrendoEsq = super.espelharAnimacao(animCorrendoDir);
        
        animEstaticoDir = super.gerarAnimacao("player1_estatico", 4);
        animEstaticoEsq = super.espelharAnimacao(animEstaticoDir);
        
        animProjetil = super.gerarAnimacao("escudo", 6);
    }
    
    public void addedToWorld(World world, int alturaCoracao) {
        world.addObject(barraRecarga, getX(), getY()-70);
        world.addObject(coracao, 100, alturaCoracao);
    }
    
    /*
     * Barra de recarga
     */
    public void moverBarra() {
        barraRecarga.setLocation(getX(), getY()-70);
    }
    
    /*
     * Métodos de Animação herdados
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
    
    public void encerrarSons() {
        somDisparo.stop();
        somRecarregando.stop();
    }
    
    /*
     * Configuração de Teclas
     */
    public void configTeclaEsquerda (String teclaMoverEsquerda) {
        this.teclaMoverEsquerda = teclaMoverEsquerda;
    }
    
    public void configTeclaDireita (String teclaMoverDireita) {
        this.teclaMoverDireita = teclaMoverDireita;
    }
    
    public void configTeclaPular (String teclaPular) {
        this.teclaPular = teclaPular;
    }
    
    public void configTeclaAtirar (String teclaAtirar) {
        this.teclaAtirar = teclaAtirar;
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
    
    /*
     * Controle da Animação
     */
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
        
        if (municao <= 0) {
            recarregando = true;
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
                
                municao--;
                barraRecarga.diminuirValor(1);
                travarTeclaAtirar = true;
            }
        } 
    }
    
    public void tempoRecarga() {
        if (recarregando) {
            if (tempoRecargaAtual > 0) {
                barraRecarga.setCor(Color.GRAY);
                tempoRecargaAtual--;
                
                if ((tempoRecargaAtual % 12) == 0) {
                    somRecarregando.play();
                    municao++;
                    barraRecarga.setValor(municao);
                }
            }
            
            if (tempoRecargaAtual <= 0) {
                recarregando = false;
                tempoRecargaAtual = 180;
                somRecarregando.stop();
            }
        }
    }
    
    /*
     * Receber dano dos inimigos
     */
    public void receberAtaque(int dano) {
        if (!estaImune && estaVivo) {
            vida-=dano;
            
            if (vida < 0 ) {
                vida = 0;
            }
            
            tornarImune();
            receberAtaque = true;
            coracao.atualizarVida(vida);
        }
    }
    
    public void tirarDoMundo(){
        getWorld().removeObject(barraRecarga);
        getWorld().removeObject(this);
        morrer();
    }
    
    /*
     * Verifica estado do personagem
     */
    public void viver() {
        estaVivo = true;
    }
    
    public void morrer() {
        estaVivo = false;
    }
    
    public boolean estaVivo() {
        return vida > 0;
    }
    
    public void redefinirVida() {
        vida = 10;
        coracao.atualizarVida(vida);
        viver();
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
}
