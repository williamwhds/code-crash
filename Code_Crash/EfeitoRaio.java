import greenfoot.*;  
import java.util.List;

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
        danoAoJogador();
        
        super.animar();
        somEletricidade.play();
        if (animacaoTerminou()) remover();
    }
    
    public void danoAoJogador() {
        //List<Jogador1> jogador1 = getWorld().getObjects(Jogador1.class);
        //List<Jogador2> jogador2 = getWorld().getObjects(Jogador2.class);
        
        //Jogador jogador = (Jogador) getOneIntersectingObject(Jogador.class);
        
        Jogador jogador1 = (Jogador) getOneIntersectingObject(Jogador1.class);
        Jogador jogador2 = (Jogador) getOneIntersectingObject(Jogador2.class);
        
        if (jogador1 != null && getWorld() != null) {
            jogador1.receberAtaque(2);
        }
        
        if (jogador2 != null && getWorld() != null) {
            jogador2.receberAtaque(2);
        }
    }
    
    // Remove o objeto e desliga o efeito sonoro
    public void remover() {
        super.remover();
        somEletricidade.stop();
    }
}

