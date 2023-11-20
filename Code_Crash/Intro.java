import greenfoot.*; 
import java.util.List;

public class Intro extends World
{
    
    private boolean videoIntro = true;
    private boolean temAtoresNoMundo;
    
    GifImage gifImg = new GifImage("Gifs/CodeCrash-Intro.gif");
    GreenfootSound somIntro = new GreenfootSound("audioIntro.mp3");
    
    boolean tocou = false;
    
    Label label;
    
    Jogador jogador1 = new Jogador1();
    Jogador jogador2 = new Jogador2();
    
    Inimigo inimigoTeste = new Inimigo1();
    
    public Intro()
    {
        super(1220, 600, 1);
        setBackground(gifImg.getCurrentImage());
        
        label = new Label("'Espaço' para pular a Intro >>", 32);
        addObject(label, getWidth()-200, getHeight()-30);
        temAtoresNoMundo = true;
    }
    
    public void act() {
        if (videoIntro) {
            gifAnimation();
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
    int count;
    int tempoEspera;
    
    public void treinoInicial() {
        List<Inimigo> inimigoNoMundo = getObjects(Inimigo.class);
        // Remove os atores do mundo, antes de começar a fase de treino;
        if (temAtoresNoMundo)
        {
            removerTudoDoMundo();
            addObject(jogador1, 65, 535);
            addObject(jogador2, 170, 535);
            
            inimigoTeste.ativarModoPacifico();
        }
        
        if (inimigoNoMundo.isEmpty()){
            count+=1;
            
            if (count<2) {
                label = new Label("Ele não é mais o mesmo. >:]", 35);
                addObject(inimigoTeste, getWidth()-100, getHeight()-50);  
                
                inimigoTeste.definirVida(100);
                inimigoTeste.definirForca(0);
                
                addObject(label, getWidth()/2, getHeight()/2);
            }
        }
        
        if (count == 2) {
            Greenfoot.stop();
        }
        
        setBackground("Back01.png");
    }
    
    public void removerTudoDoMundo() {
        removeObject(label);
        temAtoresNoMundo = false;
    }

    public void tocarSomIntro() {
        if (!somIntro.isPlaying() && !tocou) {
            somIntro.play();
            tocou = true;
        }
    }
    
    public void gifAnimation()
    {
        setBackground(gifImg.getCurrentImage());
    }
} 