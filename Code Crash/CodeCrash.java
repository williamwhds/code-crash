import greenfoot.*;
import java.util.List;

public class CodeCrash extends World {
    private GreenfootSound musicaDeFundo = musicaDeFundo = new GreenfootSound("trilhaSonora.mp3");
    
    /*
     * Declarando os Jogadores 
     */
    
    Coracao coracao1 = new Coracao();
    Coracao coracao2 = new Coracao();
        
    Jogador jogador1 = new Jogador1(coracao1);
    Jogador jogador2 = new Jogador2(coracao2);
    
    GreenfootImage fundoFase1 = new GreenfootImage("Back01.png");
    GreenfootImage fundoFase2 = new GreenfootImage("Back03.png");
    
    private int faseAtual = 2;
    
    private int totalInimigosInvocados = 0;
    private boolean invocandoInimigos = true;
    
    private boolean estaEsquerda = false;
    
    private int totalChefesInvocados = 0;
    private int numDeInimigosInvocados = 0;
    
    List<Inimigo1> inimigos;
    
    boolean chefeInvocado = false;
    private int tempoDeEspera = 0;
    
    public CodeCrash() {
        super(1220, 600, 1);
        fase();
        musicaDeFundo.setVolume(30);
        //prepare();
    }

    public void act() {
        if (!musicaDeFundo.isPlaying()) {
            musicaDeFundo.play();
        }
        
        fase();
    }
    
    public void configurarJogadores() {
        // Adiciona Corações dos Jogadores
        addObject(coracao1, 100, 100);
        addObject(coracao2, 1220-100, 100);
        // Adiciona Jogadores
        addObject(jogador1, 348, 535);
        addObject(jogador2, 165, 535);
    }
    
    public void passarFase() {
        faseAtual++;
        System.out.println("Passei para a fase: " + faseAtual);
    }
    
    public void fase() {
        switch (faseAtual) {
            case 1:
                prepararFase1();
                break;
            case 2:
                prepararFase2();
                break;
            default:
                break;
        }
    }
    
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
    }


}