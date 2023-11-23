import greenfoot.*; 
import java.util.List;

public class Intro extends World
{
    
    private boolean videoIntro = true;
    private boolean temAtoresNoMundo;
    private boolean tocou = false;
    private int count;
    private int tempoEspera;
    
    private boolean irParaMundoCrash = false;
    
    /*
     * Gif e Som da Intro
     */
    
    GifImage gifImg;
    private String locGifIntro = "Gifs/CodeCrash-Intro.gif";
    GreenfootSound somIntro = new GreenfootSound("audioIntro.mp3");
    
    /*
     * Gif da História (Diálogos)
     */
    
    GifActor gifActor;
    private String gifDialog1 = "Gifs/dialogo-inicial-fase_teste.gif";
    
    boolean dialogo = true;
    int tempoGifAtual = 0;
    
    Label label;
    
    private GreenfootSound musicaDeFundo = new GreenfootSound("trilhaSonora-suspense.mp3");
    
    /*
     * Criando Personagens
     */
    
    Jogador jogador1 = new Jogador1();
    Jogador jogador2 = new Jogador2();
    
    Inimigo inimigoTeste = new Inimigo0();
    
    public Intro()
    {
        super(1220, 600, 1);
        
        try {
            gifImg = new GifImage(locGifIntro);
            setBackground(gifImg.getCurrentImage());
            
            label = new Label("'Espaço' para pular a Intro >>", 32);
            addObject(label, getWidth()-200, getHeight()-30);
            temAtoresNoMundo = true;
            irParaMundoCrash = false;
            
            musicaDeFundo.stop();
            musicaDeFundo.setVolume(80);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void act() {
        if (!musicaDeFundo.isPlaying() && !videoIntro && !irParaMundoCrash) {
            musicaDeFundo.play();
        }
        
        if (videoIntro) {
            gifAnimation(gifImg);
            tocarSomIntro();
        }
        
        if ((!somIntro.isPlaying() && tocou) || Greenfoot.isKeyDown("space")) {
            somIntro.stop();
            videoIntro = false;
        }
        
        if (!videoIntro) {
            treinoInicial();
        }
    }
    
    public void treinoInicial() {
        try {
            List<Inimigo> inimigoNoMundo = getObjects(Inimigo.class);
            
            // Irá limpar tudo da tela antes que adicionar os jogadores e o Inimigo teste.
            if (temAtoresNoMundo)
            {
                removerTudoDoMundo();
                addObject(jogador1, 65, 535);
                addObject(jogador2, 170, 535);
                
                inimigoTeste.ativarModoPacifico();
            }
            
            if (dialogo) {
                gifActor = new GifActor(gifDialog1, 1900); // Esse número equivale a 31.1seg
                addObject(gifActor, getWidth()/2, 65);
                dialogo = false;
            }
            
            if(gifActor != null) {
                tempoGifAtual = gifActor.pegarTempoGifAtual();
            }
            
            // Se não tiver inimigo no mundo, adiciona e redefine a força e a vida. Também adiciona uma Label
            if (inimigoNoMundo.isEmpty() && tempoGifAtual < 1000){
                count+=1;
                
                if (count<2) {
                    label = new Label("Ataque!", 50);
                    addObject(inimigoTeste, getWidth()-100, getHeight()-100);  
                    
                    inimigoTeste.definirVida(100);
                    inimigoTeste.definirForca(0);
                    
                    addObject(label, getWidth()/2, getHeight()/2);
                }
            }
            
            // Count 2 significa que invocou um inimigo, e esse inimigo foi morto.
            // Com isso, o mundo irá mudar para o Mundo Crash (Code Crash)
            if (count == 2) {
                irParaMundoCrash = true;
                CodeCrash mundoCrash = new CodeCrash();
                
                Greenfoot.delay(10);
                count = 0;
                
                musicaDeFundo.stop();
                mundoCrash.definirFaseAtual(1);
                Greenfoot.setWorld(mundoCrash);
            }
            
            setBackground("Back01.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void removerTudoDoMundo() {
        try {
            removeObject(label);
            temAtoresNoMundo = false;
            // Libera memória assiciada;
            gifImg.getCurrentImage().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tocarSomIntro() {
        try {
            if (!somIntro.isPlaying() && !tocou) {
                somIntro.play();
                tocou = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void gifAnimation(GifImage gifImage)
    {
        try {
            setBackground(gifImage.getCurrentImage());
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 