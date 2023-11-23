import greenfoot.*;

public class Inimigo0 extends Inimigo
{   
    private static int vida = 30;
    private static int forca = 1;
    private static int velocidadeX = 5;
    
    public Inimigo0() {
        super(vida, forca);
        
        definirVelocidade(velocidadeX);
        
        animParadoEsq = super.gerarAnimacao("Inimigos/Inimigo0/inimigo0_", 15);
        animParadoDir = super.espelharAnimacao(animParadoEsq);
    }
    
    public void act() {
        super.act();
    }
}
