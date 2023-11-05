import greenfoot.*;

public class Projetil extends ObjetoAnimado {
    private double velocidade;
    private int distanciaPercorrida;
    private int distanciaMaxima;
    private int dano;
    
    private GreenfootImage[] animPoder;

    public Projetil(double velocidade, int distanciaMaxima, int dano) {
        this.velocidade = velocidade;
        this.distanciaMaxima = distanciaMaxima;
        this.dano = dano;
    }
    
    public void animacaoPoder(GreenfootImage[] animacao) {
        this.animPoder = animacao;
        setAnimacaoAtual(animPoder);
    }

    public void act() {
        if (!isAtEdge()) {
            distanciaPercorrida += velocidade;
            if (distanciaPercorrida >= distanciaMaxima) {
                removerDoMundo();
            } else {
                Inimigo inimigo = (Inimigo) getOneIntersectingObject(Inimigo.class);
                Chefe chefe = (Chefe) getOneIntersectingObject(Chefe.class);
                move((int) velocidade);
                
                if (inimigo != null && getWorld()!=null) {
                    inimigo.dano(dano);
                    removerDoMundo();
                }
                if (chefe != null && getWorld() != null) {
                    chefe.receberAtaque(dano);
                    removerDoMundo();
                }
            }
            super.animar();
        } else {
            removerDoMundo();
        }
    }
    
    // Remove Projetil do mundo depois de mostrar animação de explosão
    public void removerDoMundo() {
        if (getWorld() != null) {
            efeitoExplosao();
            getWorld().removeObject(this);
        }
    }
    
    // Animação de Explosão
    public void efeitoExplosao() {
        Efeito explosao = new EfeitoExplosao();
        
        GreenfootImage[] animExplosao;
        animExplosao = explosao.gerarAnimacao("Efeitos/Explosao/explosao", 5, 4);
        explosao.setAnimacaoAtual(animExplosao);
        
        getWorld().addObject(explosao, getX(), getY());
    }
}
