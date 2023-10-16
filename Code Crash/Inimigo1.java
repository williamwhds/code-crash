import greenfoot.*;

public class Inimigo1 extends Inimigo
{   
    private int velocidadeX = 2;
    
    public Inimigo1() {
        super(20, 1);
        
        definirVelocidade(velocidadeX);
    }
    
    public void act() {
       if (!removidoDoMundo) {
           super.act();
       }
        
       if (removidoDoMundo){
           getWorld().removeObject(this);
       }
    }
}
