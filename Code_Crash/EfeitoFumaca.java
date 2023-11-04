import greenfoot.*; 

public class EfeitoFumaca extends Efeito
{
    private GreenfootImage[] fumaca;
    
    public EfeitoFumaca() {
        fumaca = super.gerarAnimacao("Efeitos/Fumaca/fumaca", 8, 2);
        setAnimacaoAtual(fumaca);
    }

    public void act()
    {
        super.animar();
        if (animacaoTerminou()) remover();
    }
    
}
