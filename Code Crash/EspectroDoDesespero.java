import greenfoot.*; 

public class EspectroDoDesespero extends Inimigo
{
    private int velocidadeX = 1;
    
    public EspectroDoDesespero() {
        super(5, 2);
        
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
