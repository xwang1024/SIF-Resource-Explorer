package me.xwang1024.sifResExplorer.model;

public class AssetItem {

	private String imageFilePath;
	private String refTextureFilePath;

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public String getRefTextureFilePath() {
		return refTextureFilePath;
	}

	public void setRefTextureFilePath(String refTextureFilePath) {
		this.refTextureFilePath = refTextureFilePath;
	}

	@Override
	public String toString() {
		return "AssetItemVO [imageFilePath=" + imageFilePath + ", refTextureFilePath="
				+ refTextureFilePath + "]";
	}

}
