package me.xwang1024.sifResExplorer.data;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface ImagDao {
	public BufferedImage getImage(String imagPath) throws IOException;
	
	public List<String> getImagList();
}
