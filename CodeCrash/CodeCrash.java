import greenfoot.*;

public class CodeCrash extends World {
    private int totalInimigosInvocados = 0;
    private boolean invocandoInimigos = true;
    
    public CodeCrash() {
        super(1220, 600, 1);
        GreenfootImage background = new GreenfootImage("Back01.png");
        setBackground(background);
        prepare();
    }

    public void act() {
        /*
        if (invocandoInimigos && totalInimigosInvocados < 20) {
            invocarInimigos();
        } else if (totalInimigosInvocados >= 20 && invocandoInimigos) {
            chamarChefe();
            invocandoInimigos = false;
        }
        */
    }

    private void prepare() {
        Coracao coracao1 = new Coracao();
        Coracao coracao2 = new Coracao();
        
        Jogador jogador1 = new Jogador1(coracao1);
        Jogador jogador2 = new Jogador2(coracao2);

        addObject(coracao1, 100, 100);
        addObject(coracao2, 400, 100);
        
        addObject(jogador1, 348, 535);
        addObject(jogador2, 165, 535);
        
        //Chefe chefe = new RabulGenius(barraVida);
        //BarraFlex barraVida = new BarraFlex();
        
        Plataforma plataforma = new Plataforma();
        addObject(plataforma,754,465);
        plataforma.setLocation(696,351);
    }
    /*
    public void moverBarra() {
        barraVida.setLocation(getX(), getY()-70);
    }
    */
    
    /*
    private void invocarInimigos(BarraFlex barraVida) {
        if (Greenfoot.getRandomNumber(180) == 0) {
            Inimigo1 inimigo1 = new Inimigo1();
            int posX = Greenfoot.getRandomNumber(getWidth());
            int posY = Greenfoot.getRandomNumber(getHeight() / 2);
            addObject(inimigo1, posX, posY);
            totalInimigosInvocados++;
        }
    }
    
    */
   
    /*

    private void chamarChefe() {
        Chefe chefe = new RabulGenius();
        int posX = getWidth() / 2;
        int posY = getHeight() / 2;
        addObject(chefe, posX, posY);
    }
    */
}
