import greenfoot.*;

public class Jogador extends Actor
{
    /*
     * Teclas chaves dos movimentos do personagem
     */
    private String chaveMoverEsquerda;
    private String chaveMoverDireita;
    private String chavePular;
    private String chaveAtirar;
    private String chaveRegarregar;
    
    /*
     * Configuração das mecânicas
     */
    private double velocidadeX = 0;
    private double velocidadeY = 0;
    private double forcaSalto = 13;
    
    private int municao = 15;
    private int danoTiro = 5;
    private int tempoRecargaAtual = 180;
    
    private boolean estaPulando = false;
    private boolean travarTecla = false;
    private boolean travarTeclaAtirar = false;
    private boolean movendo_Esquerda = false;
    private boolean recarregando = false;
    
    protected boolean receberAtaque = false;
    
    private boolean estaNaPlataforma = false;
    private Plataforma plataforma;
    
    private boolean estaImune = false;
    private int tempoImunidade = 120;
    private int vida = 10;
    
    Coracao coracao;
    
    /*
     * Armazena o caminho dos gifs
     */
    private String chaveJogadorParado_Direita;
    private String chaveJogadorParado_Esquerda;
    private String chaveJogadorCorrendo_Direita;
    private String chaveJogadorCorrendo_Esquerda;
    
    /*
     * Declara o tipo dos atributos gif
     */
    GifImage gifJogadorParado_Direita;
    GifImage gifJogadorParado_Esquerda;
    GifImage gifJogadorCorrendo_Direita;
    GifImage gifJogadorCorrendo_Esquerda;
    
    /*
     * Define teclas do jogador
     */ 
    public Jogador(String chaveMoverEsquerda, String chaveMoverDireita, 
                   String chavePular, String chaveAtirar, String chaveRegarregar, 
                   Coracao coracao) {
        
        this.chaveMoverEsquerda = chaveMoverEsquerda;
        this.chaveMoverDireita = chaveMoverDireita;
        this.chavePular = chavePular;
        this.chaveAtirar = chaveAtirar;
        this.chaveRegarregar = chaveRegarregar;
        
        this.coracao = coracao;
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
        this.chaveJogadorParado_Direita = chaveParadoDireita;
        this.chaveJogadorCorrendo_Direita = chaveCorrendoDireita;
        
        gifJogadorParado_Direita = new GifImage(chaveParadoDireita);
        gifJogadorCorrendo_Direita = new GifImage(chaveCorrendoDireita);   
    }
    
    public void definirImgJogadorEsquerda(String chaveParadoEsquerda, String chaveCorrendoEsquerda) {
        this.chaveJogadorParado_Esquerda = chaveParadoEsquerda;
        this.chaveJogadorCorrendo_Esquerda = chaveCorrendoEsquerda;
        
        gifJogadorParado_Esquerda = new GifImage(chaveParadoEsquerda);
        gifJogadorCorrendo_Esquerda = new GifImage(chaveCorrendoEsquerda); 
    }
    
    /*
     * Controle de movimentos
     */
    public void movimentos() {
        if (Greenfoot.isKeyDown(chaveMoverEsquerda)) {
            moverEsquerda();
        }
        
        if (Greenfoot.isKeyDown(chaveMoverDireita)) {
            moverDireita();
        }
        
        if (!Greenfoot.isKeyDown(chaveMoverEsquerda) && !Greenfoot.isKeyDown(chaveMoverDireita)){
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
        if (Greenfoot.isKeyDown(chavePular) && !estaPulando && !travarTecla) {
            velocidadeY = -forcaSalto;
            estaPulando = true;
            travarTecla = true;
        }
        
        if (!Greenfoot.isKeyDown(chavePular)) {
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
            estaPulando = false; // Impede que o jogador continue pulando enquanto estiver na plataforma
        }
        /*
        Plataforma plataforma = (Plataforma) getOneIntersectingObject(Plataforma.class);
        
        if (plataforma != null) {
            int alturaDoAtor = getImage().getHeight() / 2;
            int alturaDaPlataforma = plataforma.getImage().getHeight() / 2;
            int novaPosicaoY = plataforma.getY() - alturaDaPlataforma - alturaDoAtor;
            
            setLocation(getX(), novaPosicaoY); // Ajuste o valor conforme necessário
        
            // Verifique se há uma colisão com a plataforma
            if (isTouching(Plataforma.class)) {
                estaPulando = false;
                // Se houver uma colisão, ajuste a posição do jogador
                setLocation(getX(), novaPosicaoY); // Ajuste o valor conforme necessário
            }
        }
        */
       
       
        /*
        Plataforma plataforma = (Plataforma) getOneIntersectingObject(Plataforma.class);
            
        int alturaDoAtor = getImage().getHeight() / 2;
        int alturaDaPlataforma = plataforma.getImage().getHeight() / 2;
        int novaPosicaoY = plataforma.getY() - alturaDaPlataforma - alturaDoAtor;
            
        if (isTouching(Plataforma.class)) {
            
            setLocation(getX(), novaPosicaoY);
        }
        */
        
    }
    
    /*
     * Método de controle de tiros
     */
    public void controlarTiros() {
        if (Greenfoot.isKeyDown(chaveAtirar) && !travarTeclaAtirar){
            atirar();
        }
        
        if (!Greenfoot.isKeyDown(chaveAtirar)) {
            travarTeclaAtirar = false;
        }
    }
        
    public void atirar() {
        if (!recarregando) {
            if (municao > 0) {
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
    
    public void tempoRecarga() {
        if (recarregando) {
            if (tempoRecargaAtual > 0) {
                tempoRecargaAtual--;
            }
            
            if (municao == 0 && recarregando && tempoRecargaAtual <= 0) {
                recarregando();
            }
        }
        
        if ((Greenfoot.isKeyDown(chaveRegarregar) && municao<15) && municao!=0) {
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
