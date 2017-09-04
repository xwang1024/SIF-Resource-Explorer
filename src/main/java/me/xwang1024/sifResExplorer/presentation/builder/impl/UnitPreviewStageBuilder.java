package me.xwang1024.sifResExplorer.presentation.builder.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import me.xwang1024.sifResExplorer.model.CardImage;
import me.xwang1024.sifResExplorer.model.CardImage.LayerStatus;
import me.xwang1024.sifResExplorer.model.Unit;
import me.xwang1024.sifResExplorer.presentation.builder.AbsStageBuilder;
import me.xwang1024.sifResExplorer.presentation.builder.impl.UnitsBoxBuilder.UnitLine;
import me.xwang1024.sifResExplorer.service.ImageService;
import me.xwang1024.sifResExplorer.service.UnitService;

public class UnitPreviewStageBuilder extends AbsStageBuilder {
	private final UnitService unitService;
	private final ImageService imageService;
	private Unit unit;
	private Stage stage;

	// ------ Elements ------
	private Label nameLb;
	private Label idLb;
	private Label staminaLb;
	private Label smileLb;
	private Label pureLb;
	private Label coolLb;

	private CheckBox bkBox;
	private CheckBox bgBox;
	private CheckBox avatarBox;
	private CheckBox frameBox;
	private CheckBox starBox;

	private CheckBox[] layerBox = new CheckBox[9];

	private StackPane normalCardPane;
	private StackPane idolizeCardPane;

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

	public UnitPreviewStageBuilder(FXMLLoader loader, Stage stage) throws Exception {
		super(loader);
		this.stage = stage;
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
		bgBox = (CheckBox) loader.getNamespace().get("bgBox");
		avatarBox = (CheckBox) loader.getNamespace().get("avatarBox");
		frameBox = (CheckBox) loader.getNamespace().get("frameBox");
		starBox = (CheckBox) loader.getNamespace().get("starBox");

		for (int i = 0; i < layerBox.length; i++) {
			layerBox[i] = (CheckBox) loader.getNamespace().get("layer" + (i + 1) + "Box");
		}

		normalCardPane = (StackPane) loader.getNamespace().get("normalCardPane");
		idolizeCardPane = (StackPane) loader.getNamespace().get("idolizeCardPane");

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
		boolean[] layerFlag = new boolean[] { bkBox.isSelected(), bgBox.isSelected(), avatarBox.isSelected(),
				frameBox.isSelected(), starBox.isSelected() };
		BufferedImage normal = imageService.getNormalAvatar(unit.getId(), layerFlag);
		BufferedImage idolize = imageService.getIdolizedAvatar(unit.getId(), layerFlag);
		WritableImage normalImage = SwingFXUtils.toFXImage(normal, null);
		WritableImage idolizeImage = SwingFXUtils.toFXImage(idolize, null);
		normalAvatarImage.setImage(normalImage);
		idolizeAvatarImage.setImage(idolizeImage);
	}

	private void resizeCardImage(int stageW, int stageH) {
		int w = (stageW - 100) / 2 - 10;
		int h = (stageH - 120);
		int resW = 0;
		int resH = 0;
		if (w >= ImageService.CARD_WIDTH && h >= ImageService.CARD_HEIGHT) {
			resW = ImageService.CARD_WIDTH;
			resH = ImageService.CARD_HEIGHT;
		} else {
			double d1 = ((double) w) / ((double) h);
			double d2 = ((double) ImageService.CARD_WIDTH) / ((double) ImageService.CARD_HEIGHT);
			if (d1 < d2) {
				resW = w;
				resH = w * ImageService.CARD_HEIGHT / ImageService.CARD_WIDTH;
			} else {
				resH = h;
				resW = h * ImageService.CARD_WIDTH / ImageService.CARD_HEIGHT;
			}
		}
		normalCardImage.setFitWidth(resW);
		normalCardImage.setFitHeight(resH);
		idolizeCardImage.setFitWidth(resW);
		idolizeCardImage.setFitHeight(resH);
	}

	private void resizeCGImage(int stageW, int stageH) {
		int w = (stageW - 10) / 2;
		int h = (stageH - 120);
		int resW = 0;
		int resH = 0;
		if (w >= ImageService.CARD_WIDTH && h >= ImageService.CARD_HEIGHT) {
			resW = ImageService.CARD_WIDTH;
			resH = ImageService.CARD_HEIGHT;
		} else {
			double d1 = ((double) w) / ((double) h);
			double d2 = ((double) ImageService.CARD_WIDTH) / ((double) ImageService.CARD_HEIGHT);
			if (d1 < d2) {
				resW = w;
				resH = w * ImageService.CARD_HEIGHT / ImageService.CARD_WIDTH;
			} else {
				resH = h;
				resW = h * ImageService.CARD_WIDTH / ImageService.CARD_HEIGHT;
			}
		}
		normalCGImage.setFitWidth(resW);
		normalCGImage.setFitHeight(resH);
		idolizeCGImage.setFitWidth(resW);
		idolizeCGImage.setFitHeight(resH);
	}

	private void initStackPane() throws SQLException, IOException {
		stage.heightProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue,
					Number newValue) {
				resizeCardImage((int) stage.getWidth(), newValue.intValue());
				resizeCGImage((int) stage.getWidth(), newValue.intValue());
			}
		});
		stage.widthProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue,
					Number newValue) {
				resizeCardImage(newValue.intValue(), (int) stage.getHeight());
				resizeCGImage((int) stage.getWidth(), newValue.intValue());
			}
		});
	}

	private void initCard() throws SQLException, IOException {
		boolean[] layerFlag = new boolean[9];
		for (int i = 0; i < layerFlag.length; i++) {
			layerFlag[i] = layerBox[i].isSelected();
		}
		CardImage normalCard = imageService.getNormalCard(unit.getId(), layerFlag);
		CardImage idolizeCard = imageService.getIdolizedCard(unit.getId(), layerFlag);
		// ------ Init layer box ------
		LayerStatus[] normalStatus = normalCard.getLayerStatus();
		LayerStatus[] idolizeStatus = idolizeCard.getLayerStatus();
		for (int i = 0; i < layerFlag.length; i++) {
			if (normalStatus[i] == LayerStatus.DISABLED && idolizeStatus[i] == LayerStatus.DISABLED) {
				layerBox[i].setDisable(true);
			}
		}
		// ----------------------------
		WritableImage normalImage = SwingFXUtils.toFXImage(normalCard.getImage(), null);
		WritableImage idolizeImage = SwingFXUtils.toFXImage(idolizeCard.getImage(), null);
		normalCardImage.setImage(normalImage);
		idolizeCardImage.setImage(idolizeImage);
	}

	private void initCG() throws SQLException, IOException {
		BufferedImage normal = imageService.getNormalCG(unit.getId());
		BufferedImage idolize = imageService.getIdolizedCG(unit.getId());
		WritableImage normalImage = SwingFXUtils.toFXImage(normal, null);
		WritableImage idolizeImage = SwingFXUtils.toFXImage(idolize, null);
		normalCGImage.setImage(normalImage);
		idolizeCGImage.setImage(idolizeImage);
	}

	@Override
	public void build() throws Exception {
		if (unit != null) {
			initElement();
			initLabel();
			initAvatar();
			initStackPane();
			resizeCardImage((int) stage.getWidth(), (int) stage.getHeight());
			resizeCGImage((int) stage.getWidth(), (int) stage.getHeight());
			initCard();
			initCG();
		}
	}

	public void setUnit(UnitLine unitLine) {
		unit = unitService.getUnitById(unitLine.getId());
		System.out.println(unit);
	}
}
