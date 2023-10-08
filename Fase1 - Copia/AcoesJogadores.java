import greenfoot.*;

public class AcoesJogadores extends Actor
{
    private String chaveMoverEsquerda;
    private String chaveMoverDireita;
    private String chavePular;
    private String chaveAtirar;
    
    private double velocidadeX = 0;
    private double velocidadeY = 0;
    private double forcaSalto = 13;
    
    private int municao = 15;
    private int tempoRecargaAtual = 180;
    
    private boolean estaPulando = false;
    private boolean travarTecla = false;
    private boolean travarTeclaAtirar = false;
    private boolean movendo_Esquerda = false;
    private boolean recarregando = false;
    
    private String chaveJogadorParado_Direita;
    private String chaveJogadorParado_Esquerda;
    private String chaveJogadorCorrendo_Direita;
    private String chaveJogadorCorrendo_Esquerda;
    
    GifImage gifJogadorParado_Direita;
    GifImage gifJogadorParado_Esquerda;
    GifImage gifJogadorCorrendo_Direita;
    GifImage gifJogadorCorrendo_Esquerda;
    
    // Define teclas do jogador
    public AcoesJogadores(String chaveMoverEsquerda, String chaveMoverDireita, 
                          String chavePular, String chaveAtirar,
                          String chaveParadoDireita, String chaveParadoEsquerda, 
                          String chaveCorrendoDireita, String chaveCorrendoEsquerda) {
        
        this.chaveMoverEsquerda = chaveMoverEsquerda;
        this.chaveMoverDireita = chaveMoverDireita;
        this.chavePular = chavePular;
        this.chaveAtirar = chaveAtirar;
        
        this.chaveJogadorParado_Direita = chaveParadoDireita;
        this.chaveJogadorParado_Esquerda = chaveParadoEsquerda;
        this.chaveJogadorCorrendo_Direita = chaveCorrendoDireita;
        this.chaveJogadorCorrendo_Esquerda = chaveCorrendoEsquerda;
        
        this.gifJogadorParado_Direita = new GifImage(chaveParadoDireita);
        this.gifJogadorParado_Esquerda = new GifImage(chaveParadoEsquerda);
        this.gifJogadorCorrendo_Direita = new GifImage(chaveCorrendoDireita);
        this.gifJogadorCorrendo_Esquerda = new GifImage(chaveCorrendoEsquerda);
        
    }

    public void act() {
        tempoRecarga();
        movimentos();
        pulos();
        gravidade();
        controlarTiros();
    }
    
    private void movimentos() {
        if (Greenfoot.isKeyDown(chaveMoverEsquerda)) {
            setImage(gifJogadorCorrendo_Esquerda.getCurrentImage());
            velocidadeX = -5;
            movendo_Esquerda = true;
        } else if (Greenfoot.isKeyDown(chaveMoverDireita)) {
            setImage(gifJogadorCorrendo_Direita.getCurrentImage());
            velocidadeX = 5;
            movendo_Esquerda = false;
            
        } else {
            if (movendo_Esquerda) {
                setImage(gifJogadorParado_Esquerda.getCurrentImage());
            } else {
                setImage(gifJogadorParado_Direita.getCurrentImage());
            }
            velocidadeX = 0;
        }
        setLocation(getX() + (int) velocidadeX, getY());
    }
    
    private void pulos() {
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
    
    protected void gravidade() {
        if (!estaPulando && getY() < getWorld().getHeight() - getImage().getHeight() / 2) {
            velocidadeY += 0.5;
            setLocation(getX(), getY() + (int) velocidadeY);
            if (getY() >= getWorld().getHeight() - getImage().getHeight() / 2) {
                velocidadeY = 0;
                setLocation(getX(), getWorld().getHeight() - getImage().getHeight() / 2);
            }
        }
    }
    
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
                    projetil = new Projetil(20, alcanceDoTiro);
                    getWorld().addObject(projetil, getX()+30, getY()+30);
                } else {
                    projetil = new Projetil(-20, alcanceDoTiro);
                    getWorld().addObject(projetil, getX()-30, getY()+30);
                }
        
                projetil.setRotation(getRotation());
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
    }
    
    public void recarregando() {
        municao = 15;
        recarregando = false;
        tempoRecargaAtual = 180;
    }
}
