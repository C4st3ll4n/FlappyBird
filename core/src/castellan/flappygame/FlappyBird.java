package castellan.flappygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture[] bird;
    private Texture fundo;
    private float movimentoY = 0;
    private float c;
    private int larg;
    private int alt;
    private int posInicialV;
    private float posMovCanoHor;
    private float espaço;
    private Random r;
    private float altCanos;
    private int estadoJogo = 0;
    private int score = 0;
    private boolean marcou = false;

    private Texture canoT;
    private Texture canoB;
    private Texture gameOver;
    private BitmapFont fonte;
    private BitmapFont msg;
    private Circle pasarineo;
    private Rectangle tuboA;
    private Rectangle tuboB;
    private ShapeRenderer sr;

    @Override
    public void create() {
        sr = new ShapeRenderer();
        /*tuboA = new Rectangle();
        tuboB = new Rectangle();*/

        this.alt = Gdx.graphics.getHeight();
        posInicialV = this.alt / 2;
        this.larg = Gdx.graphics.getWidth();
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
    public void render() {
        double deltaTime = Gdx.graphics.getDeltaTime();
        //movimentoX += 4;
        c += deltaTime * 10;
        if (c >= bird.length) c = 0;

        if (estadoJogo == 0) {

            if (Gdx.input.justTouched()) {
                estadoJogo = 1;
            }
        } else {

            if (posInicialV > 0 || movimentoY < 0) posInicialV -= movimentoY;

            movimentoY++;
            if (estadoJogo == 1) {

                posMovCanoHor -= deltaTime * 300;

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
            } else {

            }

            if (Intersector.overlaps(pasarineo, tuboB) || Intersector.overlaps(pasarineo, tuboA))
                estadoJogo = 2;
        }
        batch.begin();
        batch.draw(fundo, 0, 0, larg, alt);
        batch.draw(canoT, posMovCanoHor, alt / 2 + espaço / 2 + altCanos);
        batch.draw(canoB, posMovCanoHor, alt / 2 - canoB.getHeight() - espaço / 2 + altCanos);
        batch.draw(bird[(int) c], 120, posInicialV);
        fonte.draw(batch, String.valueOf(score), larg / 2, alt - 50);

        if (estadoJogo == 2) {
            batch.draw(gameOver, larg / 3 - 50, alt / 2);
            msg.draw(batch, "Toque para reiniciar", larg / 3 - 50, alt / 2 - 50);

            if (Gdx.input.justTouched()) {
                posInicialV = alt / 2;
                estadoJogo = 1;
                score = 0;
                posMovCanoHor = -100;
            }
        }
        batch.end();

        tuboB = new Rectangle(posMovCanoHor, alt / 2 - canoB.getHeight() - espaço / 2 + altCanos,
                canoB.getWidth(), canoB.getHeight()
        );
        tuboA = new Rectangle(posMovCanoHor, alt / 2 + espaço / 2 + altCanos, canoT.getWidth(), canoT.getHeight());

        pasarineo = new Circle(120, posInicialV, bird[0].getWidth() - 20);
        Gdx.app.log("R ->", String.valueOf(bird[0].getWidth()));

        pasarineo.set(120 + bird[0].getWidth() / 2, posInicialV + bird[0].getHeight() / 2, bird[0].getWidth() / 2);
        /*sr.begin(ShapeRenderer.ShapeType.Line);
        sr.rect(tuboB.x,tuboB.y,tuboB.width,tuboB.height);
        sr.rect(tuboA.x,tuboA.y,tuboA.width,tuboA.height);
        sr.circle(pasarineo.x, pasarineo.y, pasarineo.radius);
        sr.setColor(Color.BLUE);
        sr.end();*/
    }
/*
    private void margens() {
        // if (this.movimentoX > this.larg) this.movimentoX = -10;


    }*/
}
