import greenfoot.*;

public class GifActor extends Actor
{
    private GifImage gifImage;
    private int tempoGif;

    public GifActor(String nomeArquivoGif, int tempoGif) {
        this.tempoGif = tempoGif;
        
        gifImage = new GifImage(nomeArquivoGif);
        setImage(gifImage.getCurrentImage());
    }

    public void act() {
        if (tempoGif > 0) {
            setImage(gifImage.getCurrentImage());
            tempoGif--;
        } else {
            World world = getWorld();
            world.removeObject(this);
        }
    }
    
    public void definirTempoDoGif(int tempoGif) {
        this.tempoGif = tempoGif;
    }
    
    public int pegarTempoGifAtual() {
        return tempoGif;
    }
}
