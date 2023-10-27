import greenfoot.*;

public class Coracao extends ObjetoAnimado
{
    private String caminhoAnim;
    private GreenfootImage[] animCoracao;
    private int vidaAtual = 10;
    
    public void act()
    {
        super.animar();
    }
    
    public void atualizarVida(World CodeCrash, int dano) {
        int valorVida = vidaAtual - dano;
        
        caminhoAnim = ("coracao"+valorVida+"_");
        animCoracao = super.gerarAnimacao(caminhoAnim, 7);
        
        super.setAnimacaoAtual(animCoracao);
        definirVida(vidaAtual-dano);
    }
    
    public void definirVida(int vida) {
        this.vidaAtual = vida;
    }
    
}
