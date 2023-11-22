import greenfoot.*; 
import java.util.List;

public class Chefe4 extends Chefe
{
    static int vida = 300;
    static int forca = 1;
    
    private int tempoImune = 7 * 60;
    private int tempoVulneravel = 3 * 60;
    private int tempoAtual = 0;
    
    private int invocados = 0;
    private int n = 0;
    private int intervalo = 1*60;

    
    public Chefe4() {
        super(vida, forca);
        
        animParadoEsq = super.gerarAnimacao("Chefes/Chefe4/chefe4_vulneravel", 6);
        animAtacandoEsq = super.gerarAnimacao("Chefes/Chefe4/chefe4_atacando", 6);
        
        super.estadoChefeAtual = EstadoChefe.PARADO_ESQ;
        
        super.ficarImune();

        this.tempoEspera = tempoImune;
    }
    
    public void act()
    {
        super.animar();
        super.animChefe();
        iniciarAtaque();
    }
    
    public void iniciarAtaque() {
        if (estaImune) {
            super.estadoChefeAtual = EstadoChefe.ATACANDO_ESQ;
            if (tempoAtual < tempoImune) {
                tempoAtual++;
                atacar();
                
            } else {
                super.tornarVulneravel();
                super.estadoChefeAtual = EstadoChefe.PARADO_ESQ;
                tempoAtual = 0;
                tempoEspera = tempoVulneravel;
            }
            
        } else {
            super.estadoChefeAtual = EstadoChefe.PARADO_ESQ;
            if (tempoAtual < tempoEspera) {
                tempoAtual++;
                
            } else {
                ficarImune();
                tempoAtual = 0;
                tempoEspera = tempoImune;
            }
        }
    }

    public void atacar() {
        double vidaAtual = super.pegarVida();
        double porcenVida = (vidaAtual / vida) * 100;
        
        if (n == 0) {
            if (porcenVida >= 80.0) {
                n = 6;
            } else if (porcenVida >= 50.0) {
                n = 10;
            } else if (porcenVida >= 20.0) {
                n = 16;
            } else if (porcenVida > 0) {
                n = 20;
            }
        }
        
        intervalo--;
        if (invocados <= n && intervalo <= 0) {

            for (int i = 0; i < 1; i++) {
                int escolhaInimigo = Greenfoot.getRandomNumber(3);
                Inimigo inimigo = null;
    
                switch (escolhaInimigo) {
                    case 0:
                        inimigo = new Inimigo1();
                        break;
                    case 1:
                        inimigo = new DroneMaluco();
                        break;
                    case 2:
                        inimigo = new EspectroDoDesespero();
                        break;
                    default:
                        break;
                }
    
                // Adicione o inimigo ao mundo
                if (inimigo != null) {
                    getWorld().addObject(inimigo, getX(), getY());
                }
            }
            
            invocados+=1;
            n-=1;
            intervalo = 1*60;
        }
        
        List<Inimigo> inimigosNoMundo = getWorld().getObjects(Inimigo.class);
        if (inimigosNoMundo.size() == 0) {
            invocados = 0;
        }
    }
    /*
    public boolean existemInimigosNoMundo() {
        List<Inimigo> inimigosNoMundo = getWorld().getObjects(Inimigo.class);
        return !inimigosNoMundo.isEmpty();
    }*/
}
