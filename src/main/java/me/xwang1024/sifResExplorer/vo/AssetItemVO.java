package me.xwang1024.sifResExplorer.vo;

import java.util.Arrays;

public class AssetItemVO {
	private String[] pathTree;

	private String imagePath;
	private String refTexturepath;

	public String[] getPathTree() {
		return pathTree;
	}

	public void setPathTree(String[] pathTree) {
		this.pathTree = pathTree;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getRefTexturepath() {
		return refTexturepath;
	}

	public void setRefTexturepath(String refTexturepath) {
		this.refTexturepath = refTexturepath;
	}

	@Override
	public String toString() {
		return "AssetItemVO [pathTree=" + Arrays.toString(pathTree) + ", imagePath=" + imagePath
				+ ", refTexturepath=" + refTexturepath + "]";
	}

}
