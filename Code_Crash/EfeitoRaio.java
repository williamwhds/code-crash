import greenfoot.*;  

public class EfeitoRaio extends Efeito
{
    private GreenfootImage[] raio;
    GreenfootSound somEletricidade = new GreenfootSound("Eletricidade.mp3");
    
    public EfeitoRaio() {
        raio = super.gerarAnimacao("Efeitos/Raio/raio", 4);
        setAnimacaoAtual(raio);
    }
    
    public void act()
    {
        super.animar();
        somEletricidade.play();
        if (animacaoTerminou()) remover();;
    }
    
    // Remove o objeto e desliga o efeito sonoro
    public void remover() {
        super.remover();
        somEletricidade.stop();
    }
}

