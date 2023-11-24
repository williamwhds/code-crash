import greenfoot.*; 

/*
 *  BarraFlex é uma barra que pode ser usada para várias coisas,
 *  como barras de vida, progresso de objetivo, etc. É possível
 *  definir o valor máximo e atual, e a cor da barra.
 */

public class BarraFlex extends Actor {
    private int barraLargura, barraAltura, valorMax, valorAtual;
    
    private Color corDaBarraOriginal;
    private Color corDaBarraTemp;
    
    public BarraFlex(int largura, int altura, int max, int atual, Color cor) {
        barraLargura = largura;
        barraAltura = altura;
        valorMax = max;
        valorAtual = atual;
        corDaBarraOriginal = cor;
        corDaBarraTemp = corDaBarraOriginal;
        atualizar();
    }

    public void atualizar() {
        GreenfootImage img = new GreenfootImage(barraLargura + 2, barraAltura + 2);
        img.setColor(corDaBarraOriginal);
        img.drawRect(0, 0, barraLargura + 1, barraAltura + 1);
        img.setColor(corDaBarraTemp);
        int larguraDaBarra = (int)((double)valorAtual / valorMax * barraLargura);
        img.fillRect(1, 1, larguraDaBarra, barraAltura);
        setImage(img);
    }
    
    public void setValor(int novoValor) {
        if (novoValor < 0) novoValor = 0;
        
        if (novoValor >= valorMax) {
            novoValor = valorMax;  
            corDaBarraTemp = corDaBarraOriginal;
        }
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
    
    public void redefinirValores(int novoValor) {
        this.valorAtual = novoValor;
        this.valorMax = novoValor;
        atualizar();
    }
    
    public void setCor(Color cor) {
        corDaBarraTemp = cor;
    }
}
