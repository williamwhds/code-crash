import greenfoot.*;
import java.util.List;

public class Chefe2 extends Chefe {
    
    private static int vida = 500;
    private static int forca = 2;
    
    boolean estaParado = true;
    
    private int distanciaBordaX = 100;
    private boolean ladoEsquerdo = false;
    
    private int valorTempo = 400;
    private int tempoDeEspera = valorTempo;
    private int velocidadeX = 5;
    
    public Chefe2() {
        super(vida, forca);
        
        String locImgChefe = "Chefes/Chefe2/chefe2_parado_";
        String locImgChefeCorrendo = "Chefes/Chefe2/chefe2_correndo_";
        
        animParadoEsq = super.gerarAnimacao(locImgChefe, 9);
        animParadoDir = super.espelharAnimacao(animParadoEsq);
        
        animAtacandoEsq = super.gerarAnimacao(locImgChefeCorrendo, 3);
        animAtacandoDir = super.espelharAnimacao(animAtacandoEsq);
        // animAtacandoEsq = super.gerarAnimacao(locImgChefe, 6);
        super.estadoChefeAtual = EstadoChefe.PARADO_ESQ;
    }
    
    public void act() {
            
        super.animar();
        super.animChefe();
        super.verificarColisoesComJogadores();
        
        super.gravidade();
        
        if (estaParado) {
            iniciarAtaque();
        } else {
            mover();
        }
    }
    
    public void mover() {
        if (ladoEsquerdo) {
            moverParaDireita();
        }
        
        if (!ladoEsquerdo) {
            moverParaEsquerda();
        }
    }
    
    public void iniciarAtaque() {
        if (tempoDeEspera > 0) {
            tempoDeEspera--;
            if (!estaParado) estaParado = true;
        } else {
            if(estaParado) estaParado = false;
        }
        
        hordaInimigo(new EspectroDoDesespero(), 1);
    }
    
    public void hordaInimigo(Inimigo inimigo, int totalAInvocar) {
        List<Inimigo> fantasma = getWorld().getObjects(Inimigo.class);
        
        if ((tempoDeEspera%60 == 0) && (totalAInvocar > fantasma.size())) {
            getWorld().addObject(inimigo, getX(), getY());
        }
    }
    
    public void moverParaEsquerda() {
        if (getX() > distanciaBordaX) {
            super.estadoChefeAtual = EstadoChefe.ATACANDO_ESQ;
            move(-velocidadeX);
        } else {
            super.estadoChefeAtual = EstadoChefe.PARADO_DIR;
            ladoEsquerdo = true;
            tempoDeEspera = valorTempo;
            estaParado = true;
        }
    }
    
    public void moverParaDireita() {
        if (getX() < (getWorld().getWidth() - distanciaBordaX)) {
            super.estadoChefeAtual = EstadoChefe.ATACANDO_DIR;
            move(velocidadeX);
        } else {
            super.estadoChefeAtual = EstadoChefe.PARADO_ESQ;
            ladoEsquerdo = false;
            tempoDeEspera = valorTempo;
            estaParado = true;
        }
    } 
    
    public void definirVelocidade(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }
}

/*
public class Chefe2 extends Chefe
{
    
    private static int vida = 1000;
    private static int forcaDano = 2;
    private static int velocidadeX = 10;
    private int qntInimigosADerrotar = 4;
    private static int tempo = 600;
    private int chancesInvocarInimigos = 6;
    
    public Chefe2() {
        super(vida, forcaDano, tempo);
        
        definirVelocidade(velocidadeX);
        //definirTempoDeEspera(tempo);
        controlarInvocacao(qntInimigosADerrotar, chancesInvocarInimigos);
    }
    
    public void act()
    {
        super.act();
    }
    
    public void invocarInimigo() {
        Inimigo espectro = new EspectroDoDesespero();
        getWorld().addObject(espectro, getX(), getY());
    }
}
*/