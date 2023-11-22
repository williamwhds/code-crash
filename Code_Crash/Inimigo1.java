import greenfoot.*;

public class Inimigo1 extends Inimigo
{   
    private static int vida = 20;
    private static int forca = 1;
    private static int velocidadeX = 2;
    
    public Inimigo1() {
        super(vida, forca);
        
        definirVelocidade(velocidadeX);
    }
    
    public void act() {
        super.act();
       
    }
}
