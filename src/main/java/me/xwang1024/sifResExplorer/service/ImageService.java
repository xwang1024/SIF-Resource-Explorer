package me.xwang1024.sifResExplorer.service;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.xwang1024.sifResExplorer.data.CardDao;
import me.xwang1024.sifResExplorer.data.ImagDao;
import me.xwang1024.sifResExplorer.data.UnitDao;
import me.xwang1024.sifResExplorer.data.impl.CardDaoImpl;
import me.xwang1024.sifResExplorer.data.impl.ImagDaoImpl;
import me.xwang1024.sifResExplorer.data.impl.UnitDaoImpl;
import me.xwang1024.sifResExplorer.dto.CardBaseDTO;
import me.xwang1024.sifResExplorer.dto.CardDTO;
import me.xwang1024.sifResExplorer.dto.UnitDTO;
import me.xwang1024.sifResExplorer.model.CardImage;
import me.xwang1024.sifResExplorer.model.CardImage.LayerStatus;
import me.xwang1024.sifResExplorer.util.ImageUtil;

public class ImageService {
	public static final int CARD_WIDTH = 512;
	public static final int CARD_HEIGHT = 720;
	private static final String AVATAR_ASSETS_ROOT = "assets/image/ui/common/";
	private static final String[] AVATAR_BACKGROUND = { "", "com_win_19.png", "com_win_20.png",
			"com_win_18.png", "", "com_win_21.png" };
	private static final String[] AVATAR_STAR = { "", "com_icon_05.png", "com_icon_06.png",
			"com_icon_07.png", "com_icon_08.png", "com_icon_09.png", "com_icon_10.png",
			"com_icon_11.png", "com_icon_12.png" };

	private ImagDao imagDao;
	private UnitDao unitDao;
	private CardDao cardDao;

	public ImageService() throws ClassNotFoundException, FileNotFoundException, SQLException {
		imagDao = new ImagDaoImpl();
		unitDao = new UnitDaoImpl();
		cardDao = new CardDaoImpl();
	}

	public BufferedImage getImage(String imagPath) throws IOException {
		return imagDao.getImage(imagPath);
	}

	private BufferedImage getAvatar(int id, boolean isIdolized, boolean[] layerFlag)
			throws SQLException, IOException {
		UnitDTO unit = unitDao.getUnitById(id);
		String imagPath = isIdolized ? unit.getIdolizeAvatarPath() : unit.getNormalAvatarPath();
		String bkPath = AVATAR_ASSETS_ROOT + AVATAR_BACKGROUND[unit.getAttrId()];
		String starPath = AVATAR_ASSETS_ROOT
				+ AVATAR_STAR[unit.getRarity() * 2 - (isIdolized ? 0 : 1)];
		String[] arr = new String[] { bkPath, imagPath, starPath };
		ArrayList<BufferedImage> l = new ArrayList<BufferedImage>();
		for (int i = 0; i < arr.length; i++) {
			if (layerFlag[i]) {
				BufferedImage img = imagDao.getImage(arr[i]);
				l.add(img);
			}
		}
		if (l.isEmpty()) {
			return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		}
		return ImageUtil.merge(l);
	}

	public BufferedImage getNormalAvatar(int id, boolean[] layerFlag) throws SQLException,
			IOException {
		return getAvatar(id, false, layerFlag);
	}

	public BufferedImage getIdolizedAvatar(int id, boolean[] layerFlag) throws SQLException,
			IOException {
		return getAvatar(id, true, layerFlag);
	}

	private CardImage getCard(int cardId, boolean[] layerFlag) throws SQLException, IOException {
		String[] imagePathArr = new String[8];
		CardDTO card = cardDao.getCard(cardId);
		// ------ Set navi layer ------
		int naviIndex = card.getNaviLayerOrder() - 1;
		imagePathArr[naviIndex] = unitDao.getUnitNaviAsset(card.getUnitNaviAssetId());
		// ----------------------------
		List<CardBaseDTO> baseList = cardDao.getCardBase(card.getCardBaseId());
		for (CardBaseDTO base : baseList) {
			imagePathArr[base.getLayerOrder() - 1] = cardDao.getCardLayer(base.getCardLayerId())
					.getCardLayerAsset();
		}
		CardImage image = new CardImage();
		ArrayList<BufferedImage> list = new ArrayList<BufferedImage>(8);
		for (int i = 0; i < imagePathArr.length; i++) {
			if (layerFlag[i]) {
				if (imagePathArr[i] == null) {
					image.setLayerStatus(i, LayerStatus.DISABLED);
				} else {
					image.setLayerStatus(i, LayerStatus.ON);
					if (i == naviIndex) {
						String texbPath = imagDao.getRefTextureFilePath(imagePathArr[i]);
						BufferedImage img = imagDao.getImageWithoutSplit(texbPath);
						img = ImageUtil.resize(img, card.getNaviSizeRatio());
						img = ImageUtil.move(img, card.getNaviMoveX(), card.getNaviMoveY());
						list.add(img);
					} else {
						BufferedImage img = imagDao.getImage(imagePathArr[i]);
						list.add(img);
					}
				}
			} else {
				image.setLayerStatus(i, LayerStatus.OFF);
			}
		}
		list.add(0, new BufferedImage(512, 720, BufferedImage.TYPE_INT_ARGB));
		BufferedImage cardImage = ImageUtil.merge(list);
		image.setImage(cardImage);
		return image;
	}

	public CardImage getNormalCard(int id, boolean[] layerFlag) throws SQLException, IOException {
		UnitDTO unit = unitDao.getUnitById(id);
		return getCard(unit.getNormalCardId(), layerFlag);
	}

	public CardImage getIdolizedCard(int id, boolean isLevelMax, boolean isBondMax,
			boolean[] layerFlag) throws SQLException, IOException {
		UnitDTO unit = unitDao.getUnitById(id);
		if (!isLevelMax && !isBondMax) {
			return getCard(unit.getIdolizeCardId(), layerFlag);
		}
		if (isLevelMax && isBondMax) {
			return getCard(unit.getAllMaxCardId(), layerFlag);
		}
		if (isLevelMax) {
			return getCard(unit.getMaxLevelCardId(), layerFlag);
		}
		if (isBondMax) {
			return getCard(unit.getMaxBondCardId(), layerFlag);
		}
		return null;
	}

	public BufferedImage getNormalCG(int id) throws SQLException, IOException {
		UnitDTO unit = unitDao.getUnitById(id);
		String texbPath = imagDao.getRefTextureFilePath(unitDao.getUnitNaviAsset(unit.getNormalCGAssetId()));
		BufferedImage img = imagDao.getImageWithoutSplit(texbPath);
		return img;
	}

	public BufferedImage getIdolizedCG(int id) throws SQLException, IOException {
		UnitDTO unit = unitDao.getUnitById(id);
		String texbPath = imagDao.getRefTextureFilePath(unitDao.getUnitNaviAsset(unit.getIdolizeCGAssetId()));
		BufferedImage img = imagDao.getImageWithoutSplit(texbPath);
		return img;
	}
}
