import greenfoot.*;
import java.util.List;

public class Chefe1 extends Chefe {
    
    private static int vida = 500;
    private static int forca = 2;
    
    boolean estaParado = true;
    
    private int distanciaBordaX = 100;
    private boolean ladoEsquerdo = false;
    
    private int valorTempo = 400;
    private int tempoDeEspera = valorTempo;
    private int velocidadeX = 5;
    
    public Chefe1() {
        super(vida, forca);
        
        String locImgChefe = "Chefes/Chefe1/chefe1_";
        
        animParadoEsq = super.gerarAnimacao(locImgChefe, 4);
        animParadoDir = super.espelharAnimacao(animParadoEsq);
        // animAtacandoEsq = super.gerarAnimacao(locImgChefe, 6);
        super.estadoChefeAtual = EstadoChefe.PARADO_ESQ;
    }
    
    public void act() {
            
        super.animar();
        super.animChefe();
        super.verificarColisoesComJogadores();
        
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
        
        hordaInimigo(new DroneMaluco(), 1);
    }
    
    public void hordaInimigo(Inimigo inimigo, int totalAInvocar) {
        List<Inimigo> droneNoMundo = getWorld().getObjects(Inimigo.class);
        
        if ((tempoDeEspera%60 == 0) && (totalAInvocar > droneNoMundo.size())) {
            getWorld().addObject(inimigo, getX(), getY());
        }
    }
    
    public void moverParaEsquerda() {
        if (getX() > distanciaBordaX) {
            move(-velocidadeX);
        } else {
            super.estadoChefeAtual = EstadoChefe.PARADO_DIR;
            ladoEsquerdo = true;
            tempoDeEspera = valorTempo;
            estaParado = true;
        }
    }
    
    public void moverParaDireita() {
        if (getX() < (getWorld().getWidth()) - distanciaBordaX) {
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
public class RabulGenius extends Chefe {
    
    private static int vida = 500;
    private static int forcaDano = 2;
    private static int tempo = 600;
    
    private int velocidadeX = 10;
    private int qntInimigosADerrotar = 2;
    private int chancesInvocarInimigos = 4;
    
    public RabulGenius() {
        super(vida, forcaDano, tempo);
        
        definirVelocidade(velocidadeX);
        controlarInvocacao(qntInimigosADerrotar, chancesInvocarInimigos);
    }
    
    public void act() {
        super.act();
    }
    
    public void invocarInimigo() {
        Inimigo drone = new DroneMaluco();
        getWorld().addObject(drone, getX(), getY());
    }
}
*/