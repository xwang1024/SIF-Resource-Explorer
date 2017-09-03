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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageService {
	private static final Logger logger = LoggerFactory.getLogger(ImageService.class);
	public static final int CARD_WIDTH = 512;
	public static final int CARD_HEIGHT = 720;
	private static final String AVATAR_ASSETS_ROOT = "assets/image/ui/common/";
	private static final String[] AVATAR_BACKGROUND = { "", "com_win_19.png", "com_win_20.png",
			"com_win_18.png", "", "com_win_21.png" };
	private static final String[] AVATAR_RARITY_SMILE = { "", "com_icon_05.png", "com_icon_06.png", "com_icon_07.png",
			"com_icon_09.png", "com_icon_08.png"};
	private static final String[] AVATAR_RARITY_PURE = { "", "com_icon_10.png", "com_icon_11.png", "com_icon_12.png",
			"com_icon_55.png", "com_icon_54.png"};
	private static final String[] AVATAR_RARITY_COOL = { "", "com_icon_56.png", "com_icon_57.png", "com_icon_58.png",
			"com_icon_60.png", "com_icon_60.png"};
	private static final String[] AVATAR_RARITY_ALL = { "", "com_icon_61.png", "com_icon_62.png", "com_icon_63.png",
			"com_icon_65.png", "com_icon_64.png"};
	private static final String[][] AVATAR_RARITY = {{}, AVATAR_RARITY_SMILE, AVATAR_RARITY_PURE, AVATAR_RARITY_COOL, {}, AVATAR_RARITY_ALL};

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
				+ AVATAR_RARITY[unit.getAttrId()][unit.getRarity()];
		String bgPath = getIconBackgroundName(unit.getRarity(), unit.getAttrId(), isIdolized);
		String framePath = getIconFrameName(unit.getRarity(), unit.getAttrId());
		String[] arr = new String[] { bkPath, bgPath, imagPath, framePath, starPath };
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

	private String getIconBackgroundName(int rarity, int attribute, boolean idolized){
		String result = "";
		String rarity_prefix = "";
		String attribute_prefix = "";
		String idolized_prefix = "";
		switch(rarity){
		case 1:
			rarity_prefix = "N";break;
		case 2:
			rarity_prefix = "R";break;
		case 3:
			rarity_prefix = "SR";break;
		case 4:
			rarity_prefix = "UR";break;
		case 5:
			rarity_prefix = "SSR";break;
		}
		switch(attribute){
		case 1:
			attribute_prefix = "smile";break;
		case 2:
			attribute_prefix = "pure";break;
		case 3:
			attribute_prefix = "cool";break;
		case 5:
			attribute_prefix = "all";break;
		}
		if(idolized){
			idolized_prefix = "001";
		}else{
			idolized_prefix = "002";
		}
		if(attribute == 5){
			idolized_prefix = "001";
		}
		result = "assets/image/cards/icon/b_" + attribute_prefix + "_" + rarity_prefix + "_" + idolized_prefix + ".png.imag";
		return result;
	}

	private String getIconFrameName(int rarity, int attribute){
		String result = "";
		String rarity_prefix = "";
		String attribute_prefix = "";
		switch(rarity){
		case 1:
			rarity_prefix = "N";break;
		case 2:
			rarity_prefix = "R";break;
		case 3:
			rarity_prefix = "SR";break;
		case 4:
			rarity_prefix = "UR";break;
		case 5:
			rarity_prefix = "SSR";break;
		}
		switch(attribute){
		case 1:
			attribute_prefix = "1";break;
		case 2:
			attribute_prefix = "2";break;
		case 3:
			attribute_prefix = "3";break;
		case 5:
			attribute_prefix = "9";break;
		}

		result = "assets/image/cards/icon/f_" + rarity_prefix + "_" + attribute_prefix + ".png.imag";
		return result;
	}

	private String getBackgroundName(int rarity, int attribute, int idolized){
		String result = "";
		String rarity_prefix = "";
		String attribute_prefix = "";
		String idolized_prefix = "";
		switch(rarity){
		case 1:
			rarity_prefix = "N";break;
		case 2:
			rarity_prefix = "R";break;
		case 3:
			rarity_prefix = "SR";break;
		}
		switch(attribute){
		case 1:
			attribute_prefix = "smile";break;
		case 2:
			attribute_prefix = "pure";break;
		case 3:
			attribute_prefix = "cool";break;
		case 5:
			attribute_prefix = "all";break;
		}
		switch(idolized){
		case 0:
			idolized_prefix = "001";break;
		case 1:
			idolized_prefix = "002";break;
		}
		if(attribute == 5){
			idolized_prefix = "001";
		}
		result = "assets/image/cards/background/b_" + attribute_prefix + "_" + rarity_prefix + "_" + idolized_prefix + ".png.imag";
		return result;
	}

	private String getFrameName(int rarity, int attribute){
		String result = "";
		String rarity_prefix = "";
		String attribute_prefix = "";
		switch(rarity){
		case 1:
			rarity_prefix = "N";break;
		case 2:
			rarity_prefix = "R";break;
		case 3:
			rarity_prefix = "SR";break;
		case 4:
			rarity_prefix = "UR";break;
		case 5:
			rarity_prefix = "SSR";break;
		}
		switch(attribute){
		case 1:
			attribute_prefix = "1";break;
		case 2:
			attribute_prefix = "2";break;
		case 3:
			attribute_prefix = "3";break;
		case 5:
			attribute_prefix = "9";break;
		}
		if(rarity >= 3){
			attribute_prefix = "4";
		}
		result = "assets/image/cards/frame/f_" + rarity_prefix + "_" + attribute_prefix + ".png.imag";
		return result;
	}

	private String getLvName(int attribute, boolean is_lv_max){
		String result = "";
		String attribute_prefix = "";
		String lv_prefix = "";
		switch(attribute){
		case 1:
			attribute_prefix = "smile";break;
		case 2:
			attribute_prefix = "pure";break;
		case 3:
			attribute_prefix = "cool";break;
		}

		if(is_lv_max){
			lv_prefix = "on";
		}else{
			lv_prefix = "off";
		}

		result = "assets/image/cards/lv/v_" + attribute_prefix + "_" + lv_prefix + ".png.imag";
		return result;
	}

	private String getBondName(int attribute, boolean is_bound_max){
		String result = "";
		String attribute_prefix = "";
		String lv_prefix = "";
		switch(attribute){
		case 1:
			attribute_prefix = "smile";break;
		case 2:
			attribute_prefix = "pure";break;
		case 3:
			attribute_prefix = "cool";break;
		}

		if(is_bound_max){
			lv_prefix = "on";
		}else{
			lv_prefix = "off";
		}

		result = "assets/image/cards/love/o_" + attribute_prefix + "_" + lv_prefix + ".png.imag";
		return result;
	}

	private String getRarityName(int rarity, int attribute){
		String result = "";
		String rarity_prefix = "";
		String attribute_prefix = "";
		switch(rarity){
		case 1:
			rarity_prefix = "N";break;
		case 2:
			rarity_prefix = "R";break;
		case 3:
			rarity_prefix = "SR";break;
		case 4:
			rarity_prefix = "UR";break;
		case 5:
			rarity_prefix = "SSR";break;
		}
		switch(attribute){
		case 1:
			attribute_prefix = "smile";break;
		case 2:
			attribute_prefix = "pure";break;
		case 3:
			attribute_prefix = "cool";break;
		case 5:
			attribute_prefix = "all";break;
		}
		result = "assets/image/cards/rarity/r_" + attribute_prefix + "_" + rarity_prefix + ".png.imag";
		return result;
	}

	private String getStarName(int rarity, boolean idolized){
		String result = "";
		int star_count = 0;
		switch(rarity){
		case 1:star_count = 1;break;
		case 2:star_count = 3;break;
		case 3:star_count = 5;break;
		case 4:star_count = 9;break;
		case 5:star_count = 7;break;
		}
		if(idolized)
			star_count += 1;
		String star_count_ = String.valueOf(star_count);
		if(star_count < 10)
			star_count_ = "0" + star_count_;
		result = "assets/image/cards/star/s_0" + star_count_ + ".png.imag";
		return result;
	}

	private CardImage getCard(int cardId, boolean[] layerFlag, int id, int idolized) throws SQLException, IOException {
		CardImage image = new CardImage();
		ArrayList<BufferedImage> list = new ArrayList<BufferedImage>(9);
		UnitDTO unit = unitDao.getUnitById(id);
		CardDTO card = cardDao.getCard(cardId);
		logger.info("Got card " + cardId);
		String background;
		BufferedImage img;

		if(layerFlag[0]){
			//卡片背景

			if(unit.getRarity() < 4){
				background = getBackgroundName(unit.getRarity(), unit.getAttrId(), idolized);
			}else{
				background = card.getFlashAsset();
			}
			image.setLayerStatus(0, LayerStatus.ON);
			img = imagDao.getImage(background);
			list.add(img);
		}

		//立绘

		if(unit.getRarity() >= 4){
			image.setLayerStatus(1, LayerStatus.DISABLED);
		}else if(layerFlag[1]){
			image.setLayerStatus(1, LayerStatus.ON);
			String texbPath = imagDao.getRefTextureFilePath(unitDao.getUnitNaviAsset(card.getUnitNaviAssetId()));
			img = imagDao.getImageWithoutSplit(texbPath);
			img = ImageUtil.resize(img, card.getNaviSizeRatio());
			img = ImageUtil.move(img, card.getNaviMoveX(), card.getNaviMoveY());
			list.add(img);
		}

		if(layerFlag[2]){
			//框
			image.setLayerStatus(2, LayerStatus.ON);
			img = imagDao.getImage(getFrameName(unit.getRarity(), unit.getAttrId()));
			list.add(img);
		}

		if(layerFlag[3]){
			//稀有度（左上角的UR和右下角的圈）
			image.setLayerStatus(3, LayerStatus.ON);
			img = imagDao.getImage(getRarityName(unit.getRarity(), unit.getAttrId()));
			list.add(img);
		}

		image.setLayerStatus(4, LayerStatus.DISABLED);
		image.setLayerStatus(5, LayerStatus.DISABLED);
		/*if(unit.getAttrId() <= 3){ //全属性的没有这两个图层
			//右下角LV满
			image.setLayerStatus(4, LayerStatus.ON);
			img = imagDao.getImage(getLvName(unit.getAttrId(), isLevelMax));
			list.add(img);

			//右下角绊满
			image.setLayerStatus(5, LayerStatus.ON);
			img = imagDao.getImage(getBondName(unit.getAttrId(), isBondMax));
			list.add(img);
		}*/

		if(layerFlag[6]){
			//左下角星数
			image.setLayerStatus(6, LayerStatus.ON);
			img = imagDao.getImage(getStarName(unit.getRarity(), idolized == 1));
			list.add(img);
		}

		if(layerFlag[7]){
			//名字
			image.setLayerStatus(7, LayerStatus.ON);
			img = imagDao.getImage(unitDao.getUnitNameImgPath(unit.getUnitTypeId()));
			list.add(img);
		}

		if(layerFlag[8] && idolized == 1){
			//觉醒卡牌金色三角
			image.setLayerStatus(8, LayerStatus.ON);
			img = imagDao.getImage("assets/image/cards/evolution/ev_01.png.imag");
			list.add(img);
		}

		list.add(0, new BufferedImage(512, 720, BufferedImage.TYPE_INT_ARGB));
		BufferedImage cardImage = ImageUtil.merge(list);
		image.setImage(cardImage);
		return image;
	}

	public CardImage getNormalCard(int id, boolean[] layerFlag) throws SQLException, IOException {
		UnitDTO unit = unitDao.getUnitById(id);
		return getCard(unit.getNormalCardId(), layerFlag, id, 0);
	}

	public CardImage getIdolizedCard(int id, boolean isLevelMax, boolean isBondMax,
			boolean[] layerFlag) throws SQLException, IOException {
		UnitDTO unit = unitDao.getUnitById(id);
		if (!isLevelMax && !isBondMax) {
			logger.info("Getting Idolized card");
			return getCard(unit.getIdolizeCardId(), layerFlag, id, 1);
		}
		if (isLevelMax && isBondMax) {
			logger.info("Getting All Max card");
			return getCard(unit.getAllMaxCardId(), layerFlag, id, 1);
		}
		if (isLevelMax) {
			logger.info("Getting Level Max card");
			return getCard(unit.getMaxLevelCardId(), layerFlag, id, 1);
		}
		if (isBondMax) {
			logger.info("Getting Bond Max card");
			return getCard(unit.getMaxBondCardId(), layerFlag, id, 1);
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
