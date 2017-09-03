package me.xwang1024.sifResExplorer.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import me.xwang1024.sifResExplorer.model.CardImage;
import me.xwang1024.sifResExplorer.presentation.ApplicationContext;
import me.xwang1024.sifResExplorer.presentation.ProgressStage;
import me.xwang1024.sifResExplorer.presentation.builder.impl.UnitsBoxBuilder.UnitLine;
import me.xwang1024.sifResExplorer.service.ImageService;
import me.xwang1024.sifResExplorer.util.ImageUtil;

public class CardsExportConfigDialogController {

	private List<UnitLine> exportList;

	public List<UnitLine> getIdList() {
		return exportList;
	}

	public void setIdList(List<UnitLine> idList) {
		this.exportList = idList;
	}

	@FXML
	private TextField pathField;
	@FXML
	private CheckBox srLayer1;
	@FXML
	private CheckBox srLayer2;
	@FXML
	private CheckBox srLayer3;
	@FXML
	private CheckBox srLayer4;
	@FXML
	private CheckBox srLayer5;
	@FXML
	private CheckBox srLayer6;
	@FXML
	private CheckBox srLayer7;
	@FXML
	private CheckBox srLayer8;
	@FXML
	private CheckBox srLayer9;

	@FXML
	private CheckBox srNormalBox;
	@FXML
	private CheckBox srDoubleMaxBox;

	@FXML
	private CheckBox urLayer1;
	@FXML
	private CheckBox urLayer2;
	@FXML
	private CheckBox urLayer3;
	@FXML
	private CheckBox urLayer4;
	@FXML
	private CheckBox urLayer5;
	@FXML
	private CheckBox urLayer6;
	@FXML
	private CheckBox urLayer7;
	@FXML
	private CheckBox urLayer8;
	@FXML
	private CheckBox urLayer9;

	@FXML
	private CheckBox urNormalBox;
	@FXML
	private CheckBox urDoubleMaxBox;

	@FXML
	public void onSelectPathAction(ActionEvent event) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Select a directory to export...");
		chooser.setInitialDirectory(ApplicationContext.tracedFile);
		final File file = chooser.showDialog(ApplicationContext.stageStack.peek().getStage());
		if (file != null) {
			pathField.setText(file.getAbsolutePath());
		}
	}

	@FXML
	public void onUpdateNCardAction(ActionEvent event) {

	}

	@FXML
	public void onUpdateURCardAction(ActionEvent event) {

	}

	@FXML
	public void onExportAction(ActionEvent event) {
		final boolean[] srFlag = new boolean[] { srLayer1.isSelected(), srLayer2.isSelected(),
				srLayer3.isSelected(), srLayer4.isSelected(), srLayer5.isSelected(),
				srLayer6.isSelected(), srLayer7.isSelected(), srLayer8.isSelected(),
				srLayer9.isSelected()};
		final boolean[] urFlag = new boolean[] { urLayer1.isSelected(), urLayer2.isSelected(),
				urLayer3.isSelected(), urLayer4.isSelected(), urLayer5.isSelected(),
				urLayer6.isSelected(), urLayer7.isSelected(), urLayer8.isSelected(),
				urLayer9.isSelected() };
		if (exportList == null || exportList.isEmpty() || pathField.getText().equals("")) {
			return;
		}
		File file = new File(pathField.getText());
		Task<Void> task;
		task = new Task<Void>() {

			protected Void call() throws Exception {
				ImageService imageService = new ImageService();
				List<String> errorList = new ArrayList<String>();
				// init
				updateMessage("0/" + exportList.size());
				updateProgress(0, exportList.size());
				// init dir
				if (!file.exists() || file.isDirectory()) {
					file.mkdirs();
				}

				for (int i = 0; i < exportList.size(); i++) {
					if (isCancelled()) {
						break;
					}
					UnitLine unit = exportList.get(i);
					if (unit.rarityProperty().get().equals("UR")) {
						if (urNormalBox.isSelected()) {
							try {
								CardImage card = imageService.getNormalCard(unit.getId(), urFlag);
								BufferedImage img = card.getImage();
								File exportFile = new File(file, unit.numProperty().get() + "_card_normal.png");
								exportFile.getParentFile().mkdirs();
								if (img != null) {
									ImageIO.write(img, "png", exportFile);
								} else {
									errorList.add(unit.getId() + "_card_normal.png");
								}
							} catch (Exception e) {
								errorList.add(unit.getId() + "_card_normal.png");
								e.printStackTrace();
							}
						}
						if (urDoubleMaxBox.isSelected()) {
							try {
								CardImage card = imageService.getIdolizedCard(unit.getId(), urFlag);
								BufferedImage img = card.getImage();
								File exportFile = new File(file, unit.numProperty().get()
										+ "_card_doubleMax.png");
								exportFile.getParentFile().mkdirs();
								if (img != null) {
									ImageIO.write(img, "png", exportFile);
								} else {
									errorList.add(unit.getId() + "_card_Idolized.png");
								}
							} catch (Exception e) {
								errorList.add(unit.getId() + "_card_Idolized.png");
								e.printStackTrace();
							}
						}
					} else {
						if (srNormalBox.isSelected()) {
							try {
								CardImage card = imageService.getNormalCard(unit.getId(), srFlag);
								BufferedImage img = card.getImage();
								File exportFile = new File(file, unit.numProperty().get() + "_card_normal.png");
								exportFile.getParentFile().mkdirs();
								if (img != null) {
									ImageIO.write(img, "png", exportFile);
								} else {
									errorList.add(unit.getId() + "_card_normal.png");
								}
							} catch (Exception e) {
								errorList.add(unit.getId() + "_card_normal.png");
								e.printStackTrace();
							}
						}
						if (srDoubleMaxBox.isSelected()) {
							try {
								CardImage card = imageService.getIdolizedCard(unit.getId(), srFlag);
								BufferedImage img = card.getImage();
								File exportFile = new File(file, unit.numProperty().get()
										+ "_card_doubleMax.png");
								exportFile.getParentFile().mkdirs();
								if (img != null) {
									ImageIO.write(img, "png", exportFile);
								} else {
									errorList.add(unit.getId() + "_card_Idolized.png");
								}
							} catch (Exception e) {
								errorList.add(unit.getId() + "_card_Idolized.png");
								e.printStackTrace();
							}
						}
					}

					updateMessage((i + 1) + "/" + exportList.size());
					updateProgress((i + 1), exportList.size());
				}

				for (String error : errorList) {
					System.out.println("Error:" + error);
				}
				return null;
			}
		};
		try {
			new ProgressStage((Stage) ((Node) event.getSource()).getScene().getWindow(), task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onCancelAction(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}
}
