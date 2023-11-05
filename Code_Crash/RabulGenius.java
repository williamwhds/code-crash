/*

import greenfoot.*;
import java.util.List;

public class RabulGenius extends Chefe {
    
    private static int vida = 500;
    private static int forcaDano = 2;
    private static int tempo = 600;
    
    private int velocidadeX = 10;
    private int qntInimigosADerrotar = 2;
    private int chancesInvocarInimigos = 4;
    
    public RabulGenius() {
        super(vida, forcaDano, tempo);
        
        definirVelocidade(velocidadeX);
        controlarInvocacao(qntInimigosADerrotar, chancesInvocarInimigos);
    }
    
    public void act() {
        super.act();
    }
    
    public void invocarInimigo() {
        Inimigo drone = new DroneMaluco();
        getWorld().addObject(drone, getX(), getY());
    }
}
*/