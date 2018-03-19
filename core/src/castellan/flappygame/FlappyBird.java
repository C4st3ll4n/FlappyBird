package castellan.flappygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture[] bird;
    private Texture fundo;
    private int movimentoX;
    private float movimentoY = 0;
    private float c;
    private int larg;
    private int alt;
    private int posInicialV;
    private Texture canoT;
    private Texture canoB;
    private float posMovCanoHor;
    private float espaço;
    private Random r;
    private float altCanos;
    private int estadoJogo = 0;
    private BitmapFont fonte;
    private Circle pasarineo;
    private Rectangle tuboA;
    private Rectangle tuboB;
    private int score = 0;
    private ShapeRenderer sr;
    @Override
    public void create() {
        pasarineo = new Circle();
        tuboA = new Rectangle();
        tuboB = new Rectangle();
        this.larg = Gdx.graphics.getWidth();
        this.alt = Gdx.graphics.getHeight();
        fundo = new Texture("fundo.png");
        batch = new SpriteBatch();
        bird = new Texture[3];
        bird[0] = new Texture("passaro1.png");
        bird[1] = new Texture("passaro2.png");
        bird[2] = new Texture("passaro3.png");
        posInicialV = this.alt / 2;
        canoB = new Texture("cano_baixo.png");
        canoT = new Texture("cano_topo.png");
        posMovCanoHor = larg;
        espaço = 300;
        r = new Random();
        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);
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

            posMovCanoHor -= deltaTime * 300;


            movimentoY++;


            margens();
        }
            batch.begin();
            batch.draw(fundo, 0, 0, larg, alt);
            batch.draw(canoT, posMovCanoHor, alt / 2 + espaço / 2 + altCanos);
            batch.draw(canoB, posMovCanoHor, alt / 2 - canoB.getHeight() - espaço / 2 + altCanos);
            batch.draw(bird[(int) c], 120, posInicialV);
            fonte.draw(batch,String.valueOf(score),larg/2,alt-50);
            batch.end();

            //sr.begin(ShapeRenderer.ShapeType.Filled);

            //sr.end();
    }

    private void margens() {
        // if (this.movimentoX > this.larg) this.movimentoX = -10;

        if (posMovCanoHor <= -200) {
            posMovCanoHor = this.larg;
            altCanos = r.nextInt(400) - 200;
        }

        if (Gdx.input.justTouched()) movimentoY = -20;

        if (posInicialV > 0 || movimentoY < 0) posInicialV -= movimentoY;
    }
}
