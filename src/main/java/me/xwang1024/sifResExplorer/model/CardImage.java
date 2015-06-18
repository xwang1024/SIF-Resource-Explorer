package me.xwang1024.sifResExplorer.model;

import java.awt.image.BufferedImage;

public class CardImage {
	public static enum LayerStatus {
		ON, OFF, DISABLED
	}

	private LayerStatus[] layerStatus = new LayerStatus[8];
	private BufferedImage image;
	private String imagePath;

	public CardImage() {
		for (int i = 0; i < 8; i++) {
			layerStatus[i] = LayerStatus.DISABLED;
		}
	}

	public LayerStatus[] getLayerStatus() {
		return layerStatus;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setLayerStatus(int index, LayerStatus status) {
		layerStatus[index] = status;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
