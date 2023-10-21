import greenfoot.*;

public class Jogador2 extends Jogador
{      
    public Jogador2(Coracao coracao) {
        super(coracao);
        
        configurarTeclas("left", "right", "up", "O", "L", "P");
    }
    
     public void act() {
        super.act();
        
        if (!verificaVida()) {
            getWorld().removeObject(this);
        }
    }
}
