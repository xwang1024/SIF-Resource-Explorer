package me.xwang1024.sifResExplorer.service;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import me.xwang1024.sifResExplorer.data.ImagDao;
import me.xwang1024.sifResExplorer.data.UnitDao;
import me.xwang1024.sifResExplorer.data.impl.ImagDaoImpl;
import me.xwang1024.sifResExplorer.data.impl.UnitDaoImpl;
import me.xwang1024.sifResExplorer.dto.UnitDTO;
import me.xwang1024.sifResExplorer.util.ImageUtil;

public class ImageService {
	private static final String AVATAR_ASSETS_ROOT = "assets/image/ui/common/";
	private static final String[] AVATAR_BACKGROUND = { "", "com_win_19.png", "com_win_20.png",
			"com_win_18.png", "", "com_win_21.png" };
	private static final String[] AVATAR_STAR = { "", "com_icon_05.png", "com_icon_06.png",
			"com_icon_07.png", "com_icon_08.png", "com_icon_09.png", "com_icon_10.png",
			"com_icon_11.png", "com_icon_12.png" };

	private ImagDao imagDao;
	private UnitDao unitDao;

	public ImageService() throws ClassNotFoundException, FileNotFoundException, SQLException {
		imagDao = new ImagDaoImpl();
		unitDao = new UnitDaoImpl();
	}

	public BufferedImage getImage(String imagPath) throws IOException {
		return imagDao.getImage(imagPath);
	}

	private BufferedImage getAvatar(int id, boolean isIdolized, boolean[] layerFlag)
			throws SQLException, IOException {
		UnitDTO unit = unitDao.getUnitById(id);
		String imagPath = isIdolized ? unit.getIdolizeAvatarPath() : unit.getNormalAvatarPath();
		String bkPath = AVATAR_ASSETS_ROOT + AVATAR_BACKGROUND[unit.getAttrId()];
		String starPath = AVATAR_ASSETS_ROOT + AVATAR_STAR[unit.getRarity() * 2 - (isIdolized ? 0 : 1)];
		String[] arr = new String[] { bkPath, imagPath, starPath };
		ArrayList<BufferedImage> l = new ArrayList<BufferedImage>();
		for(int i = 0; i < arr.length;i++){
			if(layerFlag[i]){
				BufferedImage img = imagDao.getImage(arr[i]);
				l.add(img);
			}
		}
		if(l.isEmpty()) {
			return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		}
		return ImageUtil.merge(l);
	}

	public BufferedImage getNormalAvatar(int id, boolean[] layerFlag) throws SQLException, IOException {
		return getAvatar(id,false,layerFlag);
	}

	public BufferedImage getIdolizedAvatar(int id, boolean[] layerFlag) throws SQLException, IOException {
		return getAvatar(id,true,layerFlag);
	}

	public BufferedImage getNormalCard(int id, boolean[] layerFlag) {
		return null;
	}

	public BufferedImage getIdolizedCard(int id, boolean isRankMax, boolean isBondMax,
			boolean[] layerFlag) {
		return null;
	}

	public BufferedImage getNormalCG(int id) {
		return null;
	}

	public BufferedImage getIdolizedCG(int id) {
		return null;
	}
}
