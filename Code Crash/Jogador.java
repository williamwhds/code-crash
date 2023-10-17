import greenfoot.*;

public class Jogador extends Actor
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
     * Armazena o caminho dos gifs
     */
    private String caminhoJogadorParado_Direita;
    private String caminhoJogadorParado_Esquerda;
    private String caminhoJogadorCorrendo_Direita;
    private String caminhoJogadorCorrendo_Esquerda;
    
    /*
     * Declara o tipo dos atributos gif
     */
    GifImage gifJogadorParado_Direita;
    GifImage gifJogadorParado_Esquerda;
    GifImage gifJogadorCorrendo_Direita;
    GifImage gifJogadorCorrendo_Esquerda;
    
    /*
     * Outro
     */
    private Plataforma plataforma;
    
    GreenfootSound somDisparo = new GreenfootSound("Disparo.mp3");
    
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
    }
    
    /*
     * Define a imagem do personagem
     */
    public void definirImgJogadorDireita(String chaveParadoDireita, String chaveCorrendoDireita) {
        this.caminhoJogadorParado_Direita = chaveParadoDireita;
        this.caminhoJogadorCorrendo_Direita = chaveCorrendoDireita;
        
        gifJogadorParado_Direita = new GifImage(chaveParadoDireita);
        gifJogadorCorrendo_Direita = new GifImage(chaveCorrendoDireita);   
    }
    
    public void definirImgJogadorEsquerda(String chaveParadoEsquerda, String chaveCorrendoEsquerda) {
        this.caminhoJogadorParado_Esquerda = chaveParadoEsquerda;
        this.caminhoJogadorCorrendo_Esquerda = chaveCorrendoEsquerda;
        
        gifJogadorParado_Esquerda = new GifImage(chaveParadoEsquerda);
        gifJogadorCorrendo_Esquerda = new GifImage(chaveCorrendoEsquerda); 
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
        setImage(gifJogadorCorrendo_Esquerda.getCurrentImage());
        velocidadeX = -5;
        movendo_Esquerda = true;
    }
    
    public void moverDireita() {
        setImage(gifJogadorCorrendo_Direita.getCurrentImage());
        velocidadeX = 5;
        movendo_Esquerda = false;
    }
    
    public void parado() {
        if (movendo_Esquerda) {
                setImage(gifJogadorParado_Esquerda.getCurrentImage());
            } else {
                setImage(gifJogadorParado_Direita.getCurrentImage());
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
                    projetil = new Projetil(20, alcanceDoTiro, danoTiro);
                    getWorld().addObject(projetil, getX()+30, getY()+30);
                } else {
                    projetil = new Projetil(-20, alcanceDoTiro, danoTiro);
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
