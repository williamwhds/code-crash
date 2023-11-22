import greenfoot.*;

public class Coracao extends ObjetoAnimado
{
    private String caminhoAnim;
    private GreenfootImage[] animCoracao;
    private int vidaAtual;
    
    private String nomePastaJogador;
    
    public Coracao(String pasta) {
        nomePastaJogador(pasta);
        atualizarVida(10);
    }
    
    public void act()
    {
        super.animar();
    }
    
    public void nomePastaJogador(String nomePastaJogador) {
        this.nomePastaJogador = nomePastaJogador;
    }
    
    public void atualizarVida(int vidaAtual) {
        caminhoAnim = ("Coracao/"+nomePastaJogador+"/coracao"+vidaAtual+"_");
        animCoracao = super.gerarAnimacao(caminhoAnim, 7);
        super.setAnimacaoAtual(animCoracao);
        definirVida(vidaAtual);
    }
    
    public void definirVida(int vida) {
        this.vidaAtual = vida;
    }
    
}
