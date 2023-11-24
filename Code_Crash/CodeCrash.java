import greenfoot.*;
import java.util.List;
import java.util.Random;

public class CodeCrash extends World {
    /*
     * Atributos do mundo
     */
    private int ladoDireito;
    private int altura;
    
    /*
     * Declarando os Jogadores 
     */ 
    Jogador jogador1;
    Jogador jogador2;
    
    /*
     * Configurações da Horda
     */
    private int tempoEsperaInvocarInimigo;
    private int totalInimigoInvocEmHorda;
    private Inimigo[] tiposDeInimigo;

    private boolean chefeInvocado;
    
    /*
     * Configurações Modo História
     */
    private boolean dialogo;
    private boolean dialogoParte2;
    private boolean dialogoParte3;
    private boolean ativouModoHistoria;
    private int tempoDoGif;
    
    /*
     * Instânciando o plano de fundo
     */
    GreenfootImage fundoFase1;
    GreenfootImage fundoFase2;
    GreenfootImage fundoFase3;
    GreenfootImage fundoFase4;
    
    /*
     * Configuração das fases 
     */
    private int tempoComecarFase;
    private int faseAtual;
    
    /*
     * Configurar Gifs do mundo
     */
    
     // Fase1
    private String locDialogo1;
    private String locDialogo2;
    private String locDialogo3;
    
     // Fase 4
    private String locDialogoInicialFase4;
    private String locDialogoFinalFase4;
    boolean musicaFase4;
    
     // Intro Final 
    private boolean acabouOJogo;
    private boolean introFinal;
    private String localIntroFinal;
    
    /*
     * Configuração do Som
     */
    private GreenfootSound musicaDeFundo;
    private GreenfootSound somIntroFinal;
    
    public CodeCrash() {
        super(1220, 600, 1);
        
        ladoDireito = getWidth();
        altura = getHeight()-100;
        
        jogador1 = new Jogador1();
        jogador2 = new Jogador2();
        
        tempoEsperaInvocarInimigo = 0;
        totalInimigoInvocEmHorda = 0;
        
        dialogo = false;
        dialogoParte2 = false;
        dialogoParte3 = false;
        introFinal = false;
        acabouOJogo = false;
        ativouModoHistoria = false;
        chefeInvocado = false;
        
        tempoComecarFase = 120;
        faseAtual = 0;
        
        locDialogo1 = "Gifs/dialogo2_inicio-fase1.gif";
        locDialogo2 = "Gifs/dialogo3_chefe-fase1.gif";
        locDialogo3 = "Gifs/dialogo4_final-fase1.gif";
        
        locDialogoInicialFase4 = "Gifs/dialogo1_inicio-fase4.gif";
        locDialogoFinalFase4 = "Gifs/dialogo1_chefe-fase4.gif";
        musicaFase4 = false;
        
        fundoFase1 = new GreenfootImage("Back01.png");
        fundoFase2 = new GreenfootImage("Back02.png");
        fundoFase3 = new GreenfootImage("Back03.png");
        fundoFase4 = new GreenfootImage("Back04.png");
                
        localIntroFinal = "Gifs/intro-final.gif";
        acabouOJogo = false;
        
        musicaDeFundo = new GreenfootSound("trilhaSonora.mp3");
        musicaDeFundo.setVolume(70);
        
        fase();
        Greenfoot.start();
    }
    
    public void act() {
        
        if (!musicaDeFundo.isPlaying() && !acabouOJogo) {
            musicaDeFundo.play();
        }
                
        if (!jogador1.estaVivo && !jogador2.estaVivo) {
            
            addObject(new ImagemFundo("gameover.png"), getWidth() / 2, getHeight() / 2);

            if (Greenfoot.isKeyDown("r")) {
                retroceder();
            }
        }
        
        fase();
    }
    
    /*
     * Passa de fase e redefine os jogadores e atributos
     */
    public void passarFase() {
        faseAtual++;
        
        jogador1.redefinirVida();
        jogador2.redefinirVida();
        redefinirConfiguracoes();
    }
    
    /*
     * Maquina de estado para mudar de fase
     */
    public void fase() {
        switch (faseAtual) {
            case 0: 
                Greenfoot.setWorld(new Menu());
                break;
            case 1:
                prepararFase1();
                break;
            case 2:
                prepararFase2();
                break;
            case 3:
                prepararFase3();
                break;
            case 4:
                prepararFase4();
                break;
            default:
                introFinal();
                break;
        }
    }
    
    public void prepararFase1() {
        try {
            final int totalInimigoAInvocar = 15;
            
            configurarJogadores();
            setBackground(fundoFase1);
            
            if (tempoComecarFase > 0) tempoComecarFase--;
            
            if (tempoComecarFase == 0) {
                if (!dialogo) {
                    GifActor gifDialogo1 = new GifActor(locDialogo1, 900);
                    addObject(gifDialogo1, getWidth()/2, 65);
                    
                    dialogo = true;
                }
                
                tiposDeInimigo = new Inimigo[] {
                    new Inimigo0(),
                    new Inimigo1()
                };
                
                gerenciarHorda(totalInimigoAInvocar, 3, tiposDeInimigo);
            }
            
            invocarChefe(new Chefe1(), totalInimigoAInvocar);
            
            if (chefeInvocado) {
                
                List<Chefe> qntChefeNoMundo = getObjects(Chefe.class);
                
                // Exibe a parte 2 do diálogo, caso não tenha iniciado
                if (!dialogoParte2) {
                    definirTemporDoGif(750);
                    
                    GifActor gifDialogo2 = new GifActor(locDialogo2, tempoDoGif);
                    addObject(gifDialogo2, getWidth()/2, 65);
                    
                    dialogoParte2 = true;
                }
                
                // Desativa as cemânicas dos personagens enquanto tiver o diálogo
                if (tempoDoGif > 0) {
                    tempoDoGif--;
                    
                    if (!ativouModoHistoria) {
                        jogador1.ativarModoPacifico();
                        jogador2.ativarModoPacifico();
                        
                        if (!qntChefeNoMundo.isEmpty()) {
                            Chefe primeiroChefe = qntChefeNoMundo.get(0);
                            primeiroChefe.ativarModoPacifico();
                        }
                        ativouModoHistoria = true;
                    }
                }
                
                // Ativa novamente as cemânicas dos personagens quando o diálogo acaba
                if (tempoDoGif == 0 && ativouModoHistoria) {
                    
                    jogador1.desativarModoPacifico();
                    jogador2.desativarModoPacifico();
                    
                    if (!qntChefeNoMundo.isEmpty()) {
                        Chefe primeiroChefe = qntChefeNoMundo.get(0);
                        primeiroChefe.desativarModoPacifico();
                    }
                    ativouModoHistoria = false;
                }
                
                if (qntChefeNoMundo.isEmpty()) {
                    
                    List<Inimigo> inimigoNoMundo = getObjects(Inimigo.class);
                    if (!inimigoNoMundo.isEmpty()){
                        for(int i=0; i < inimigoNoMundo.size(); i++) {
                            inimigoNoMundo.get(i).removerDoMundo();
                        }
                    }
                    
                    // Inicia a parte 3 do diálogo, caso ainda não tenha começado
                    if (!dialogoParte3) {
                        definirTemporDoGif(1800);
                        
                        GifActor gifDialogo3 = new GifActor(locDialogo3, tempoDoGif);
                        addObject(gifDialogo3, getWidth()/2, 65);
                        
                        dialogoParte3 = true;
                    }
                    
                    // Passa para a próxima fase
                    if (tempoDoGif == 0 && dialogoParte3) 
                    {
                        passarFase();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void prepararFase2() {
        try {
            final int totalInimigoAInvocar = 30;
            
            configurarJogadores();
            setBackground(fundoFase2);
            
            if (tempoComecarFase > 0) tempoComecarFase--;
            
            if (tempoComecarFase == 0) {
                tiposDeInimigo = new Inimigo[] {
                    new DroneMaluco(),
                    new Inimigo0(),
                    new Inimigo1()
                };
                
                gerenciarHorda(totalInimigoAInvocar, 6, tiposDeInimigo);
            }
            
            invocarChefe(new Chefe2(), totalInimigoAInvocar);
            
            if (chefeInvocado) {
                List<Chefe> qntChefeNoMundo = getObjects(Chefe.class);
                
                if (qntChefeNoMundo.isEmpty()) {
                    passarFase();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void prepararFase3() {
        
        try {
            final int totalInimigoAInvocar = 45;
            
            configurarJogadores();
            setBackground(fundoFase3);
            
            if (tempoComecarFase > 0) tempoComecarFase--;
            
            if (tempoComecarFase == 0) {
                tiposDeInimigo = new Inimigo[] {
                    new EspectroDoDesespero(),
                    new DroneMaluco(),
                    new Inimigo0(),
                    new Inimigo1()
                };
                
                gerenciarHorda(totalInimigoAInvocar, 9, tiposDeInimigo);
            }
            
            invocarChefe(new Chefe3(), totalInimigoAInvocar);
            
            if (chefeInvocado) {
                List<Chefe> qntChefeNoMundo = getObjects(Chefe.class);
                
                if (qntChefeNoMundo.isEmpty()) {
                    passarFase();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void prepararFase4() {
        try {
            final int totalInimigoAInvocar = 60;
            if (!musicaFase4) {
                musicaDeFundo.stop();
                musicaDeFundo = new GreenfootSound("trilha-melancolica.mp3");
                musicaDeFundo.setVolume(100);
                musicaFase4 = true;
            }
            
            configurarJogadores();
            setBackground(fundoFase4);
            
            if (tempoComecarFase > 0) tempoComecarFase--;
            
            if (tempoComecarFase == 0) {
                
                if (!dialogo) {
                    definirTemporDoGif(1600);
                    GifActor gifDialogo = new GifActor(locDialogoInicialFase4, tempoDoGif);
                    addObject(gifDialogo, getWidth()/2, 65);
                    
                    dialogo = true;
                }
                
                tiposDeInimigo = new Inimigo[] {
                    new EspectroDoDesespero(),
                    new DroneMaluco(),
                    new Inimigo0(),
                    new Inimigo1()
                };
                
                gerenciarHorda(totalInimigoAInvocar, 12, tiposDeInimigo);
            }
            
            invocarChefe(new Chefe4(), totalInimigoAInvocar);
            
            if (chefeInvocado) {
                List<Chefe> qntChefeNoMundo = getObjects(Chefe.class);
                
                // Desativa as cemânicas dos personagens enquanto tiver o diálogo
                if (tempoDoGif > 0) {
                    tempoDoGif--;
                    
                    if (!ativouModoHistoria) {
                        jogador1.ativarModoPacifico();
                        jogador2.ativarModoPacifico();
                
                        if (!qntChefeNoMundo.isEmpty()) {
                            Chefe primeiroChefe = qntChefeNoMundo.get(0);
                            primeiroChefe.ativarModoPacifico();
                        }
                        ativouModoHistoria = true;
                    }
                }
                
                // Ativa novamente as cemânicas dos personagens quando o diálogo acaba
                if (tempoDoGif == 0 && ativouModoHistoria) {
                    
                    jogador1.desativarModoPacifico();
                    jogador2.desativarModoPacifico();
                    
                    if (!qntChefeNoMundo.isEmpty()) {
                        Chefe primeiroChefe = qntChefeNoMundo.get(0);
                        primeiroChefe.desativarModoPacifico();
                    }
                    ativouModoHistoria = false;
                }
                
                if (!dialogoParte2) {
                    definirTemporDoGif(4100);
                    
                    GifActor gifDialogo2 = new GifActor(locDialogoFinalFase4, tempoDoGif);
                    addObject(gifDialogo2, getWidth()/2, 65);
                    
                    dialogoParte2 = true;
                }
                
                if (qntChefeNoMundo.isEmpty()) {
                    
                    List<Inimigo> inimigoNoMundo = getObjects(Inimigo.class);
                    if (inimigoNoMundo.isEmpty()){
                        passarFase();
                    }
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Mostra o epílogo, e muda para o mundo Menu quando o tempo acabar
     */
    public void introFinal() {
        if (tempoDoGif > 0) tempoDoGif--;
        
        if (!introFinal) {
            definirTemporDoGif(5600);
            GifActor gifIntroFinal = new GifActor(localIntroFinal, tempoDoGif);
            addObject(gifIntroFinal, getWidth()/2, getHeight()/2);
            introFinal = true;
            
            somIntroFinal = new GreenfootSound("trilhaSonora-suspense.mp3");
            musicaDeFundo.stop();
            somIntroFinal.play();
            somIntroFinal.setVolume(100);
        }
        
        if(tempoDoGif == 0) {
            acabouOJogo = true;
            musicaDeFundo.stop();
            somIntroFinal.stop();
            Greenfoot.setWorld(new Menu());
        }
    } 
    
    /*
     * Invoca os inimigos enquanto as condicionais forem atendidas
     */
    public void gerenciarHorda(int qntTotalInvoc, int qntInvocPorEtapa, Inimigo[] tipoInimigo) {
        
        // Atenderá essa condição, se o total que eu quero invocar for maior que o total já invocado
        if (qntTotalInvoc > totalInimigoInvocEmHorda) {
        
            // O tempo só será reduzido que ele for maior que 0
            if (tempoEsperaInvocarInimigo > 0) tempoEsperaInvocarInimigo--;
            
            // Pega todos os inimigos do tipo Inimigo que estão presentes no mundo
            
             List<Inimigo> qntInimigoNoMundo = getObjects(Inimigo.class);
            // Atenderá essa condição, se a quantidade de inimigos no mundo
            // for menor que a quant que quero invocar na etapa
            if (qntInvocPorEtapa > qntInimigoNoMundo.size()) {
                horda(tipoInimigo);
            }
        }
    }

    /*
     * Invoca os inimigos da lista aleatóriamente, em um intervalo de 1 seg
     */
    public void horda(Inimigo[] tipoInimigo) 
    {
        // Será executado quando o tempo chegar a 0;
        if (tempoEsperaInvocarInimigo == 0) {
            Random random = new Random();
            
            // Pega um indice aleatório, com base no tamanho da lista de tipos de inimigos
            int indiceAleatorio = random.nextInt(tipoInimigo.length);
            
            addObject(tipoInimigo[indiceAleatorio], getWidth(), getHeight()-50);
            
            // Quando 1 inimigo é invocado, aumenta o total de inimigos invocados
            totalInimigoInvocEmHorda += 1;
            // Quando o inimigo for invocado, o intervalo entre as invocações voltará a ser 1s
            tempoEsperaInvocarInimigo = 60;
        }
    }
    
    /*
     * Invoca os Chefes quando as condição de Horda for completa
     */
    public void invocarChefe(Chefe chefe, int totalInimigoAInvocar) {
        
        if (totalInimigoInvocEmHorda == totalInimigoAInvocar) {
            List<Inimigo> qntInimigoNoMundo = getObjects(Inimigo.class);
            
            if (qntInimigoNoMundo.isEmpty() && !chefeInvocado)
            {
                addObject(chefe, getWidth()-100, getHeight() - chefe.getImage().getHeight()/2);
                chefeInvocado = true;
            }
        }
    }
    
    /*
     * Adicione os Jogadores no mundo se eles estiverem vivos
     */
    public void configurarJogadores() {
        if (jogador1.estaVivo()) {
            this.addObject(jogador1, 65, 535);
        }
        if (jogador2.estaVivo()) {
            this.addObject(jogador2, 170, 535);
        }
    }
    
    /*
     * Redefine todas as configurações, para quando todos os jogadores morrerem
     */
    public void retroceder() {
        faseAtual = 1;
        
        removerTodosOsAtores();
        redefinirConfiguracoes();
        configurarJogadores();
        
        jogador1.redefinirVida();
        jogador1.reiniciarMunicao();
        
        jogador2.redefinirVida();
        jogador2.reiniciarMunicao();
        
        Greenfoot.delay(10);
    }
        
    /*
     * Redefine os atributos para o valor inicial
     */
    public void redefinirConfiguracoes() {
        tempoComecarFase = 120;
        totalInimigoInvocEmHorda = 0;
        tempoEsperaInvocarInimigo = 0;
        chefeInvocado = false;
        
        dialogo = false;
        dialogoParte2 = false;
        dialogoParte3 = false;
        ativouModoHistoria = false;
    }
    
    /*
     * Remove todos os ítens da tela (Usado no momento que os jogadores morrerem)
     */
    public void removerTodosOsAtores() {
        List<Actor> atores = getObjects(Actor.class);
    
        for (Actor ator : atores) {
            removeObject(ator);
        }
    }
    
    /*
     * Define fase atual
     */
    public void definirFaseAtual(int novaFase) 
    {
        this.faseAtual = novaFase;
    }
    
    /*
     * Define tempo de duração do Gif
     */
    public void definirTemporDoGif(int tempo) {
        this.tempoDoGif = tempo;
    }
}