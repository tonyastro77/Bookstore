package com.tony.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.tony.mygame.RoleGame;
import com.tony.mygame.Settings;
import com.tony.mygame.controller.PlayerController;
import com.tony.mygame.model.Actor;
import com.tony.mygame.model.Camera;
import com.tony.mygame.model.TERRAIN;
import com.tony.mygame.model.TileMap;
import com.tony.mygame.util.AnimationSet;

public class GameScreen extends AbstractScreen {
	
	private PlayerController controller;
	
	private Camera camera;
	private Actor player;
	private TileMap map;
	
	private SpriteBatch batch;	
	private Texture grass1;
	private Texture grass2;

	public GameScreen(RoleGame app) {
		super(app);		
		grass1 = new Texture("res/grass1.png");
		grass2 = new Texture("res/grass2.png");
		batch = new SpriteBatch();
		
		TextureAtlas atlas = app.getAssetManager().get("res/packed/textures.atlas", TextureAtlas.class);
		
		AnimationSet animations = new AnimationSet (
				new Animation(0.3f/2f, atlas.findRegions("tony_walk_north"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("tony_walk_south"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("tony_walk_east"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("tony_walk_west"), PlayMode.LOOP_PINGPONG),
				atlas.findRegion("tony_stand_north"),
				atlas.findRegion("tony_stand_south"),
				atlas.findRegion("tony_stand_east"),
				atlas.findRegion("tony_stand_west")
				);
		
		map = new TileMap(10, 10);
		player = new Actor(map, 0, 0, animations);
		camera = new Camera();
		
		controller = new PlayerController(player);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {
	
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void render(float delta) {
		controller.update(delta);
		
		player.update(delta);
		camera.update(player.getWorldX() + 0.5f, player.getWorldY() + 0.5f);
		
		batch.begin();
		
		float worldStartX = Gdx.graphics.getWidth()/2 - camera.getCameraX() * Settings.SCALE_TILE_SIZE;
		float worldStartY = Gdx.graphics.getHeight()/2 - camera.getCameraY() * Settings.SCALE_TILE_SIZE;
		
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				Texture render;
				if (map.getTile(x, y).getTerrain() == TERRAIN.GRASS_1) {
					render = grass1;		
				} else {
					render = grass2;
				}
				batch.draw(render, 
						worldStartX + x * Settings.SCALE_TILE_SIZE,
						worldStartY + y * Settings.SCALE_TILE_SIZE,
						Settings.SCALE_TILE_SIZE,
						Settings.SCALE_TILE_SIZE);
			}
		}
		
		batch.draw(player.getSprite(), 
				worldStartX + player.getWorldX() * Settings.SCALE_TILE_SIZE, 
				worldStartY + player.getWorldY() * Settings.SCALE_TILE_SIZE, 
				Settings.SCALE_TILE_SIZE, 
				Settings.SCALE_TILE_SIZE*1.5f);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}

}
