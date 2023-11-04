import greenfoot.*;

public class Jogador2 extends Jogador
{      
    Color corBarra = Color.WHITE;
    
    public Jogador2() {
        coracao = new Coracao("p2");
        barraRecarga = new BarraFlex(largura, altura, municao, municao, corBarra);
        
        // Animação 
        animCorrendoDir = super.gerarAnimacao("Jogadores/j2/j2_andando", 9);
        animCorrendoEsq = super.espelharAnimacao(animCorrendoDir);
        
        animEstaticoDir = super.gerarAnimacao("Jogadores/j2/j2_parado", 4);
        animEstaticoEsq = super.espelharAnimacao(animEstaticoDir);
        
        animAtirarDir = super.gerarAnimacao("Jogadores/j2/j2_atirar", 2, 1);
        animAtirarEsq = super.espelharAnimacao(animAtirarDir);
        
        animDano = super.gerarAnimacao("Jogadores/j2/j2_dano", 2);
        animPulando = super.gerarAnimacao("Jogadores/j2/j2_pulando", 6, 1);
        
        // Controles
        super.configTeclaEsquerda("left");
        super.configTeclaDireita("right");
        super.configTeclaPular("up");
        super.configTeclaAtirar("O");
    }
    
    public void addedToWorld(World world) {
        super.addedToWorld(world, world.getWidth()-100);
    }
    
     public void act() {
        super.act();
    }
}
