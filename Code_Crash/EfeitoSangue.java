import greenfoot.*;  

public class EfeitoSangue extends Efeito
{
    GreenfootImage[] sangue;
    public EfeitoSangue() {
        sangue = super.gerarAnimacao("Efeitos/Sangue/sangue", 6, 2);
        setAnimacaoAtual(sangue);
    }
    
    public void act()
    {
        super.animar();
        if (animacaoTerminou()) remover();
    }
    
}
