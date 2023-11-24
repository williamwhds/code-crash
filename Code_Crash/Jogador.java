import greenfoot.*;

public class Jogador extends AtorPersonagem
{   
    /*
     * Vida
     */
    Coracao coracao;
    
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
    private double velocidadeX;
    private double velocidadeY;
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
    protected int largura = 100;
    protected int altura = 10;
    protected BarraFlex barraRecarga;
    
    /*
     * Mecânica de verificação
     */
    private boolean estaPulando;
    private boolean movendo_Esquerda;
    private boolean recarregando;
    private boolean atirou;
    
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
    
    protected GreenfootImage[] animProjetilDir;
    protected GreenfootImage[] animProjetilEsq;
    
    /*
     * Configuração da máquina de estado
     */
    private EstadoJogador estadoAtual;
    
    /*
     * Som
     */
    GreenfootSound somDisparo;
    GreenfootSound somRecarregando;
    GreenfootSound somDanoGlitch;
    GreenfootSound somRemover;
    GreenfootSound somPulo;
    
    public Jogador() {
        super.vida = 10;
        
        estaPulando = false;
        movendo_Esquerda = false;
        recarregando = false;
        atirou = false;
        
        estadoAtual = EstadoJogador.PARADO_DIR;
        
        somDisparo = new GreenfootSound("Projetil.mp3");
        somRecarregando = new GreenfootSound("Recarregando.mp3");
        somDanoGlitch = new GreenfootSound("dano_glitch.mp3");
        somRemover = new GreenfootSound("remover.mp3");
        somPulo = new GreenfootSound("Pulo.mp3");
    }
    
    public void act() {   
        
        super.animar();
        animJogador();
        
        if (!modoPacifico) {
            movimentos();
            pulos();
            controlarTiros();  
            moverBarra(); 
        } 
        
        gravidade();
        gerenciarImunidade();
        tempoRecarga();
        
        if (vida==0) {
            tirarDoMundo();
            encerrarSons();
        }
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
                direcaoProjetil();  
                municao--;
                barraRecarga.diminuirValor(1);
                travarTeclaAtirar = true;
            }
        } 
    }
    
        // Configura a direção do tiro
    public void direcaoProjetil() {
        int alcanceDoTiro = 900;
                
        if (!movendo_Esquerda) {
            projetilParaDireita(alcanceDoTiro);
        } else {
            projetilParaEsquerda(alcanceDoTiro);
        }
    }
    
        // Atira para a direita
    public void projetilParaDireita(int alcanceDoTiro) {
        Projetil projetil = new Projetil(20, alcanceDoTiro, danoTiro);
        projetil.animacaoPoder(animProjetilDir);
        getWorld().addObject(projetil, getX()+30, getY()+30);
        estadoAtual = EstadoJogador.ATIRANDO_DIR;
    }
    
        // Atira para a esquerda
    public void projetilParaEsquerda(int alcanceDoTiro) {
        Projetil projetil = new Projetil(-20, alcanceDoTiro, danoTiro);
        projetil.animacaoPoder(animProjetilEsq);
        getWorld().addObject(projetil, getX()-30, getY()+30);
        estadoAtual = EstadoJogador.ATIRANDO_ESQ;
    }
    
        // O jogador não consegue atirar até que o tempo de recarga não for 0
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
    
    public void reiniciarMunicao() {
        recarregando = false;
        tempoRecargaAtual = 180;
        municao = 15;
        barraRecarga.setValor(municao);
    }
    
    /*
     * Receber dano dos inimigos
     */
    public void receberAtaque(int dano) {
        if (!super.estaImune && estaVivo) {
            
            if (vida < 0 ) {
                vida = 0;
            }
            
            super.receberAtaque(dano);
            super.tornarImune(180);
            estadoAtual = EstadoJogador.DANO;
            coracao.atualizarVida(vida);
        }
    }
    
    public void gerenciarImunidade() {
        if (super.estaImune) {
            estadoAtual = EstadoJogador.DANO;
            super.gerenciarImunidade();
        }
    }
    
    /*
     * Estabelece a física de queda do personagem no mundo
     */
    public void gravidade() {
        if (naPlataforma()) {
            estaPulando = false;
        }
        
        if (!naPlataforma() && !estaPulando && getY() < getWorld().getHeight() - getImage().getHeight() / 2) {
            
            velocidadeY += 0.5;
            setLocation(getX(), getY() + (int) velocidadeY);
            
            if (getY() >= getWorld().getHeight() - getImage().getHeight() / 2) {
                velocidadeY = 0;
                setLocation(getX(), getWorld().getHeight() - getImage().getHeight() / 2);
            }
        }
    }
    
    public boolean naPlataforma() {
        if (getOneIntersectingObject(Plataforma.class) != null) {
            return true;
        }
        return false;
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
    
    public void ativarModoPacifico() {
        super.ativarModoPacifico();
    }
    
    public void desativarModoPacifico() {
        super.desativarModoPacifico();
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
    }
}
