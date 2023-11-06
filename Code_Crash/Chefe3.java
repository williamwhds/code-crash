import greenfoot.*;  
import java.util.Collections;
import java.util.ArrayList;

public class Chefe3 extends Chefe
{
    /*
     * Características específicas do Chefe
     */
    static int vidaMax = 100;
    static int forca = 3;
    int tempoAtaque = 1000; // Valor teste
    
    /*
     * Configurar Habilidades
     */
        // Raios
    private Efeito raio;
    private int qntRaio;
    private int intervalo = 100;
        
        // Clone
    private int qntClone;
     
    /*
     * Listas
     */
    private ArrayList<Integer> posY;
    
    // Horda
    Inimigo[] possiveisInimigos = {new Inimigo1(), new EspectroDoDesespero()};
    int[][] possiveisCoordenadas = {{10, 10}, {70, 10}};
    private Horda horda = new Horda(10, 5, possiveisInimigos, possiveisCoordenadas, true);
    
    public Chefe3() {
        super(vidaMax, forca);
        
        // Imagens dos estados para o chefe
        animParadoEsq = super.gerarAnimacao("Chefes/Chefe3/chefe3_calmo", 6);
        animParadoDir = super.espelharAnimacao(animParadoEsq);
        animCloneEsq = super.gerarAnimacao("Chefes/Chefe3/chefe3_clone", 6);
        animCloneDir = super.espelharAnimacao(animCloneEsq);
        animAtacandoEsq = super.gerarAnimacao("Chefes/Chefe3/chefe3_atacando", 3);
        animAtacandoDir = super.espelharAnimacao(animAtacandoEsq);
        
        // Cria uma lista que recebe números para a posição Y
        posY = new ArrayList<Integer>();
        addPosY();
        Collections.shuffle(posY);
        
        getWorld().addObject(horda, 50, 50);
    }
    
        // Adiciona coordenadas na lista para a invocação aleatória dos raios
    public void addPosY() {
        posY.add(201);
        posY.add(370);
        posY.add(520);  
    }
    
    public void act()
    {
        super.animar();
        super.estadoChefeAtual = EstadoChefe.PARADO_ESQ;
        
        iniciarAtaque();
           
        //if (getX() > getWorld().getWidth()-100) move(-5);
    }
    
    public void iniciarAtaque() {  
        tempoAtaque--;
        
        if (tempoAtaque > 0) {
            super.estadoChefeAtual = EstadoChefe.ATACANDO_ESQ;  
            super.tornarImune(tempoAtaque);
            atacar();
        } 
    }
    
    public void definirTempoAtaque(int tempoAtaque) {
        this.tempoAtaque = tempoAtaque;
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
            System.out.println("Desenvocado.");
        }
        //System.out.println("intervalo: " + intervalo);
    }
    
    public void invocarRaio(int qntRaio) {
        Collections.shuffle(posY); // Reembaralhe a lista posY
        int novoPosY;
        
        for (int i = 0; i < qntRaio; i++) {
            novoPosY = posY.get(i);
            
            raio = new EfeitoRaio();
            getWorld().addObject(raio, getWorld().getHeight(), novoPosY);
            System.out.println("Indice: " + i);
            System.out.println("Posição Y: " + novoPosY);
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
    
    /*
     * Invoca um Clone do Chefe no lado oposto a ele
     */
    public void invocarClone(GreenfootImage[] animPosicaoAtual, int ladoAtual) {
        if (qntClone == 0) {
            Efeito clone = new EfeitoClonagem(animPosicaoAtual);
            getWorld().addObject(clone, ladoAtual, getY());
            qntClone+=1;
        }
    }
    
    /*
     * Muda o Chefe de lugar, com base na sua posição atual
     */
    public void mudarDeLugar() {
        if (estaNaEsquerda) {
            mudarParaLadoDireito();
        } else {
            mudarParaLadoEsquerdo();
        }
    }
    
        // O Chefe vai para o lado Esquerdo
    public void mudarParaLadoEsquerdo() {
        setLocation(ladoEsquerdo, getY());
        estaNaEsquerda = true;
        invocarClone(animCloneDir, ladoDireito);
    }
    
        // O Chefe vai para o lado Direito
    public void mudarParaLadoDireito() {
        setLocation(ladoDireito, getY());
        estaNaEsquerda = false;
        invocarClone(animCloneEsq, ladoEsquerdo);
    }
}
