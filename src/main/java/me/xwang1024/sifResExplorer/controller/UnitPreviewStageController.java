package me.xwang1024.sifResExplorer.controller;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import me.xwang1024.sifResExplorer.model.CardImage;
import me.xwang1024.sifResExplorer.service.ImageService;

public class UnitPreviewStageController {
	private final ImageService imageService;

	@FXML
	private Label nameLb;
	@FXML
	private Label idLb;
	@FXML
	private Label staminaLb;
	@FXML
	private Label smileLb;
	@FXML
	private Label pureLb;
	@FXML
	private Label coolLb;

	@FXML
	private CheckBox bkBox;
	@FXML
	private CheckBox bgBox;
	@FXML
	private CheckBox frameBox;
	@FXML
	private CheckBox avatarBox;
	@FXML
	private CheckBox starBox;

	@FXML
	private CheckBox layer1Box;
	@FXML
	private CheckBox layer2Box;
	@FXML
	private CheckBox layer3Box;
	@FXML
	private CheckBox layer4Box;
	@FXML
	private CheckBox layer5Box;
	@FXML
	private CheckBox layer6Box;
	@FXML
	private CheckBox layer7Box;
	@FXML
	private CheckBox layer8Box;

	private CheckBox[] layerBox = new CheckBox[8];

	@FXML
	private ImageView normalAvatarImage;
	@FXML
	private ImageView idolizeAvatarImage;
	@FXML
	private ImageView normalCardImage;
	@FXML
	private ImageView idolizeCardImage;
	@FXML
	private ImageView normalCGImage;
	@FXML
	private ImageView idolizeCGImage;



	public UnitPreviewStageController() throws ClassNotFoundException, FileNotFoundException, SQLException {
		imageService = new ImageService();

	}

	@FXML
	public void onUpdateAvatarAction(ActionEvent event) throws Exception {
		int unitId = Integer.parseInt(idLb.getText());
		boolean[] layerFlag = new boolean[]{bkBox.isSelected(),avatarBox.isSelected(),starBox.isSelected()};
		BufferedImage normal = imageService.getNormalAvatar(unitId, layerFlag);
		WritableImage normalImage = SwingFXUtils.toFXImage(normal, null);
		normalAvatarImage.setImage(normalImage);
		BufferedImage idolize = imageService.getIdolizedAvatar(unitId, layerFlag);
		WritableImage idolizeImage = SwingFXUtils.toFXImage(idolize, null);
		idolizeAvatarImage.setImage(idolizeImage);
	}

	@FXML
	public void onUpdateCardAction(ActionEvent event) throws Exception {
		layerBox[0] = layer1Box;
		layerBox[1] = layer2Box;
		layerBox[2] = layer3Box;
		layerBox[3] = layer4Box;
		layerBox[4] = layer5Box;
		layerBox[5] = layer6Box;
		layerBox[6] = layer7Box;
		layerBox[7] = layer8Box;

		int unitId = Integer.parseInt(idLb.getText());
		boolean[] layerFlag = new boolean[8];
		for(int i = 0; i < layerFlag.length; i++) {
			layerFlag[i] = layerBox[i].isSelected();
		}
		CardImage normalCard = imageService.getNormalCard(unitId, layerFlag);
		CardImage idolizeCard = imageService.getIdolizedCard(unitId, true, true, layerFlag);
		WritableImage normalImage = SwingFXUtils.toFXImage(normalCard.getImage(), null);
		WritableImage idolizeImage = SwingFXUtils.toFXImage(idolizeCard.getImage(), null);
		normalCardImage.setImage(normalImage);
		idolizeCardImage.setImage(idolizeImage);
	}
}
