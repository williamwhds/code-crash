import greenfoot.*;

public class Coracao extends _Componentes
{
    //private GreenfootImage[] images = new GreenfootImage[11];
    private String caminhoGif;
    
    GifImage gifVidaCompleta;
    GifImage gifCoracao;
    
    private int vidaAtual = 10;
    
    public Coracao() {
        gifVidaCompleta = new GifImage("Coracao10.gif");
    }
    
    public void act()
    {
        
    }
    
    public void atualizarVida(World CodeCrash, int dano) {
        int valorVida = vidaAtual - dano;
        
        caminhoGif = ("Coracao"+valorVida+".gif");
        gifCoracao = new GifImage(caminhoGif);
        
        setImage(gifCoracao.getCurrentImage());
        definirVida(vidaAtual-dano);
    }
    
    public void definirVida(int vida) {
        this.vidaAtual = vida;
    }
    
}
