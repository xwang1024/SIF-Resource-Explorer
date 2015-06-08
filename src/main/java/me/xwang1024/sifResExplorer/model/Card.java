package me.xwang1024.sifResExplorer.model;

import java.util.Arrays;

public class Card {
	private int id;
	private String[] layers;
	private int moveX;
	private int moveY;
	private float sizeRatio;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String[] getLayers() {
		return layers;
	}

	public void setLayers(String[] layers) {
		this.layers = layers;
	}

	public int getMoveX() {
		return moveX;
	}

	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}

	public int getMoveY() {
		return moveY;
	}

	public void setMoveY(int moveY) {
		this.moveY = moveY;
	}

	public float getSizeRatio() {
		return sizeRatio;
	}

	public void setSizeRatio(float sizeRatio) {
		this.sizeRatio = sizeRatio;
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", layers=" + Arrays.toString(layers)
				+ ", moveX=" + moveX + ", moveY=" + moveY + ", sizeRatio="
				+ sizeRatio + "]";
	}

}
