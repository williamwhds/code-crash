import greenfoot.*; 

public class EspectroDoDesespero extends Inimigo
{
    private int velocidadeX = 2;
    
    public EspectroDoDesespero() {
        super(30, 2);
        
        animParadoEsq = super.gerarAnimacao("Inimigos/Fantasma/fantasma_andando_", 6);
        animParadoDir = animParadoEsq;
        animAtacandoEsq = super.gerarAnimacao("Inimigos/Fantasma/fantasma_dano_", 7);
        animAtacandoDir = animAtacandoEsq;
        
        definirVelocidade(velocidadeX);
    }
    
    public void act() {
        super.act();
    }
}
