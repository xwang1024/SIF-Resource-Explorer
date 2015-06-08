package me.xwang1024.sifResExplorer.data.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.zip.Inflater;

import javax.imageio.ImageIO;

import me.xwang1024.sifResExplorer.data.ImagDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImagDaoImpl implements ImagDao {
	private static final Logger logger = LoggerFactory.getLogger(ImagDaoImpl.class);

	private String readString(DataInputStream dis, int len) throws IOException {
		byte[] b = new byte[len];
		dis.read(b, 0, len);
		return new String(b);
	}

	public byte[] getBitmap(DataInputStream dis) throws IOException {
		byte[] b = new byte[1024];
		int len = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		while ((len = dis.read(b)) != -1) {
			dos.write(b, 0, len);
		}
		return baos.toByteArray();
	}

	private byte[] decompress(byte[] data) {
		byte[] output = new byte[0];
		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);
		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			output = data;
			e.printStackTrace();
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		decompresser.end();
		return output;
	}

	@Override
	public BufferedImage getImage(File imagFile) throws IOException {
		Imag imag = new Imag();
		DataInputStream dis = new DataInputStream(new FileInputStream(imagFile));
		imag.tag = readString(dis, 4);
		imag.pathLen = dis.readInt();
		imag.refPath = readString(dis, imag.pathLen);
		System.out.println(imag);
		dis.close();
		return null;
	}

	public void readTexb(File texbFile) throws IOException {
		Texb t = new Texb();
		DataInputStream dis = new DataInputStream(new FileInputStream(texbFile));
		t.tag = readString(dis, 4);
		t.size = dis.readInt();
		t.pathLen = dis.readUnsignedShort();
		t.path = readString(dis, t.pathLen).replaceAll("^T", "");
		t.canvasWidth = dis.readUnsignedShort();
		t.canvasHeight = dis.readUnsignedShort();
		dis.skipBytes(1);

		byte attrFlag = dis.readByte();
		t.imgType = Texb.IMG_TYPE[(attrFlag & 0x07)];
		t.isCompressed = (attrFlag & 0x08) != 0;
		t.isMipMap = (attrFlag & 0x10) != 0;
		t.isDoubleBuff = (attrFlag & 0x20) != 0;
		t.pixelType = Texb.PIXEL_TYPE[((attrFlag & 0x00C0) >> 6)];

		t.vertexLen = dis.readUnsignedShort();
		t.indexLen = dis.readUnsignedShort();
		t.imageCnt = dis.readUnsignedShort();
		t.image = new Timg[t.imageCnt];
		for (int i = 0; i < t.imageCnt; i++) {
			Timg img = new Timg();
			img.tag = readString(dis, 4);
			img.size = dis.readUnsignedShort();
			img.pathLen = dis.readUnsignedShort();
			img.path = readString(dis, img.pathLen).replaceAll("^I", "");
			int spFlag = dis.readUnsignedShort();
			if (spFlag != 0xffff) {
				logger.error("Image FFFF align error");
			}
			// System.out.println(spFlag == 0xffff);

			int imgTypeFlag = dis.readUnsignedShort();
			int[] flagArr = { dis.readUnsignedByte(), dis.readUnsignedByte(), dis.readInt() };
			if (flagArr[0] != 0 || flagArr[1] != 0 || flagArr[2] != 1) {
				logger.error("未知的Flag组：({},{},{})", flagArr[0], flagArr[1], flagArr[2]);
				continue;
			}
			if (imgTypeFlag >= 5) {
				imgTypeFlag = dis.readUnsignedByte();
				if (imgTypeFlag == 1) {
					logger.info("该图片是ScrollBar");
					dis.skipBytes(31);
				} else if (imgTypeFlag == 5) {
					logger.error("该图片是Scale9");
					return;
				} else {
					logger.error("该图片的KLB类型未知");
					return;
				}
			} else {
				// logger.info("该图片是一般类型");
				dis.skipBytes(2);
			}
			img.vertexLen = dis.readUnsignedByte();
			img.indexLen = dis.readUnsignedByte();
			img.width = dis.readUnsignedShort();
			img.height = dis.readUnsignedShort();
			img.centerX = dis.readUnsignedShort();
			img.centerY = dis.readUnsignedShort();

			ArrayList<Vertice> vertices = new ArrayList<Vertice>(img.vertexLen);
			for (int j = 0; j < img.vertexLen; j++) {
				Vertice vertice = new Vertice();
				vertice.x = dis.readInt() / 0x10000;
				vertice.y = dis.readInt() / 0x10000;
				vertice.u = dis.readInt() * t.canvasWidth / 0x10000;
				vertice.v = dis.readInt() * t.canvasHeight / 0x10000;
				vertices.add(vertice);
			}
			Collections.sort(vertices, new Comparator<Vertice>() {
				public int compare(Vertice v1, Vertice v2) {
					return (int) ((v1.u + v1.v) - (v2.u + v2.v));
				}
			});
			img.vertices = vertices.toArray(new Vertice[img.vertexLen]);
			img.indices = new int[img.indexLen];
			for (int j = 0; j < img.indexLen; j++) {
				img.indices[j] = dis.readUnsignedByte();
			}
			t.image[i] = img;
		}

		int compressType = dis.readInt();

		byte[] bitmap = getBitmap(dis);
		if (t.isCompressed) {
			if (compressType != 0) {
				logger.error("未知的压缩格式");
				return;
			}
			bitmap = decompress(bitmap);
		}

		int[] pixelArr = new int[t.canvasWidth * t.canvasHeight];
		if (t.pixelType.equals("4444 RGBA")) {
			int ptr = 0;
			for (int j = 0; j < bitmap.length; j++) {
				if ((bitmap[j] & 0x0F) == 0x0F) {
					pixelArr[ptr] = (0xFF) << 24; // A
				} else {
					pixelArr[ptr] = (bitmap[j] & 0x0F) << 28; // A
					pixelArr[ptr] += (0x09) << 24; // A
				}
				pixelArr[ptr] += (bitmap[j + 1] & 0xF0) << 16; // R
				pixelArr[ptr] += (bitmap[j + 1] & 0x0F) << 12; // G
				pixelArr[ptr] += (bitmap[j] & 0xF0); // B
				j++;
				ptr++;
			}
		} else if (t.pixelType.equals("5551 RGBA")) {
			int ptr = 0;
			for (int j = 0; j < bitmap.length; j++) {
				if ((bitmap[j] & 0x01) == 1) {
					pixelArr[ptr] = (0xFF) << 28; // A
				} else {
					pixelArr[ptr] = (0x00) << 28; // A
				}
				pixelArr[ptr] += (bitmap[j + 1] & 0xF8) << 16; // R
				pixelArr[ptr] += (bitmap[j + 1] & 0x07) << 13; // G
				pixelArr[ptr] += (bitmap[j] & 0xC0) << 5; // G
				pixelArr[ptr] += (bitmap[j] & 0x3E) << 2; // B
				j++;
				ptr++;
			}
		} else if (t.pixelType.equals("565 RGB")) {
			int ptr = 0;
			for (int j = 0; j < bitmap.length; j++) {
				pixelArr[ptr] = (0xFF) << 28; // A
				pixelArr[ptr] += (bitmap[j + 1] & 0xF8) << 16; // R
				pixelArr[ptr] += (bitmap[j + 1] & 0x07) << 13; // G
				pixelArr[ptr] += (bitmap[j] & 0xE0) << 5; // G
				pixelArr[ptr] += (bitmap[j] & 0x1F) << 3; // B
				j++;
				ptr++;
			}
		} else if (t.pixelType.equals("Byte")) {
			int ptr = 0;
			for (int j = 0; j < bitmap.length; j++) {
				pixelArr[ptr] = (bitmap[j + 3] & 0xFF) << 24; // A
				pixelArr[ptr] += (bitmap[j] & 0xFF) << 16; // R
				pixelArr[ptr] += (bitmap[j + 1] & 0xFF) << 8; // G
				pixelArr[ptr] += (bitmap[j + 2] & 0xFF); // B
				j += 3;
				ptr++;
			}
		}
		BufferedImage image = new BufferedImage(t.canvasWidth, t.canvasHeight,
				BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, t.canvasWidth, t.canvasHeight, pixelArr, 0, t.canvasWidth);
		dis.close();
		ImageIO.write(image, "png", new File("test/" + texbFile.getName() + ".png"));
	}

	private static class Vertice {
		int x, y, u, v;

		@Override
		public String toString() {
			return "Vertice [x=" + x + ", y=" + y + ", u=" + u + ", v=" + v + "]";
		}

	}

	private static class Timg {
		String tag;
		int size;
		int pathLen;
		String path;
		int vertexLen;
		int indexLen;
		int width;
		int height;
		int centerX;
		int centerY;
		Vertice[] vertices;
		int[] indices;

		@Override
		public String toString() {
			return "Timg [tag=" + tag + ", size=" + size + ", pathLen=" + pathLen + ", path="
					+ path + ", vertexLen=" + vertexLen + ", indexLen=" + indexLen + ", width="
					+ width + ", height=" + height + ", centerX=" + centerX + ", centerY="
					+ centerY + ", vertices=" + Arrays.toString(vertices) + ", indices="
					+ Arrays.toString(indices) + "]";
		}

	}

	private static class Texb {
		private static String[] IMG_TYPE = { "ALPHA", "LUMA", "LUMALPHA", "RGB", "RGBA" };
		private static String[] PIXEL_TYPE = { "565 RGB", "5551 RGBA", "4444 RGBA", "Byte" };
		String tag;
		int size;
		int pathLen;
		String path;
		int canvasWidth;
		int canvasHeight;
		String imgType;
		boolean isCompressed;
		boolean isMipMap;
		boolean isDoubleBuff;
		String pixelType;
		int vertexLen;
		int indexLen;
		int imageCnt;
		Timg[] image;

		@Override
		public String toString() {
			return "Texb [tag=" + tag + ", size=" + size + ", pathLen=" + pathLen + ", path="
					+ path + ", canvasWidth=" + canvasWidth + ", canvadHeight=" + canvasHeight
					+ ", imgType=" + imgType + ", isCompressed=" + isCompressed + ", isMipMap="
					+ isMipMap + ", isDoubleBuff=" + isDoubleBuff + ", pixelType=" + pixelType
					+ ", vertexLen=" + vertexLen + ", indexLen=" + indexLen + ", imageCnt="
					+ imageCnt + "]";
		}

	}

	private static class Imag {
		String tag;
		int pathLen;
		String refPath;

		@Override
		public String toString() {
			return "Imag [tag=" + tag + ", pathLen=" + pathLen + ", refPath=" + refPath + "]";
		}
	}

	public static void main(String[] args) throws Exception {
		// new ImagDaoImpl().readTexb(new File(
		// "D:/data/Application Support/assets/flash/ui/live/img/tx_live_play.texb"));
		Date d1 = new Date();
		tree(new File("D:/data/Application Support/assets"));
		Date d2 = new Date();
		System.out.println("Total Time: " + (d2.getTime() - d1.getTime()));
	}

	public static void tree(File f) throws IOException {
		if (f.isFile()) {
			if (f.getName().endsWith(".texb")) {
				System.out.println(f.getAbsolutePath());
				Date d1 = new Date();
				new ImagDaoImpl().readTexb(f);
				Date d2 = new Date();
				System.out.println("Time: " + (d2.getTime() - d1.getTime()));
			}
		} else if (f.isDirectory()) {
			if (f.listFiles() != null) {
				for (File child : f.listFiles()) {
					tree(child);
				}
			}
		}
	}
}
