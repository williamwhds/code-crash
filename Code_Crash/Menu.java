import greenfoot.*;

public class Menu extends World
{
    
    public Menu()
    {    
        super(1220, 600, 1); 
        setBackground("menu-start.png");
    }
    
    public void act() {
        if (Greenfoot.isKeyDown("enter")) {
            começarJogo();
        }
    }
    
    public void começarJogo() {
        setBackground("menu.png");
        Greenfoot.delay(10);
        Greenfoot.setWorld(new Intro());
    }
}
