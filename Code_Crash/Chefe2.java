/*
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Chefe2 extends Chefe
{
    
    private static int vida = 1000;
    private static int forcaDano = 2;
    private static int velocidadeX = 10;
    private int qntInimigosADerrotar = 4;
    private static int tempo = 600;
    private int chancesInvocarInimigos = 6;
    
    public Chefe2() {
        super(vida, forcaDano, tempo);
        
        definirVelocidade(velocidadeX);
        //definirTempoDeEspera(tempo);
        controlarInvocacao(qntInimigosADerrotar, chancesInvocarInimigos);
    }
    
    public void act()
    {
        super.act();
    }
    
    public void invocarInimigo() {
        Inimigo espectro = new EspectroDoDesespero();
        getWorld().addObject(espectro, getX(), getY());
    }
}
*/