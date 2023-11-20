import greenfoot.*;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Chefe3 extends Chefe
{
    private static int vidaMax = 100;
    private static int forca = 1;
    private int tempoAtaque = 1000;
    private int aguardarAtaque;
    
    private Efeito raio;
    private int qntRaio;
    private int tempoAntesRaio = 2*60;
    private int intervalo = 100;
    
    private Efeito clone;
    private int qntClone;
    
    private ArrayList<Integer> posY;
    
    public Chefe3() {
        super(vidaMax, forca);
        
        animParadoEsq = super.gerarAnimacao("Chefes/Chefe3/chefe3_calmo", 6);
        animParadoDir = super.espelharAnimacao(animParadoEsq);
        animCloneEsq = super.gerarAnimacao("Chefes/Chefe3/chefe3_clone", 6);
        animCloneDir = super.espelharAnimacao(animCloneEsq);
        animAtacandoEsq = super.gerarAnimacao("Chefes/Chefe3/chefe3_atacando", 3);
        animAtacandoDir = super.espelharAnimacao(animAtacandoEsq);
        
        super.estadoChefeAtual = EstadoChefe.PARADO_ESQ;
        
        super.ficarImune();
        
        posY = new ArrayList<Integer>();
        addPosY();
        Collections.shuffle(posY);
    }
    
    public void addPosY() {
        posY.add(201);
        posY.add(370);
        posY.add(520);
    }
    
    public void act()
    {
        super.animar();
        super.animChefe();
        
        super.verificarColisoesComJogadores();
        
        if (getX() > getWorld().getWidth() - 100) move(-5);
        iniciarAtaque();
    }
    
    public void definirTempoAtaque(int tempoAtaque) {
        this.tempoAtaque = tempoAtaque;
    }
    
    public void iniciarAtaque() {  
        tempoAtaque--;
        tempoAntesRaio--;
        if (tempoAtaque > 0) {
            super.estadoChefeAtual = EstadoChefe.ATACANDO_ESQ;  
            super.tornarImune(tempoAtaque);
            if (tempoAntesRaio <= 0) atacar();
        } else {
            
            if (tempoAtaque == 0) {
                super.tornarVulneravel();
                super.estadoChefeAtual = EstadoChefe.PARADO_ESQ;
                //invocarClone(animCloneDir, pegarLadoEsquerdo());
                tempoAtaque = 1;  
            } 
            
            if (aguardarAtaque == (3*60)) {
                //removerClone();
                aguardarAtaque = 0;
                tempoAtaque = 1000;
                tempoAntesRaio = 2*60;
            }
            aguardarAtaque++;
            //System.out.println("Aguardar Ataque! : " + aguardarAtaque);
        }
    }

    
    public void atacar() {  
        double vidaAtual = super.pegarVida();
        double porcenVida = (vidaAtual / vidaMax) * 100;
        
        int intervaloAlvo = 0;
        int qntRaioAlvo = 0;
        intervalo --;
        
        if (porcenVida >= 80.0) {
            qntRaioAlvo = 1;
            intervaloAlvo = 150;
        } else if (porcenVida >= 50.0) {
            qntRaioAlvo = 1;
            intervaloAlvo = 80;
        } else if (porcenVida >= 30.0) {
            qntRaioAlvo = 2;
            intervaloAlvo = 150;
        } else if (porcenVida > 0) {
            qntRaioAlvo = 2;
            intervaloAlvo = 80;
        }

        if (qntRaio == 0) {
            qntRaio++;
            invocarRaio(qntRaioAlvo);
        }
        
        if (intervalo <= 0) {
            desenvocarRaio(intervaloAlvo);
            //System.out.println("Desenvocado.");
        }
        //System.out.println("intervalo: " + intervalo);
    }
    
    public void invocarRaio(int qntRaio) {
        Collections.shuffle(posY);
        int novoPosY;
        
        for (int i = 0; i < qntRaio; i++) {
            novoPosY = posY.get(i);
            raio = new EfeitoRaio();
            getWorld().addObject(raio, getWorld().getHeight()-100, novoPosY);
        }
    }
    
    public void desenvocarRaio(int intervalo) {
        if (this.intervalo <= 0) {
            removerRaio();
            redefinirIntervalo(intervalo);
            this.qntRaio = 0;
        }
    }
    
    public void redefinirIntervalo(int tempo) {
        intervalo = tempo;
    }
    
    public void removerRaio() {
        raio.remover();
    }
    
    public void invocarClone(GreenfootImage[] animPosicaoAtual, int ladoAtual) {
        if (qntClone == 0) {
            clone = new EfeitoClonagem(animPosicaoAtual);
            getWorld().addObject(clone, ladoAtual, getY());
            qntClone += 1;
        }
    }
    
    public void removerClone() {
        if (getWorld() != null && clone != null) {            
            getWorld().removeObject(clone);
            qntClone = 0;
        }
    }
}
