import greenfoot.*;

public class Jogador1 extends Jogador
{      
    public Jogador1(Coracao coracao) {
        super(coracao);
        
        configurarTeclas("A", "D", "W", "V", "R", "C");
    }
    
     public void act() {
        super.act();
        
        if (!verificaVida()) {
            getWorld().removeObject(this);
        }
    }
}
