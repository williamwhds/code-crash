import greenfoot.*;
import java.util.List;

public class Projetil extends ObjetoAnimado {
    
    private double velocidade;
    private int distanciaPercorrida;
    private int distanciaMaxima;
    private int dano;
    private boolean colidiu;

    private GreenfootImage[] animPoder;

    public Projetil(double velocidade, int distanciaMaxima, int dano) {
        this.velocidade = velocidade;
        this.distanciaMaxima = distanciaMaxima;
        this.dano = dano;
        this.colidiu = false;
    }

    public void animacaoPoder(GreenfootImage[] animacao) {
        this.animPoder = animacao;
        setAnimacaoAtual(animPoder);
    }

    public void act() {
        if (!isAtEdge() && !colidiu) {
            distanciaPercorrida += velocidade;
            if (distanciaPercorrida >= distanciaMaxima) {
                removerDoMundo();
            } else {
                verificarColisao();
                move((int) velocidade);
            }
            super.animar();
        } else {
            removerDoMundo();
        }
    }

    public void verificarColisao() {
        // Verificar colisão com inimigos
        List<Inimigo> inimigos = getIntersectingObjects(Inimigo.class);
        for (Inimigo inimigo : inimigos) {
            inimigo.receberAtaque(dano);
            colidiu = true; // Tirando esse atributo, o projetil atravessa todos os Inimigos
                            // E os mata
        }

        List<Chefe> chefes = getIntersectingObjects(Chefe.class);
        for (Chefe chefe : chefes) {
            chefe.receberAtaque(dano);
            colidiu = true;
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
