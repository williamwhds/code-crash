import greenfoot.*;

public class DroneMaluco extends Inimigo
{
    private int velocidadeX = 5;
    
    public DroneMaluco() {
        super(5, 1);
        definirVelocidade(velocidadeX);
    }
    
    public void act()
    {
        if (!removidoDoMundo) {
           super.act();
           gravidade();
       }
        
       if (removidoDoMundo){
           getWorld().removeObject(this);
       }
    }
    
    public void gravidade() {
        
    }
}
