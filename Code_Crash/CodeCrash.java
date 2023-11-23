import greenfoot.*;
import java.util.List;
import java.util.Random;

public class CodeCrash extends World {
    /*
     * Atributos do mundo
     */
    private int ladoDireito = getWidth();
    private int altura = getHeight()-100;
    
    /*
     * Declarando os Jogadores 
     */ 
    Jogador jogador1 = new Jogador1();
    Jogador jogador2 = new Jogador2();
    
    /*
     * Configurações da Horda
     */
    
    private int tempoEsperaInvocarInimigo = 0;
    private int totalInimigoInvocEmHorda = 0;
    
    boolean chefeInvocado = false;
    int tempoComecarFase = 120;
    boolean dialogo = false;
    Inimigo[] tiposDeInimigo;
    
    boolean ativouModoPacifico = false;
    
    /*
     * Configurações dos Inimigos
     */
    private boolean invocarInimigos = true;
    
    /*
     * Configurações dos Chefes
     */   
    // private int tempoEspera = 2*60;
    // boolean chefeInvocado = false;
    
    /*
     * Configuração das fases 
     */
    private int faseAtual = 0;
    
    GreenfootImage fundoFase1 = new GreenfootImage("Back01.png");
    GreenfootImage fundoFase2 = new GreenfootImage("Back03.png");
    GreenfootImage fundoFase3 = new GreenfootImage("Back02.png");
    GreenfootImage fundoFase4 = new GreenfootImage("Back04.png");
    
    /*
     * Configuração do Som
     */
    private GreenfootSound musicaDeFundo = new GreenfootSound("trilhaSonora.mp3");
    
    /*
     * Configurar Gifs do mundo
     */
    
     // Fase1
    private String locDialogo1 = "Gifs/dialogo2_inicio-fase1.gif";
    private String locDialogo2 = "Gifs/dialogo3_chefe-fase1.gif";
    private String locDialogo3 = "Gifs/dialogo4_final-fase1.gif";
    
     // Fase 4
    private String locDialogoInicialFase4 = "Gifs/dialogo1_inicio-fase4.gif";
    private String locDialogoFinalFase4 = "Gifs/dialogo1_chefe-fase4.gif";
    
    // Intro Final 
    
    private String localIntroFinal = "Gifs/intro-final.gif";
    
    private boolean acabouOJogo = false;
    
    boolean introFinal = false;
    GreenfootSound somIntroFinal;
    
    boolean musicaFase4 = false;
    
    public CodeCrash() {
        super(1220, 600, 1);
        fase();
        acabouOJogo = false;
        musicaFase4 = false;
        musicaDeFundo.setVolume(70);
        Greenfoot.start();
        //prepare();
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
    
    public void configurarJogadores() {
        if (jogador1.estaVivo()) {
            this.addObject(jogador1, 65, 535);
        }
        if (jogador2.estaVivo()) {
            this.addObject(jogador2, 170, 535);
        }
    }
    
    public void passarFase() {
        faseAtual++;
        
        jogador1.redefinirVida();
        jogador2.redefinirVida();
        redefinirConfiguracoes();
    }
    
    public void retroceder() {
        faseAtual = 1;
        removerTodosOsAtores();
        
        configurarJogadores();
        
        jogador1.redefinirVida();
        jogador1.reiniciarMunicao();
        
        jogador2.redefinirVida();
        jogador2.reiniciarMunicao();
        
        redefinirConfiguracoes();
        Greenfoot.delay(10);
    }
    
    public void removerTodosOsAtores() {
        List<Actor> atores = getObjects(Actor.class);
    
        for (Actor ator : atores) {
            removeObject(ator);
        }
    }
    
    public void redefinirConfiguracoes() {
        tempoComecarFase = 120;
        totalInimigoInvocEmHorda = 0;
        tempoEsperaInvocarInimigo = 0;
        chefeInvocado = false;
        
        dialogo = false;
        dialogoParte2 = false;
        dialogoParte3 = false;
        ativouModoPacifico = false;
    }
    
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
    boolean dialogoParte2 = false;
    boolean dialogoParte3 = false;
    int tempoDoGif;
    
    public void definirTemporDoGif(int tempo) {
        this.tempoDoGif = tempo;
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
                
                if (!dialogoParte2) {
                    definirTemporDoGif(750);
                    
                    GifActor gifDialogo2 = new GifActor(locDialogo2, tempoDoGif);
                    addObject(gifDialogo2, getWidth()/2, 65);
                    
                    dialogoParte2 = true;
                }
                
                // Desativa as cemânicas dos personagens enquanto tiver o diálogo
                if (tempoDoGif > 0) {
                    tempoDoGif--;
                    
                    if (!ativouModoPacifico) {
                        jogador1.ativarModoPacifico();
                        jogador2.ativarModoPacifico();
                        
                        if (!qntChefeNoMundo.isEmpty()) {
                            Chefe primeiroChefe = qntChefeNoMundo.get(0);
                            primeiroChefe.ativarModoPacifico();
                        }
                        ativouModoPacifico = true;
                    }
                }
                
                // Ativa novamente as cemânicas dos personagens quando o diálogo acaba
                if (tempoDoGif == 0 && ativouModoPacifico) {
                    
                    jogador1.desativarModoPacifico();
                    jogador2.desativarModoPacifico();
                    
                    if (!qntChefeNoMundo.isEmpty()) {
                        Chefe primeiroChefe = qntChefeNoMundo.get(0);
                        primeiroChefe.desativarModoPacifico();
                    }
                    ativouModoPacifico = false;
                }
                
                if (qntChefeNoMundo.isEmpty()) {
                    
                    List<Inimigo> inimigoNoMundo = getObjects(Inimigo.class);
                    if (!inimigoNoMundo.isEmpty()){
                        for(int i=0; i < inimigoNoMundo.size(); i++) {
                            inimigoNoMundo.get(i).removerDoMundo();
                        }
                    }
                    
                    
                    if (!dialogoParte3) {
                        definirTemporDoGif(1800);
                        
                        GifActor gifDialogo3 = new GifActor(locDialogo3, tempoDoGif);
                        addObject(gifDialogo3, getWidth()/2, 65);
                        
                        dialogoParte3 = true;
                    }
                    
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
            setBackground(fundoFase3);
            
            if (tempoComecarFase > 0) tempoComecarFase--;
            
            if (tempoComecarFase == 0) {
                /*
                if (!dialogo) {
                    GifActor gifDialogo1 = new GifActor(locDialogo1, 800);
                    addObject(gifDialogo1, getWidth()/2, 65);
                    
                    dialogo = true;
                }*/
                
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
            setBackground(fundoFase2);
            
            if (tempoComecarFase > 0) tempoComecarFase--;
            
            if (tempoComecarFase == 0) {
                /*
                if (!dialogo) {
                    GifActor gifDialogo1 = new GifActor(locDialogo1, 800);
                    addObject(gifDialogo1, getWidth()/2, 65);
                    
                    dialogo = true;
                }*/
                
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
                    
                    if (!ativouModoPacifico) {
                        jogador1.ativarModoPacifico();
                        jogador2.ativarModoPacifico();
                
                        if (!qntChefeNoMundo.isEmpty()) {
                            Chefe primeiroChefe = qntChefeNoMundo.get(0);
                            primeiroChefe.ativarModoPacifico();
                        }
                        ativouModoPacifico = true;
                    }
                }
                
                // Ativa novamente as cemânicas dos personagens quando o diálogo acaba
                if (tempoDoGif == 0 && ativouModoPacifico) {
                    
                    jogador1.desativarModoPacifico();
                    jogador2.desativarModoPacifico();
                    
                    if (!qntChefeNoMundo.isEmpty()) {
                        Chefe primeiroChefe = qntChefeNoMundo.get(0);
                        primeiroChefe.desativarModoPacifico();
                    }
                    ativouModoPacifico = false;
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
    
    public int contarInimigos() {
        List<Inimigo> inimigos = getObjects(Inimigo.class);
        return inimigos.size();
    }
    
    public void definirFaseAtual(int novaFase) 
    {
        this.faseAtual = novaFase;
    }
    
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
    
    // Redefinir 'chefeInvocado' para false; TotalInimigoAInvocar = 0;
    // totalInimigoInvocEmHorda = 0; 
    
}