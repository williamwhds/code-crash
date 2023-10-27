import greenfoot.*; 

public class BarraFlex extends Actor {
    private int barraLargura, barraAltura, valorMax, valorAtual;
    private Color corDaBarra;
    
    public BarraFlex(int largura, int altura, int max, int atual, Color cor) {
        barraLargura = largura;
        barraAltura = altura;
        valorMax = max;
        valorAtual = atual;
        corDaBarra = cor;
        atualizar();
    }

    public void atualizar() {
        GreenfootImage img = new GreenfootImage(barraLargura + 2, barraAltura + 2);
        img.setColor(Color.BLACK);
        img.drawRect(0, 0, barraLargura + 1, barraAltura + 1);
        img.setColor(corDaBarra);
        int larguraDaBarra = (int)((double)valorAtual / valorMax * barraLargura);
        img.fillRect(1, 1, larguraDaBarra, barraAltura);
        setImage(img);
    }
    
    public void setValor(int novoValor) {
        if (novoValor < 0) novoValor = 0;
        if (novoValor > valorMax) novoValor = valorMax;
        valorAtual = novoValor;
        atualizar();
    }

    public void aumentarValor(int quantidade) {
        setValor(valorAtual + quantidade);
    }
    
    public void diminuirValor(int quantidade) {
        setValor(valorAtual - quantidade);
    }

    public int getValor() {
        return valorAtual;
    }

    public int getValorMax() {
        return valorMax;
    }
}
