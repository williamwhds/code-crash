import greenfoot.*;

public class BarraFlex extends _Componentes
{
    private int vidaMaxima, vidaAtual;
    private int larguraMaxima, altura;
    private Color cor;
    
    //public BarraFlex() {
    //    atualizarBarra();
    //}
    
    public BarraFlex(int vidaMaxima, int vidaAtual, int largura, int altura, Color cor) {
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaAtual;
        this.larguraMaxima = largura;
        this.altura = altura;
        this.cor = cor;
        atualizarBarra();
    }
    
    public void act()
    {
        
    }
    
    public void diminuirVida(int dano) {
        if (vidaAtual > 0) {
            vidaAtual -= dano;
            atualizarBarra();
            if (vidaAtual < 0) {
                vidaAtual = 0;
            }
        }
    }
    
    public void aumentarVida(int cura) {
        if (vidaAtual<vidaMaxima) {
            vidaAtual += cura;
            atualizarBarra();
            
            if (vidaAtual > vidaMaxima) {
                vidaAtual = vidaMaxima;
            }
        }
    }
    
    public void atualizarBarra() {
        if (vidaAtual != 0) {
            int larguraAtual = (int)((double)vidaAtual / vidaMaxima * larguraMaxima); 
            
            GreenfootImage imagem = new GreenfootImage(larguraAtual, altura);
            imagem.setColor(Color.RED);
            imagem.fill();
            setImage(imagem);
        }
    }
    
    public int pegarVida() {
        return vidaAtual;
    }
}