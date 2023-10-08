import greenfoot.*;

public class Projetil extends Actor {
    private int velocidade;
    private int distanciaPercorrida;
    private int distanciaMaxima;
    GifImage gifPoder = new GifImage("Player1Poder.gif");

    public Projetil(int velocidade, int distanciaMaxima) {
        this.velocidade = velocidade;
        this.distanciaMaxima = distanciaMaxima;
        setImage(gifPoder.getCurrentImage());
    }

    public void act() {
        move(velocidade);
        distanciaPercorrida += velocidade;

        if (distanciaPercorrida >= distanciaMaxima || isAtEdge()) {
            getWorld().removeObject(this);
        }
    }
}
