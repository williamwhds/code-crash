import greenfoot.*;

public class Jogador2 extends Jogador
{      
    private int alturaCoracao = 110;
    Color corBarra = Color.WHITE;
    
    public Jogador2() {
        coracao = new Coracao("p1");
        barraRecarga = new BarraFlex(largura, altura, municao, municao, corBarra);
        
        // Controles
        super.configTeclaEsquerda("left");
        super.configTeclaDireita("right");
        super.configTeclaPular("up");
        super.configTeclaAtirar("O");
    }
    
    public void addedToWorld(World world) {
        super.addedToWorld(world, alturaCoracao);
    }
    
     public void act() {
        super.act();
    }
}
