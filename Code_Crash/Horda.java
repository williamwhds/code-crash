import greenfoot.*;
import java.util.List;
import java.util.Random;

public class Horda extends Actor {
    private int inimigosTotais;
    private int inimigosEtapa;
    private int quantidadeEtapas;
    private int etapaAtual;
    private Inimigo[] possiveisInimigos;
    private int[][] possiveisCoordenadas;
    private boolean etapaAtiva;
    private boolean hordaAtiva;
    private boolean automatico;

    // Construtor
    public Horda(int inimigosTotais, int inimigosEtapa, Inimigo[] possiveisInimigos, int[][] possiveisCoordenadas, boolean automatico) {
        this.inimigosTotais = inimigosTotais;
        this.inimigosEtapa = inimigosEtapa;
        this.possiveisInimigos = possiveisInimigos;
        this.possiveisCoordenadas = possiveisCoordenadas;
        this.automatico = automatico;
        this.quantidadeEtapas = (int) Math.ceil((double) inimigosTotais / inimigosEtapa);
        this.etapaAtual = 0;
        this.etapaAtiva = false;
        this.hordaAtiva = true;
    }

    // Inicia a horda pela primeira etapa
    public void iniciarHorda() {
        if (!hordaAtiva) {
            return;
        }
        iniciarProximaEtapa();
    }

    // Inicia a próxima etapa
    public void iniciarProximaEtapa() {
        if (!hordaAtiva || etapaFinalizada()) {
            return;
        }
        etapaAtual++;
        etapaAtiva = true;
        spawnarInimigos();
    }

    // Verifica se a etapa foi finalizada
    public boolean etapaFinalizada() {
        if (etapaAtiva && countInimigosAtivos() == 0) {
            etapaAtiva = false;
            return true;
        }
        return false;
    }

    // Verifica se todas as etapas da horda foram finalizadas
    public boolean hordaFinalizada() {
        return etapaAtual == quantidadeEtapas && !etapaAtiva;
    }

    // Método para contar inimigos ativos na etapa
    private int countInimigosAtivos() {
        int count = 0;
        if (etapaAtiva && getWorld() != null) {
            List<Inimigo> inimigos = getWorld().getObjects(Inimigo.class);
            count = inimigos.size();
        }
        return count;
    }
    
    private void spawnarInimigos() {
        if (!etapaAtiva) {
            return;
        }
        
        Random random = new Random();
        
        if (getWorld() != null) {
            for (int i = 0; i < inimigosEtapa; i++) {
                if (countInimigosAtivos() < inimigosTotais) {
                    int indexInimigo = random.nextInt(possiveisInimigos.length);
                    int indexCoordenada = random.nextInt(possiveisCoordenadas.length);
                    Inimigo inimigo = possiveisInimigos[indexInimigo];
                    int x = possiveisCoordenadas[indexCoordenada][0];
                    int y = possiveisCoordenadas[indexCoordenada][1];
                    // Adicione o inimigo ao mundo na posição (x, y)
                    
                    getWorld().addObject(inimigo, x, y);
                }
            }
        }
    }
}
