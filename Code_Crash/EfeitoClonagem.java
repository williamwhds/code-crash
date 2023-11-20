import greenfoot.*;

/*
 * Não consigo pegar os métodos dessa classe quando crio um objeto dela em outra classe
 */


public class EfeitoClonagem extends Efeito
{
    private int vida;
    
    private int tempoEspera = 2*60;
    private boolean estaAndando = false;
    
    GreenfootImage[] animClone;
    
    public EfeitoClonagem(GreenfootImage[] animClone ) {
        this.animClone = animClone;
        animarClone();
    }
    
    public void act()
    {
        super.animar();
        tempoEspera--;
        
        if (tempoEspera <= 0) {
            estaAndando = true;
            moverDireita();
        };
    }
    
    public void animarClone() {
        super.setAnimacaoAtual(animClone);
    }
    
    public void definirVida(int vida) {
        this.vida = vida;
    }
    
    public void diminuirVida(int dano) {
        vida-=dano;
    }
    
    public void removerDoMundo(){
        if (getWorld() != null) {
            Efeito fumaca = new Efeito();
            GreenfootImage[] anim;
            anim = fumaca.gerarAnimacao("Efeitos/Fumaca/fumaca", 8, 10);
            fumaca.setAnimacaoAtual(anim);
            getWorld().removeObject(this);
        }
    }
    
    public void moverDireita() {
        move(5);
    }
}
