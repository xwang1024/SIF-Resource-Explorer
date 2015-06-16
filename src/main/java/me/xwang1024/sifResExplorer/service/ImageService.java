package me.xwang1024.sifResExplorer.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import me.xwang1024.sifResExplorer.data.ImagDao;
import me.xwang1024.sifResExplorer.data.impl.ImagDaoImpl;

public class ImageService {
	private ImagDao imagDao = new ImagDaoImpl();
	
	public BufferedImage getImage(String imagPath) throws IOException {
		return imagDao.getImage(imagPath);
	}
}
