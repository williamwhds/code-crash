import greenfoot.*; 
import java.util.List;

public class Intro extends World
{
    /*
     * Intro
     */
    private boolean videoIntro;
    private String locGifIntro;
    private GifImage gifImg;
    private GreenfootSound somIntro;
    
    /*
     * Gif da História (Diálogos)
     */
    private String gifDialog1;
    private boolean dialogo;
    private int tempoGifAtual;
    private GifActor gifActor;
    private Label label;
  
    /*
     * Verificação
     */
    private boolean irParaMundoCrash;
    private boolean temAtoresNoMundo;
    private boolean tocou;
    private int count;
    
    /*
     * Som
     */
    private GreenfootSound musicaDeFundo;
    
    /*
     * Criando Personagens
     */
    private Jogador jogador1;
    private Jogador jogador2;
    private Inimigo inimigoTeste;
    
    public Intro() {
        super(1220, 600, 1);
        
        try {
            videoIntro = true;
            locGifIntro = "Gifs/CodeCrash-Intro.gif";
            somIntro = new GreenfootSound("audioIntro.mp3");
            
            dialogo = true;
            tempoGifAtual = 0;
            gifDialog1 = "Gifs/dialogo-inicial-fase_teste.gif";
            
            temAtoresNoMundo = true;
            irParaMundoCrash = false;
            irParaMundoCrash = false;
            tocou = false;
            
            musicaDeFundo = new GreenfootSound("trilhaSonora-suspense.mp3");
            
            jogador1 = new Jogador1();
            jogador2 = new Jogador2();
            inimigoTeste = new Inimigo0();
             
            gifImg = new GifImage(locGifIntro);
            setBackground(gifImg.getCurrentImage());
            
            label = new Label("'Espaço' para pular a Intro >>", 32);
            addObject(label, getWidth()-200, getHeight()-30);
                    
            musicaDeFundo.stop();
            musicaDeFundo.setVolume(80);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void act() {
        
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
            dialogo();
            
            if (!musicaDeFundo.isPlaying() && !irParaMundoCrash) {
                musicaDeFundo.play();
            }
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
    
    public void dialogo() {
        if (dialogo) {
            gifActor = new GifActor(gifDialog1, 1900);
            addObject(gifActor, getWidth()/2, 65);
            dialogo = false;
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