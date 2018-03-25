package castellan.flappygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    //1920x1080
    private final float VIRTUAL_W = 768;
    private final float VIRTUAL_H = 1024;
    // Texturas
    private Texture[] bird;
    private Texture fundo;
    private Texture canoT;
    private Texture canoB;
    private Texture gameOver;
    // Camera
    private OrthographicCamera camera;
    private Viewport viewport;

    // "Desenhador"
    private SpriteBatch batch;

    // Queda
    private float movimentoY = 0;

    // Controlador de texturas
    private float c;

    // Dimensões do celular ou tablet
    private float larg;
    private float alt;

    // Posição inicial Vertical
    private float posInicialV;

    // Movimentação dos canos
    private float posMovCanoHor;

    // Espaço em que o passaro pode passar
    private float espaço;

    // Número randomico para que os canos apareçam diferentes a cada passagem
    private Random r;

    // Dimensão de altura dos canos
    private float altCanos;

    // Controlador de estados do jogo
    // | 0 -> Parado | 1 -> Iniciado ( Rodando ) | 2 -> Game Over
    private int estadoJogo = 0;

    //Pontuação e verificador de pontuação
    private int score = 0;
    private boolean marcou = false;

    // Textos da pontuação e de Game Over
    private BitmapFont fonte;
    private BitmapFont msg;

    // Colidores
    private Circle pasarineo;
    private Rectangle tuboA;
    private Rectangle tuboB;

    // "Desenhador" de colidores
    //private ShapeRenderer sr;

    @Override
    public void create() {
        //sr = new ShapeRenderer();
        /*tuboA = new Rectangle();
        tuboB = new Rectangle();*/

        config();
    }

    @Override
    public void render() {
        voo();

        if (estadoJogo == 0) {

            if (Gdx.input.justTouched()) estadoJogo = 1;
        } else {
            movimentoY++;
            if (estadoJogo == 1) {
                rodando();
            }
            if (Intersector.overlaps(pasarineo, tuboB) ||
                    Intersector.overlaps(pasarineo, tuboA) ||
                    posInicialV <= 0 || posInicialV >= (alt - 50))
                estadoJogo = 2;
        }
        desenhar();
        /*sr.begin(ShapeRenderer.ShapeType.Line);
        sr.rect(tuboB.x,tuboB.y,tuboB.width,tuboB.height);
        sr.rect(tuboA.x,tuboA.y,tuboA.width,tuboA.height);
        sr.circle(pasarineo.x, pasarineo.y, pasarineo.radius);
        sr.setColor(Color.BLUE);
        sr.end();*/
    }

    private void rodando() {

        if (posInicialV > 0 || movimentoY < 0) posInicialV -= movimentoY;

        posMovCanoHor -= Gdx.graphics.getDeltaTime() * 300;

        if (Gdx.input.justTouched()) movimentoY = -20;

        if (posMovCanoHor <= -200) {
            posMovCanoHor = this.larg;
            altCanos = r.nextInt(400) - 200;
            marcou = false;
        }
        if (posMovCanoHor < 120) {
            if (!marcou) {
                score += 1;
                marcou = true;
            }
        }

    }

    private void reinicio() {
        if (Gdx.input.justTouched()) {
            estadoJogo = 0;
            score = 0;
            posInicialV = alt / 2;
            posMovCanoHor = -200;
            movimentoY = 0;
        }
    }

    private void voo() {
        c += Gdx.graphics.getDeltaTime() * 10;
        if (c >= bird.length) c = 0;
    }

    private void desenhar() {
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(fundo, 0, 0, larg, alt);
        batch.draw(canoT, posMovCanoHor, alt / 2 + espaço / 2 + altCanos);
        batch.draw(canoB, posMovCanoHor, alt / 2 - canoB.getHeight() - espaço / 2 + altCanos);
        batch.draw(bird[(int) c], 120, posInicialV);
        fonte.draw(batch, String.valueOf(score), larg / 2, alt - 50);

        if (estadoJogo == 2) {
            batch.draw(gameOver, larg / 3 - 50, alt / 2);
            msg.draw(batch, "Toque para reiniciar", larg / 3 - 50, alt / 2 - 50);
            reinicio();
        }
        batch.end();

        tuboB = new Rectangle(posMovCanoHor, alt / 2 - canoB.getHeight() - espaço / 2 + altCanos,
                canoB.getWidth(), canoB.getHeight());

        tuboA = new Rectangle(posMovCanoHor, alt / 2 + espaço / 2 + altCanos, canoT.getWidth(), canoT.getHeight());

        pasarineo = new Circle();

        pasarineo.set(120 + bird[0].getWidth() / 2, posInicialV + bird[0].getHeight() / 2, bird[0].getWidth() / 2);
    }

    private void config() {
        // Setando as configs:

        // dimensões do celular/tablet;
        this.alt = VIRTUAL_W;
        this.larg = VIRTUAL_H;

        // inicio do Flappy;
        posInicialV = this.alt / 2;

        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_W / 2, VIRTUAL_H / 2, 0);
        viewport = new StretchViewport(VIRTUAL_W, VIRTUAL_H, camera);

        fundo = new Texture("fundo.png");
        batch = new SpriteBatch();
        bird = new Texture[3];

        bird[0] = new Texture("passaro1.png");
        bird[1] = new Texture("passaro2.png");
        bird[2] = new Texture("passaro3.png");
        canoB = new Texture("cano_baixo.png");
        canoT = new Texture("cano_topo.png");
        gameOver = new Texture("game_over.png");
        posMovCanoHor = larg;
        espaço = 300;
        r = new Random();

        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);

        msg = new BitmapFont();
        msg.setColor(Color.WHITE);
        msg.getData().setScale(3);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
