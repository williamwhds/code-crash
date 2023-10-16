import greenfoot.*;

public class Projetil extends Actor {
    private double velocidade;
    private int distanciaPercorrida;
    private int distanciaMaxima;
    private int dano;
    
    GifImage gifPoder = new GifImage("Player1Poder.gif");

    public Projetil(double velocidade, int distanciaMaxima, int dano) {
        this.velocidade = velocidade;
        this.distanciaMaxima = distanciaMaxima;
        this.dano = dano;
        setImage(gifPoder.getCurrentImage());
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
                    chefe.dano(dano);
                    removerDoMundo();
                }
            }
        } else {
            removerDoMundo();
        }
    }
    
    public void removerDoMundo() {
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
    }
}
