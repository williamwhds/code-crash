import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class EfeitoExplosao extends Efeito
{
    GreenfootImage[] explosao;
    
    public void remover () {
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
    }
    
    public void setAnimacaoAtual (GreenfootImage[] anim) {
        super.setAnimacaoAtual(anim);
    } 
    
    public void animar () {
        super.animar();
    }
    
    public boolean animacaoTerminou () {
        return super.animacaoTerminou();
    }
    
    public EfeitoExplosao () {
        explosao = super.gerarAnimacao("explosao", 5, 2);
        setAnimacaoAtual(explosao);
    }
    
    public void act()
    {
        animar();
        if (animacaoTerminou()) remover();
    }
}
