package me.xwang1024.sifResExplorer.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

public class ImageUtil {
	public static BufferedImage resize(BufferedImage img, double percent) {
		int w = (int) (img.getWidth() * percent);
		int h = (int) (img.getHeight() * percent);
		BufferedImage scaledImage = Scalr.resize(img, Method.QUALITY, w, h);
		return scaledImage;
	}

	public static BufferedImage move(BufferedImage img, int offsetX, int offsetY) {
		int w = img.getWidth() + offsetX;
		int h = img.getHeight() + offsetY;
		BufferedImage newImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = newImg.getGraphics();
		g.drawImage(img, offsetX > 0 ? offsetX : 0, offsetY > 0 ? offsetY : 0,
				w, h, offsetX > 0 ? 0 : (-offsetX), offsetY > 0 ? 0
						: (-offsetY), img.getWidth(), img.getHeight(), null);
		g.dispose();
		return newImg;
	}

	public static BufferedImage merge(ArrayList<BufferedImage> list) {
		// create the new image, canvas size is the max. of both image sizes
		int w = list.get(0).getWidth();
		int h = list.get(0).getHeight();
		BufferedImage combined = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		for (BufferedImage img : list) {
			g.drawImage(img,0,0,w,h,0,0,w,h,null);
		}
		g.dispose();
		return combined;
	}
}
