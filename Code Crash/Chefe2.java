import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Chefe2 extends Chefe
{
    
    private static int vida = 1000;
    private static int forcaDano = 2;
    private int velocidadeX = 10;
    private int qntInimigosADerrotar = 2;
    private int tempo = 600;
    private int chancesInvocarInimigos = 4;
    
    public Chefe2() {
        super(vida, forcaDano);
        
        definirVelocidade(velocidadeX);
        definirTempoDeEspera(tempo);
        controlarInvocacao(qntInimigosADerrotar, chancesInvocarInimigos);
    }
    
    public void act()
    {
        super.act();
    }
}
