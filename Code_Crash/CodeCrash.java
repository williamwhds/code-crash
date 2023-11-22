import greenfoot.*;
import java.util.List;

public class CodeCrash extends World {
    /*
     * Declarando os Jogadores 
     */ 
    Jogador jogador1 = new Jogador1();
    Jogador jogador2 = new Jogador2();
    
    /*
     * Configurações dos Inimigos
     */
    private int inimigosMortos = 0;
    private boolean invocarInimigos = true;
    
    /*
     * Configurações dos Chefes
     */   
    private int totalChefesInvocados = 0;
    private int chefeMorto = 0;
    private int tempoEspera = 2*60;
    //boolean chefeInvocado = false;
    
    /*
     * Configuração das fases 
     */
    private int faseAtual = 4;
    
    GreenfootImage fundoFase1 = new GreenfootImage("Back01.png");
    GreenfootImage fundoFase2 = new GreenfootImage("Back03.png");
    GreenfootImage fundoFase3 = new GreenfootImage("Back02.png");
    GreenfootImage fundoFase4 = new GreenfootImage("Back04.png");
    
    /*
     * Configuração do Som
     */
    private GreenfootSound musicaDeFundo = new GreenfootSound("trilhaSonora.mp3");
    
    private int ladoDireito = getWidth();
    private int altura = getHeight()-100;
    
    private boolean iniciarEtapa = false;

    // Corrigir
    //private boolean estaEsquerda = false;
    //private int tempoDeEspera = 0;
    
    public CodeCrash() {
        super(1220, 600, 1);
        fase();
        musicaDeFundo.setVolume(10);
        //prepare();
    }

    public void act() {
        if (!musicaDeFundo.isPlaying()) {
            musicaDeFundo.play();
        }
        
        fase();
    }
    
    public void configurarJogadores() {
        if (jogador1.estaVivo()) {
            this.addObject(jogador1, 65, 535);
        }
        if (jogador2.estaVivo()) {
            this.addObject(jogador2, 170, 535);
        }
    }
    
    public void passarFase() {
        faseAtual++;
        
        jogador1.redefinirVida();
        jogador2.redefinirVida();
        redefinirConfiguracoes();
    }
    
    public void redefinirConfiguracoes() {
        tempoEspera = 2*60;
        inimigosMortos = 0;
        totalChefesInvocados = 0;
        chefeMorto = 0;
    }
    
    public void fase() {
        switch (faseAtual) {
            case 1:
                // prepararFase1();
                break;
            case 2:
                // prepararFase2();
                break;
            case 3:
                prepararFase3();
                break;
            case 4:
                prepararFase4();
                break;
            default:
                setBackground(fundoFase1);
                break;
        }
    }
    /*
    public void prepararFase1() {
        setBackground(fundoFase1);
        configurarJogadores();
        
        inimigos = getObjects(Inimigo1.class);
        int numDeInimigos = inimigos.size();
        
        if (numDeInimigos < 2 && totalInimigosInvocados < 20) {
            Inimigo inimigo = new Inimigo1();
            int posY = getHeight() / 2;
            int posX;
            if (estaEsquerda) {
                posX = 0;
                estaEsquerda = false;
            } else {
                posX = getWidth();
                estaEsquerda = true;
            }
            addObject(inimigo, posX, posY);
            totalInimigosInvocados++;
        }
        
        if (numDeInimigos == 0 && totalInimigosInvocados == 20) {
            if (getObjects(RabulGenius.class).isEmpty() && !chefeInvocado) {
                
                Chefe chefe = new RabulGenius();
                addObject(chefe, getWidth(), 500);
                chefeInvocado = true;
                
            } else {
                List<RabulGenius> chefe = getObjects(RabulGenius.class);
                List<DroneMaluco> drone = getObjects(DroneMaluco.class);
                
                if (chefeInvocado && chefe.isEmpty() && drone.isEmpty()) {
                    passarFase();
                    numDeInimigos = 0;
                    totalInimigosInvocados = 0;
                    chefeInvocado = false;
                    fase();
                }
            }
        }
    }
    
    public void prepararFase2() {
        setBackground(fundoFase2);
        configurarJogadores();
    
        int posY = getHeight() / 2;
        int posX;
        if (!estaEsquerda) {
            posX = 0;
            estaEsquerda = true;
        } else {
            posX = getWidth();
        }
    
        int escolhaInimigo;
        Inimigo inimigo = null;
    
        if (totalInimigosInvocados < 40) {
            if (tempoDeEspera % (60 * 3) == 0) {
    
                for (int i = 0; i < 4; i++) {
                    escolhaInimigo = Greenfoot.getRandomNumber(3);
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
                            // Lógica de erro, caso necessário
                            break;
                    }
                    addObject(inimigo, posX, posY);
                    totalInimigosInvocados++;
                }
            }
            tempoDeEspera++;
        }
    
        List<Inimigo> inimigos = getObjects(Inimigo.class);
    
        if (totalInimigosInvocados == 40 && inimigos.isEmpty() && !chefeInvocado) {
            chefeInvocado = true;
        }
    
        if (chefeInvocado && getObjects(Chefe2.class).isEmpty()) {
            Chefe chefe = new Chefe2();
            addObject(chefe, getWidth(), 530);
        }
    
        List<Chefe2> chefe = getObjects(Chefe2.class);
        List<EspectroDoDesespero> espectro = getObjects(EspectroDoDesespero.class);
    
        if (chefeInvocado && chefe.isEmpty() && espectro.isEmpty()) {
            passarFase();
            totalInimigosInvocados = 0;
            chefeInvocado = false;
            fase();
        }
    } */
    
    public void prepararFase3() {
        setBackground(fundoFase3);
        configurarJogadores();
        
        int numeroDeInimigos = contarInimigos();
        
        Horda horda = new Horda(6, 6, new Inimigo[]{new Inimigo1(), new EspectroDoDesespero()}, new int[][]{{ladoDireito, altura}}, false);
        addObject(horda, 0, 0);
        
        if (inimigosMortos < 25) {
            horda.iniciarProximaEtapa();
        }
        /*
        if (invocarInimigos && inimigosMortos < 25) {
            horda.iniciarProximaEtapa();
            invocarInimigos = false;
        }*/
        
        if (inimigosMortos >= 25 && totalChefesInvocados == 0 && numeroDeInimigos == 0) {
            tempoEspera--;
            if (tempoEspera == 0) {
                Chefe chefe3 = new Chefe3();
                addObject(chefe3, getWidth(), getHeight() - 145);
                totalChefesInvocados = 1;
            }
        }
        
        if (chefeMorto == 1) {
            passarFase();
            //System.out.println("Passar De Fase " + faseAtual);
        }
    }
    int invocouHorda = 0;
    Horda horda;
    
    public void prepararFase4() {
        setBackground(fundoFase4);
        configurarJogadores();
        
        int numeroDeInimigos = contarInimigos();
        
        if (invocouHorda < 1) {
            horda = new Horda(12, 3, new Inimigo[]{new Inimigo1(), new EspectroDoDesespero()}, new int[][]{{ladoDireito, altura}}, false);
            
            invocouHorda+=1;
        }
        addObject(horda, 0, 0);
        
        if (inimigosMortos < 35) {
            horda.iniciarProximaEtapa();
        }
        
        if (inimigosMortos >= 35 && totalChefesInvocados == 0 && numeroDeInimigos == 0) {
            tempoEspera--;
            if (tempoEspera == 0) {
                Chefe chefe4 = new Chefe4();
                addObject(chefe4, getWidth()-100, getHeight() - 220);
                totalChefesInvocados = 1;
            }
        }
        
        if (chefeMorto == 1) {
            passarFase();
        }
    }
    
    public int contarInimigos() {
        List<Inimigo> inimigos = getObjects(Inimigo.class);
        return inimigos.size();
    }
    
    public void inimigoDerrotado() {
        this.inimigosMortos++;
        //System.out.println("Inimigos Mortos: " + inimigosMortos);
    }
    
    public void chefeDerrotado() {
        this.chefeMorto++;
        System.out.println("Boss Mortos: " + chefeMorto);
    }
}