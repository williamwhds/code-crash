import greenfoot.*;

public class EfeitoExplosao extends Efeito
{
    GreenfootImage[] explosao;
    
    public EfeitoExplosao () {
        explosao = super.gerarAnimacao("Efeitos/Explosao/explosao", 5, 2);
        setAnimacaoAtual(explosao);
    }
    
    public void act()
    {
        super.animar();
        if (animacaoTerminou()) remover();
    }
    /*
    public void remover () {
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
    }
    
    public void setAnimacaoAtual (GreenfootImage[] anim) {
        super.setAnimacaoAtual(anim);
    } 
    
    public boolean animacaoTerminou () {
        return super.animacaoTerminou();
    }
    */
}
