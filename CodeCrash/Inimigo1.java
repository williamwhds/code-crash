import greenfoot.*;

public class Inimigo1 extends Inimigo
{   
    private double velocidadeY = 0;
    //private int vida = 20;
    private BarraFlex barraVida;
    
    public Inimigo1(BarraFlex barraVida) {
        super(20, 1, 3, barraVida);
        //adicionarBarraVida(barraVida);
        //barraVida = new BarraFlex();
        //BarraFlex barraVida = new BarraFlex(20, 20, 100, 10, Color.RED);
        
        //getWorld().addObject(barraVida, 100, 500);
        //barraVida.atualizarBarra();
        //barraVida.setLocation(100, 100);
        
        
        //adicionarBarraVida(barraVida);
    }
    
    
    /*
    public void inicializar() {
        if (!removidoDoMundo) {
            barraVida = new BarraFlex(20, 20, 100, 10, Color.RED);
            //getWorld().addObject(barraVida, getX(), getY()-70);
            barraVida.atualizarBarra();
        }
    }
    */
   

    public void act() {
       if (!removidoDoMundo) {
           super.act();
       }
        
       if (removidoDoMundo){
           //getWorld().removeObject(barraVida);
           getWorld().removeObject(this);
           //barraVida.getWorld().removeObject(this);
       }
    }
}
