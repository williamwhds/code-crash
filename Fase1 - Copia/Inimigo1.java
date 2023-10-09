import greenfoot.*;

public class Inimigo1 extends InimigosDinamicos
{   
    private double velocidadeY = 0;
    
    public Inimigo1() {
        super(20, 1, 3);
    }

    public void act() {
        /*
        if (!removidoDoMundo) {
            super.act();
            if (!estaNoChao()) {
                velocidadeY += 0.5;
            } else {
                velocidadeY = 0;
            }
            setLocation(getX(), getY() + (int) velocidadeY);
        }
        */
       if (!removidoDoMundo) {
           super.act();
       }
        
       if (removidoDoMundo){
           getWorld().removeObject(this);
       }
    }
}
