import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Efeito extends ObjetoAnimado
{
    public void act()
    {
        if (animacaoTerminou()) remover();
    }
    
    public void remover () {
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
    }
    /*
    public void setAnimacaoAtual (GreenfootImage[] anim) {
        super.setAnimacaoAtual(anim);
    } 
    
    public void animar () {
        super.animar();
    }
    
    public boolean animacaoTerminou () {
        return super.animacaoTerminou();
    }
    */
}
