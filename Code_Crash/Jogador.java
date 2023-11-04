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
    protected boolean receberAtaque = false;
    private boolean recarregando = false;
    private boolean atirou = false;
    
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
    protected GreenfootImage[] animCorrendoDir;
    protected GreenfootImage[] animCorrendoEsq;
    
    protected GreenfootImage[] animEstaticoDir;
    protected GreenfootImage[] animEstaticoEsq;
    
    protected GreenfootImage[] animDano;
    protected GreenfootImage[] animPulando;
    
    protected GreenfootImage[] animAtirarDir;
    protected GreenfootImage[] animAtirarEsq;
    
    protected GreenfootImage[] animProjetil;
    
    /*
     * Configuração da máquina de estado
     */
    private EstadoJogador estadoAtual = EstadoJogador.PARADO_DIR;
    
    /*
     * Som
     */
    GreenfootSound somDisparo = new GreenfootSound("Projetil.mp3");
    GreenfootSound somRecarregando = new GreenfootSound("Recarregando.mp3");
    GreenfootSound somDanoGlitch = new GreenfootSound("dano_glitch.mp3");
    GreenfootSound somRemover = new GreenfootSound("remover.mp3");
    GreenfootSound somPulo = new GreenfootSound("Pulo.mp3");
    
    public void act() { 
        super.animar();
        animJogador();
        movimentos();
        pulos();
        controlarTiros();
        tempoRecarga();
        
        gravidade();
        gerenciarImunidade();
        moverBarra();
        
        if (vida==0) {
            tirarDoMundo();
            encerrarSons();
        }  
    }
 
    public Jogador() {    
        animProjetil = super.gerarAnimacao("escudo", 6);
    }
    
    public void addedToWorld(World world, int posX) {
        world.addObject(barraRecarga, getX(), getY()-70);
        world.addObject(coracao, posX, 70);
    }
    
    /*
     * Barra de recarga
     */
    public void moverBarra() {
        barraRecarga.setLocation(getX(), getY()-70);
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
    
    public void tocarSomPulo () {
        if (somPulo.isPlaying()) somPulo.stop();
        somPulo.play();
    }

    public void encerrarSons() {
        somDisparo.stop();
        somRecarregando.stop();
        somDanoGlitch.stop();
        somPulo.stop();
        //somRemover.stop();
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
     * Controle de Movimentos
     */
    public void moverEsquerda() {
        estadoAtual = EstadoJogador.ANDANDO_ESQ;
        velocidadeX = -5;
        movendo_Esquerda = true;
    }
    
    public void moverDireita() {
        estadoAtual = EstadoJogador.ANDANDO_DIR;
        velocidadeX = 5;
        movendo_Esquerda = false;
    }
    
    public void parado() {
        if (movendo_Esquerda) estadoAtual = EstadoJogador.PARADO_ESQ;
        if (!movendo_Esquerda) estadoAtual = EstadoJogador.PARADO_DIR;
        
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
            
            efeitoFumaca(3);
        }
        
        if (!Greenfoot.isKeyDown(teclaPular)) {
            travarTecla = false;
        }
        
        if (estaPulando) {
            int newY = getY() + (int) velocidadeY; 
            velocidadeY += 0.5;
            
            estadoAtual = EstadoJogador.PULANDO;
            
            if (newY >= getWorld().getHeight() - getImage().getHeight() / 2) {
                velocidadeY = 0;
                estaPulando = false;
                int landingHeight = getWorld().getHeight() - getImage().getHeight() / 2;
                newY = landingHeight - 15;
            }
            setLocation(getX(), newY); 
        }
    }
    
    // Configura o tamanho da animação da fumaça
    public void efeitoFumaca(int escala) {
        Efeito fumaca = new EfeitoFumaca();
        
        GreenfootImage[] animFumaca;
        animFumaca = fumaca.gerarAnimacao("Efeitos/Fumaca/fumaca", 8, escala);
        fumaca.setAnimacaoAtual(animFumaca);
        
        getWorld().addObject(fumaca, getX(), getY()+15);
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
                Projetil projetil;
                int alcanceDoTiro = 900;
                
                if (!movendo_Esquerda) {
                    projetil = new Projetil(20, alcanceDoTiro, danoTiro, animProjetil);
                    getWorld().addObject(projetil, getX()+30, getY()+30);
                    estadoAtual = EstadoJogador.ATIRANDO_DIR;
                } else {
                    projetil = new Projetil(-20, alcanceDoTiro, danoTiro, animProjetil);
                    getWorld().addObject(projetil, getX()-30, getY()+30);
                    estadoAtual = EstadoJogador.ATIRANDO_ESQ;
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
            
            estadoAtual = EstadoJogador.DANO;
            
            if (vida < 0 ) {
                vida = 0;
            }
            
            tornarImune();
            receberAtaque = true;
            coracao.atualizarVida(vida);
        }
    }
    
    /*
     * Verifica estado do personagem
     */
    
        // Define personagem como Vivo
    public void viver() {
        estaVivo = true;
    }
    
        // Define o Jogador como Morto
    public void morrer() {
        estaVivo = false;
    }
    
        // Retorna verdadeiro se o Jogador estiver vivo
    public boolean estaVivo() {
        return vida > 0;
    }
    
        // Remove o personagem e suas referências do mundo após morrer
    public void tirarDoMundo(){
        somRemover.play();
        estadoAtual = EstadoJogador.DANO;
        efeitoFumaca(8);
        
        getWorld().removeObject(barraRecarga);
        getWorld().removeObject(this);
        morrer();
    }
    
        // Revive o Jogador
    public void redefinirVida() {
        vida = 10;
        coracao.atualizarVida(vida);
        viver();
    }
    
    /*
     * Torna o jogador imune após levar dano
     */
    public void tornarImune() {
        estaImune = true;
        tempoImunidade = 180;
    }
    
        // O Jogador fica imune a ataques até que o tempo chegue a 0
    public void gerenciarImunidade() {
        if (estaImune) {
            estadoAtual = EstadoJogador.DANO;
            
            tempoImunidade--;
            if (tempoImunidade <= 0) {
                estaImune = false;
            }
        }
    }
    
    /*
     * Máquina de estado do Jogador
     */
    
    public void animJogador() {
        switch (estadoAtual) {
            case PARADO_DIR: 
                estadoParadoDir();
                break;
                
            case PARADO_ESQ:
                estadoParadoEsq();
                break;
                
            case ANDANDO_DIR:
                estadoAndandoDir();
                break;
                
            case ANDANDO_ESQ:
                estadoAndandoEsq();
                break;
                
            case PULANDO:
                estadoPulando();
                break;
                
            case ATIRANDO_DIR:
                estadoAtirandoDir();
                break;
                
            case ATIRANDO_ESQ:
                estadoAtirandoEsq();
                break;
                
            case DANO:
                estadoDano();
                break;
        }
    }
    
        // Animação do Jogador Parado virado para a Direita
    private void estadoParadoDir() {
        super.setAnimacaoAtual(animEstaticoDir);
        super.setTempoEntreFrames(7);
    }
    
        // Animação do Jogador Parado virado para a Esquerda
    private void estadoParadoEsq() {
        super.setAnimacaoAtual(animEstaticoEsq);
        super.setTempoEntreFrames(7);
    }
    
        // Animação do Jogador Andando para a Direita
    private void estadoAndandoDir() {
        super.setAnimacaoAtual(animCorrendoDir);
        super.setTempoEntreFrames(5);
    }
    
        // Animação do Jogador Andando para a Esquerda
    private void estadoAndandoEsq() {
        super.setAnimacaoAtual(animCorrendoEsq);
        super.setTempoEntreFrames(5);
    }
    
        // Animação do Jogador Pulando
    private void estadoPulando() {
        somPulo.play();
        super.setAnimacaoAtual(animPulando);
    }
    
        // Animação do Jogador Atirando para a Direita
    private void estadoAtirandoDir() {
        tocarSomDisparo();
        super.setAnimacaoAtual(animAtirarDir);
        super.setTempoEntreFrames(2);
    }
    
        // Animação do Jogador Atirando para a Esquerda
    private void estadoAtirandoEsq() {
        tocarSomDisparo();
        super.setAnimacaoAtual(animAtirarEsq);
        super.setTempoEntreFrames(2);
    }
    
        // Animação do Jogador levando Dano
    private void estadoDano() {
        somDanoGlitch.play();
        if (!estaImune) somDanoGlitch.stop();
        super.setAnimacaoAtual(animDano);
        super.setTempoEntreFrames(5);
    }
}
