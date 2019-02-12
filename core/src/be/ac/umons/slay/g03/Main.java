package be.ac.umons.slay.g03;


import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;




public class Main extends ApplicationAdapter {
	TiledMap map;
	TiledMapTileLayer tiledLayer;
	float unitScale;
	HexagonalTiledMapRenderer renderer;
	OrthographicCamera camera;
	final int length = 32;
	int width;
	int heigth;

	
	@Override
	public void create () {
		map = new TmxMapLoader().load("test.tmx");

		//width = tiledLayer.getWidth();
		//heigth = tiledLayer.getHeight();
		//Cell cell;




		//Cell cell = tiledLayer.getCell(16,4 );
		// int value = (Integer)cell.getTile().getProperties().get("available");
		//System.out.println(value);


		//System.out.println(bol);

		//layer = (TiledMapImageLayer)map.getLayers().get(0);

		//renderer = new HexagonalTiledMapRenderer(map);

		//camera = new OrthographicCamera();
		//camera.setToOrtho(false, (length*width)+length/2f, (((heigth/2f)*length)+((heigth/2f)*(length/2)))+length/4f);
		//camera.update();

		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		//Gdx.gl.glClearColor(158/255f, 158/255f, 158/255f, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		//camera.update();
		//renderer.setView(camera);
		//renderer.render();
	}
	
	@Override
	public void dispose () {

	}

}
