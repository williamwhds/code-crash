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
        if (invocandoInimigos && totalInimigosInvocados < 20) {
            invocarInimigos();
        } else if (totalInimigosInvocados >= 20 && invocandoInimigos) {
            chamarChefe();
            invocandoInimigos = false;
        }
    }

    private void prepare() {
        Jogador1 jogador1 = new Jogador1();
        Jogador2 jogador2 = new Jogador2();
        
        addObject(jogador1, 348, 535);
        addObject(jogador2, 165, 535);
        
        // Adicione aqui qualquer outra preparação adicional que você precisa para a fase 1.
    }

    private void invocarInimigos() {
        if (Greenfoot.getRandomNumber(180) == 0) {
            Inimigo1 inimigo1 = new Inimigo1();
            int posX = Greenfoot.getRandomNumber(getWidth());
            int posY = Greenfoot.getRandomNumber(getHeight() / 2);
            addObject(inimigo1, posX, posY);
            totalInimigosInvocados++;
        }
    }

    private void chamarChefe() {
        ChefeFinal chefe = new RabulGenius();
        int posX = getWidth() / 2;
        int posY = getHeight() / 2;
        addObject(chefe, posX, posY);
    }
}
