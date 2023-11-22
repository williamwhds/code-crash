import greenfoot.*;

public class Jogador1 extends Jogador
{      
    Color corBarra = Color.BLUE;
    
    public Jogador1() { 
        coracao = new Coracao("p1");
        barraRecarga = new BarraFlex(largura, altura, municao, municao, corBarra);
        
        // Animação
        animCorrendoDir = super.gerarAnimacao("Jogadores/j1/j1_andando", 9);
        animCorrendoEsq = super.espelharAnimacao(animCorrendoDir);
        
        animEstaticoDir = super.gerarAnimacao("Jogadores/j1/j1_parado", 4);
        animEstaticoEsq = super.espelharAnimacao(animEstaticoDir);
        
        animProjetilDir = super.gerarAnimacao("Jogadores/j1/j1_poder", 4, 2);
        animProjetilEsq = super.espelharAnimacao(animProjetilDir);
        
        animAtirarDir = super.gerarAnimacao("Jogadores/j1/j1_atirar", 2);
        animAtirarEsq = super.espelharAnimacao(animAtirarDir);
        
        animDano = super.gerarAnimacao("Jogadores/j1/j1_dano", 2);
        animPulando = super.gerarAnimacao("Jogadores/j1/j1_pulando", 6);
        
        // Controles
        super.configTeclaEsquerda("A");
        super.configTeclaDireita("D");
        super.configTeclaPular("W");
        super.configTeclaAtirar("V");
    }
    
    public void addedToWorld(World world) {
        super.addedToWorld(world, 100);
    }
    
     public void act() {
        super.act();
    }
}
