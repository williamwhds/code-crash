import greenfoot.*;

public class Inimigo1 extends Inimigo
{   
    private static int vida = 35;
    private static int forca = 1;
    private static int velocidadeX = 2;
    
    public Inimigo1() {
        super(vida, forca);
        
        definirVelocidade(velocidadeX);
        
        animParadoEsq = super.gerarAnimacao("Inimigos/Inimigo1/inimigo1_", 15);
        animParadoDir = super.espelharAnimacao(animParadoEsq);
        animAtacandoEsq = super.gerarAnimacao("Inimigos/Inimigo1/inimigo1Atacando_", 4);
        animAtacandoDir = super.espelharAnimacao(animAtacandoEsq);
    }
    
    public void act() {
        super.act();
    }
}
