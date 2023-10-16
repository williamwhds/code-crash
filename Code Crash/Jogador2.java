import greenfoot.*;

public class Jogador2 extends Jogador
{      
    public Jogador2(Coracao coracao) {
        super(coracao);
        
        configurarTeclas("left", "right", "up", "P", "L");
        imagemJogadorDireita();
        imagemJogadorEsquerda();
    }

    public void imagemJogadorDireita() {
        definirImgJogadorDireita("Player1Estatico_Frente.gif", "Player1Correndo_Frente.gif");
    }
    
    public void imagemJogadorEsquerda() {
        definirImgJogadorEsquerda("Player1Estatico_Tras.gif", "Player1Correndo_Tras.gif");
    }
    
     public void act() {
        super.act();
        
        if (!verificaVida()) {
            getWorld().removeObject(this);
        }
    }
}
