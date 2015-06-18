package me.xwang1024.sifResExplorer.presentation.builder.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import me.xwang1024.sifResExplorer.model.Unit;
import me.xwang1024.sifResExplorer.presentation.builder.AbsStageBuilder;
import me.xwang1024.sifResExplorer.presentation.builder.impl.UnitsBoxBuilder.UnitLine;
import me.xwang1024.sifResExplorer.service.ImageService;
import me.xwang1024.sifResExplorer.service.UnitService;

public class UnitPreviewStageBuilder extends AbsStageBuilder {
	private final UnitService unitService;
	private final ImageService imageService;
	private Unit unit;

	// ------ Elements ------
	private Label nameLb;
	private Label idLb;
	private Label staminaLb;
	private Label smileLb;
	private Label pureLb;
	private Label coolLb;

	private CheckBox bkBox;
	private CheckBox avatarBox;
	private CheckBox starBox;

	private CheckBox[] layerBox = new CheckBox[7];

	private ImageView normalAvatarImage;
	private ImageView idolizeAvatarImage;
	private ImageView normalCardImage;
	private ImageView idolizeCardImage;
	private ImageView normalCGImage;
	private ImageView idolizeCGImage;
	// ----------------------

	public UnitPreviewStageBuilder(FXMLLoader loader) throws Exception {
		super(loader);
		unitService = UnitService.getInstance();
		imageService = new ImageService();
	}

	private void initElement() {
		nameLb = (Label) loader.getNamespace().get("nameLb");
		idLb = (Label) loader.getNamespace().get("idLb");
		staminaLb = (Label) loader.getNamespace().get("staminaLb");
		smileLb = (Label) loader.getNamespace().get("smileLb");
		pureLb = (Label) loader.getNamespace().get("pureLb");
		coolLb = (Label) loader.getNamespace().get("coolLb");

		bkBox = (CheckBox) loader.getNamespace().get("bkBox");
		avatarBox = (CheckBox) loader.getNamespace().get("avatarBox");
		starBox = (CheckBox) loader.getNamespace().get("starBox");

		for (int i = 0; i < layerBox.length; i++) {
			layerBox[i] = (CheckBox) loader.getNamespace().get("layer" + (i + 1) + "Box");
		}

		normalAvatarImage = (ImageView) loader.getNamespace().get("normalAvatarImage");
		idolizeAvatarImage = (ImageView) loader.getNamespace().get("idolizeAvatarImage");
		normalCardImage = (ImageView) loader.getNamespace().get("normalCardImage");
		idolizeCardImage = (ImageView) loader.getNamespace().get("idolizeCardImage");
		normalCGImage = (ImageView) loader.getNamespace().get("normalCGImage");
		idolizeCGImage = (ImageView) loader.getNamespace().get("idolizeCGImage");
	}

	private void initLabel() {
		String frameName = unit.getEponym();
		if (frameName.startsWith("<")) {
			frameName = unit.getName();
		} else {
			frameName += " - " + unit.getName();
		}
		nameLb.setText(frameName);
		idLb.setText(unit.getId() + "");
		staminaLb.setText(unit.getStamina() + "");
		smileLb.setText(unit.getSmile() + "");
		pureLb.setText(unit.getPure() + "");
		coolLb.setText(unit.getCool() + "");
	}
	
	private void initAvatar() throws SQLException, IOException {
		boolean[] layerFlag = new boolean[]{bkBox.isSelected(),avatarBox.isSelected(),starBox.isSelected()};
		BufferedImage normal = imageService.getNormalAvatar(unit.getId(), layerFlag);
		WritableImage normalImage = SwingFXUtils.toFXImage(normal, null);
		normalAvatarImage.setImage(normalImage);
		BufferedImage idolize = imageService.getIdolizedAvatar(unit.getId(), layerFlag);
		WritableImage idolizeImage = SwingFXUtils.toFXImage(idolize, null);
		idolizeAvatarImage.setImage(idolizeImage);
	}

	@Override
	public void build() throws Exception {
		if (unit != null) {
			initElement();
			initLabel();
			initAvatar();
		}
	}

	public void setUnit(UnitLine unitLine) {
		unit = unitService.getUnitById(unitLine.getId());
		System.out.println(unit);
	}
}
