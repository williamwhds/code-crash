import greenfoot.*; 

public class AtorPersonagem extends ObjetoAnimado
{
    /*
     * Configura Vida
     */
    protected int vida;
    protected boolean estaVivo = true;
    
    /*
     * Imunidade
     */
    protected boolean estaImune = false;
    private int tempoImunidade;
    
    /*
     * Torna o personagem imune
     */
    public void tornarImune(int tempoImunidade) {
        estaImune = true;
        this.tempoImunidade = tempoImunidade;
    }
    
    public void ficarImune() {
        estaImune = true;
    }
    
        // O personagem fica imune a ataques até que o tempo chegue a 0
    public void gerenciarImunidade() {
        if (estaImune) { 
            tempoImunidade--;
            if (tempoImunidade <= 0) {
                estaImune = false;
            }
        }
    }
    
        // O personagem fica vulnerável a ataques
    public void tornarVulneravel() {
        estaImune = false;
        tempoImunidade = 0;
    }
    
    /*
     * Receber dano dos inimigos
     */
    public void receberAtaque(int dano) {
        if (!estaImune && estaVivo) {
            vida-=dano;
            efeitoSangue();            
            if (vida <= 0 ) {
                efeitoFumaca(10);
                vida = 0;
                //getWorld().removeObject(this);
            }
            //System.out.println("Ele está perdendo vida!: " + vida);
        }
        //System.out.println("Imunidade: " + estaImune);
    }
    
    // Configura o tamanho da animação da fumaça
    public void efeitoFumaca(int escala) {
        Efeito fumaca = new EfeitoFumaca();
        
        GreenfootImage[] animFumaca;
        animFumaca = fumaca.gerarAnimacao("Efeitos/Fumaca/fumaca", 8, escala);
        fumaca.setAnimacaoAtual(animFumaca);
        
        getWorld().addObject(fumaca, getX(), getY()+15);
    }
    
    public void efeitoSangue() {
        Efeito sangue = new EfeitoSangue();
        
        GreenfootImage[] animSangue;
        animSangue = sangue.gerarAnimacao("Efeitos/Sangue/sangue", 6, 5);
        sangue.setAnimacaoAtual(animSangue);
        
        getWorld().addObject(sangue, getX(), getY());
    }
    
    public int segundos(int segundo) {
        return segundo*60;
    }
}
