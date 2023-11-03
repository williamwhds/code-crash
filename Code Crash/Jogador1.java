import greenfoot.*;

public class Jogador1 extends Jogador
{      
    private int alturaCoracao = 70;
    Color corBarra = Color.BLUE;
    
    public Jogador1() { 
        coracao = new Coracao("p1");
        barraRecarga = new BarraFlex(largura, altura, municao, municao, corBarra);
        
        // Controles
        super.configTeclaEsquerda("A");
        super.configTeclaDireita("D");
        super.configTeclaPular("W");
        super.configTeclaAtirar("V");
    }
    
    public void addedToWorld(World world) {
        super.addedToWorld(world, alturaCoracao);
    }
    
     public void act() {
        super.act();
    }
}
