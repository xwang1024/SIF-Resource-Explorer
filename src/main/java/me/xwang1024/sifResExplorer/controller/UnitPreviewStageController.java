package me.xwang1024.sifResExplorer.controller;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.sql.SQLException;

import me.xwang1024.sifResExplorer.service.ImageService;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

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
		int id = Integer.parseInt(idLb.getText());
		boolean[] layerFlag = new boolean[]{bkBox.isSelected(),avatarBox.isSelected(),starBox.isSelected()};
		BufferedImage normal = imageService.getNormalAvatar(id, layerFlag);
		WritableImage normalImage = SwingFXUtils.toFXImage(normal, null);
		normalAvatarImage.setImage(normalImage);
		BufferedImage idolize = imageService.getIdolizedAvatar(id, layerFlag);
		WritableImage idolizeImage = SwingFXUtils.toFXImage(idolize, null);
		idolizeAvatarImage.setImage(idolizeImage);
	}

	@FXML
	public void onUpdateCardAction(ActionEvent event) throws Exception {

	}
}
