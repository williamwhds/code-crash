import greenfoot.*;

public class EfeitoClonagem extends Efeito
{
    GreenfootImage[] animClone;
    
    public EfeitoClonagem(GreenfootImage[] animClone ) {
        this.animClone = animClone;
        animarClone();
    }
    
    public void act()
    {
        super.animar();
    }
    
    public void animarClone() {
        super.setAnimacaoAtual(animClone);
    }
}
