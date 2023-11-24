import greenfoot.*;

public class Menu extends World
{
    private GreenfootSound somConfirmar;
    private GreenfootSound somMenu;
    private boolean iniciou;
    
    public Menu()
    {    
        super(1220, 600, 1); 
        
        somMenu = new GreenfootSound("trilhaSonora-Menu.mp3");
        iniciou = false;
        
        setBackground("menu-start.png");
        somMenu.setVolume(70);
        iniciou = false;
    }
    
    public void act() {
        if (Greenfoot.isKeyDown("enter")) {
            começarJogo();
        }
        
        if (!somMenu.isPlaying() && !iniciou) {
            somMenu.play();
        }
    }
    
    public void começarJogo() {
        somConfirmar = new GreenfootSound("somConfirmar.mp3");
        somConfirmar.play();
        iniciou = true;
        
        setBackground("menu.png");
        Greenfoot.delay(10);
        Greenfoot.setWorld(new Intro());
        somMenu.stop();
    }
}
