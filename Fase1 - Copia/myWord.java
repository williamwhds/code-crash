import greenfoot.*;

public class myWord extends World
{
    public myWord()
    {    
        super(1280, 720, 1); 
        getBackground().setColor(new Color(255, 255, 255));
        getBackground().fill();
        addObject(new Jogador1(), 700, 200);
        //addObject(new plataformaPedra(), 166, 356);
    }
}
