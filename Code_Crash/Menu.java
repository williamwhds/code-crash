import greenfoot.*;

public class Menu extends World
{
    private GreenfootSound somConfirmar;
    private GreenfootSound somMenu = new GreenfootSound("trilhaSonora-Menu.mp3");
    private boolean iniciou = false;
    
    public Menu()
    {    
        super(1220, 600, 1); 
        setBackground("menu-start.png");
        somMenu.setVolume(70);
        iniciou = false;
    }
    
    public void act() {
        if (Greenfoot.isKeyDown("enter")) {
            somConfirmar = new GreenfootSound("somConfirmar.mp3");
            somConfirmar.play();
            iniciou = true;
            começarJogo();
        }
        
        if (!somMenu.isPlaying() && !iniciou) {
            somMenu.play();
        }
    }
    
    public void começarJogo() {
        setBackground("menu.png");
        Greenfoot.delay(10);
        Greenfoot.setWorld(new Intro());
        somMenu.stop();
    }
}
