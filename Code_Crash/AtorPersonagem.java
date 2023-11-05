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
        if (estaImune && estaVivo) {
            vida-=dano;
            
            if (vida < 0 ) {
                vida = 0;
            }
            
            tornarImune(180);
        }
    }
}
