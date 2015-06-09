package me.xwang1024.sifResExplorer.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImagDao {
	public BufferedImage getImage(String imagPath) throws IOException;
}
