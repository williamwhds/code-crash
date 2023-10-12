import greenfoot.*;

public class Jogador1 extends AcoesJogadores
{      
    public Jogador1() {
        super("A", "D", "W", "V", "R");
        imagemPersonagem();
    }
    
    public void imagemPersonagem() {
        definirImagemDoPersonagem("Player1Estatico_Frente.gif", "Player1Estatico_Tras.gif", 
                              "Player1Correndo_Frente.gif", "Player1Correndo_Tras.gif");
    }
    
     public void act() {
        super.act();
        
        if (!verificaVida()) {
            getWorld().removeObject(this);
        }
    }
}
