package castellan.flappygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture bird;
	private Texture fundo;
	private int movimentoX;
	private int movimentoY;

	private int larg;
	private int alt;

	@Override
	public void create () {
        this.larg = Gdx.graphics.getWidth();
        this.alt = Gdx.graphics.getHeight();
	    fundo = new Texture("fundo.png");
	    batch = new SpriteBatch();
	    bird = new Texture("passaro1.png");
	}

	@Override
	public void render () {
        movimentoX += 4;
        batch.begin();

        batch.draw(fundo,0,0,larg,alt);

        batch.draw(bird,movimentoX,this.alt/2);

        margens();

        batch.end();
	}

	public void margens(){
	    if (this.movimentoX >this.larg){
	        this.movimentoX = 0;
        }
        if (this.movimentoY >= this.alt || this.movimentoY <= 0){
            System.out.println("Moleu");
        }
    }
}