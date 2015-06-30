package me.xwang1024.sifResExplorer.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import me.xwang1024.sifResExplorer.model.Unit;
import me.xwang1024.sifResExplorer.presentation.ApplicationContext;
import me.xwang1024.sifResExplorer.presentation.CardsExportDialog;
import me.xwang1024.sifResExplorer.presentation.ProgressStage;
import me.xwang1024.sifResExplorer.presentation.builder.impl.AssetsBoxBuilder.AssetLine;
import me.xwang1024.sifResExplorer.presentation.builder.impl.UnitsBoxBuilder.UnitLine;
import me.xwang1024.sifResExplorer.service.ImageService;
import me.xwang1024.sifResExplorer.service.UnitService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnitsBoxController {
	private static final Logger logger = LoggerFactory.getLogger(UnitsBoxController.class);
	private final UnitService unitService;
	@FXML
	private Label selectStatLb;
	@FXML
	private ComboBox<String> nameBox;
	@FXML
	private ComboBox<String> attrBox;
	@FXML
	private ComboBox<String> rarityBox;
	@FXML
	private ComboBox<String> skillEffectBox;
	@FXML
	private ComboBox<String> skillTriggerBox;
	@FXML
	private ComboBox<String> leaderSkillTypeBox;
	@FXML
	private TextField searchTf;
	@FXML
	private TableView<UnitLine> unitsTable;

	private ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
		public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
			refreshSelectStat();
		}
	};

	public UnitsBoxController() throws ClassNotFoundException, FileNotFoundException, SQLException {
		unitService = UnitService.getInstance();
	}

	private void refreshSelectStat() {
		ObservableList<UnitLine> list = unitsTable.getItems();
		int total = list.size();
		int selected = 0;
		for (UnitLine item : list) {
			if (item.getSelected()) {
				selected++;
			}
		}
		selectStatLb.setText("(" + selected + "/" + total + ")");
	}

	private void updateTableData() {
		List<Unit> l = unitService.getUnitListByConditions(searchTf.getText(), nameBox.getValue(),
				attrBox.getValue(), rarityBox.getValue(), leaderSkillTypeBox.getValue(),
				skillEffectBox.getValue(), skillTriggerBox.getValue());
		final ObservableList<UnitLine> data = FXCollections.observableArrayList();
		for (Unit vo : l) {
			data.add(new UnitLine(false, vo.getId(), vo.getUnitNo(), vo.getName(), vo.getEponym(),
					vo.getAttribute(), vo.getRarity(), vo.getSmile(), vo.getPure(), vo.getCool(),
					vo.getLeaderSkillType(), vo.getSkillName(), vo.getSkillEffect(), vo
							.getSkillTrigger(), listener));
		}
		unitsTable.setItems(data);
		refreshSelectStat();
	}

	@FXML
	public void onUpdateTableAction(ActionEvent event) {
		logger.debug("onSearchAction");
		if (event.getSource() instanceof ComboBox) {
			ComboBox<String> source = (ComboBox<String>) event.getSource();
			if (source.getValue() != null && source.getValue().equals("<All>")) {
				source.setValue(null);
			}
		}
		updateTableData();
		refreshSelectStat();
	}

	@FXML
	public void onClearFilterAction(ActionEvent event) {
		logger.debug("onClearFilterAction");
		nameBox.setValue(null);
		attrBox.setValue(null);
		rarityBox.setValue(null);
		skillEffectBox.setValue(null);
		skillTriggerBox.setValue(null);
		leaderSkillTypeBox.setValue(null);
		updateTableData();
		refreshSelectStat();
	}

	@FXML
	public void onSelectAllAction(ActionEvent event) {
		logger.debug("onSelectAllAction");
		ObservableList<UnitLine> list = unitsTable.getItems();
		for (UnitLine item : list) {
			item.setSelected(true);
		}
	}

	@FXML
	public void onDeselectAllAction(ActionEvent event) {
		logger.debug("onDeselectAllAction");
		ObservableList<UnitLine> list = unitsTable.getItems();
		for (UnitLine item : list) {
			item.setSelected(false);
		}
	}

	@FXML
	public void onExportCardAction(ActionEvent event) {
		logger.debug("onExportCardAction");
		ObservableList<UnitLine> list = unitsTable.getItems();
		final LinkedList<UnitLine> exportList = new LinkedList<UnitLine>();
		for (UnitLine item : list) {
			if (item.getSelected()) {
				logger.debug("Selected:" + item.getId());
				exportList.add(item);
			}
		}
		try {
			new CardsExportDialog((Stage) unitsTable.getScene().getWindow(), exportList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onExportIconAction(ActionEvent event) {
		ObservableList<UnitLine> list = unitsTable.getItems();
		final LinkedList<Integer> exportList = new LinkedList<Integer>();
		for (UnitLine item : list) {
			if (item.getSelected()) {
				logger.debug("Selected:" + item.getId());
				exportList.add(item.getId());
			}
		}
		if(exportList.isEmpty()) {
			return;
		}
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Select a directory to export...");
		chooser.setInitialDirectory(ApplicationContext.tracedFile);
		final File file = chooser.showDialog(ApplicationContext.stageStack.peek().getStage());
		if (file != null) {
			Task<Void> task;
			task = new Task<Void>() {

				protected Void call() throws Exception {
					ImageService imageService = new ImageService();
					List<String> errorList = new ArrayList<String>();
					// init
					updateMessage("0/" + exportList.size()*2);
					updateProgress(0, exportList.size()*2);
					// init dir
					if (!file.exists() || file.isDirectory()) {
						file.mkdirs();
					}

					for (int i = 0; i < exportList.size(); i++) {
						if (isCancelled()) {
							break;
						}
						try {
							BufferedImage img = imageService.getNormalAvatar(exportList.get(i),new boolean[]{true,true,true});
							File exportFile = new File(file, exportList.get(i)+"_icon_normal.png");
							exportFile.getParentFile().mkdirs();
							if (img != null) {
								ImageIO.write(img, "png", exportFile);
							} else {
								errorList.add(exportList.get(i)+"_icon_normal.png");
							}
						} catch (Exception e) {
							errorList.add(exportList.get(i)+"_icon_normal.png");
							e.printStackTrace();
						} finally {
							updateMessage((i*2 + 1) + "/" + exportList.size()*2);
							updateProgress((i*2 + 1), exportList.size()*2);
						}
						
						try {
							BufferedImage img = imageService.getIdolizedAvatar(exportList.get(i),new boolean[]{true,true,true});
							File exportFile = new File(file, exportList.get(i)+"_icon_idolized.png");
							exportFile.getParentFile().mkdirs();
							if (img != null) {
								ImageIO.write(img, "png", exportFile);
							} else {
								errorList.add(exportList.get(i)+"_icon_idolized.png");
							}
						} catch (Exception e) {
							errorList.add(exportList.get(i)+"_icon_idolized.png");
							e.printStackTrace();
						} finally {
							updateMessage((i*2 + 2) + "/" + exportList.size()*2);
							updateProgress((i*2 + 2), exportList.size()*2);
						}
					}

					for (String error : errorList) {
						System.out.println("Error:" + error);
					}
					return null;
				}
			};
			try {
				new ProgressStage(ApplicationContext.stageStack.peek().getStage(), task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void onExportNaviAction(ActionEvent event) {
		ObservableList<UnitLine> list = unitsTable.getItems();
		final LinkedList<Integer> exportList = new LinkedList<Integer>();
		for (UnitLine item : list) {
			if (item.getSelected()) {
				logger.debug("Selected:" + item.getId());
				exportList.add(item.getId());
			}
		}
		if(exportList.isEmpty()) {
			return;
		}
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Select a directory to export...");
		chooser.setInitialDirectory(ApplicationContext.tracedFile);
		final File file = chooser.showDialog(ApplicationContext.stageStack.peek().getStage());
		if (file != null) {
			Task<Void> task;
			task = new Task<Void>() {

				protected Void call() throws Exception {
					ImageService imageService = new ImageService();
					List<String> errorList = new ArrayList<String>();
					// init
					updateMessage("0/" + exportList.size()*2);
					updateProgress(0, exportList.size()*2);
					// init dir
					if (!file.exists() || file.isDirectory()) {
						file.mkdirs();
					}

					for (int i = 0; i < exportList.size(); i++) {
						if (isCancelled()) {
							break;
						}
						try {
							BufferedImage img = imageService.getNormalCG(exportList.get(i));
							File exportFile = new File(file, exportList.get(i)+"_navi_normal.png");
							exportFile.getParentFile().mkdirs();
							if (img != null) {
								ImageIO.write(img, "png", exportFile);
							} else {
								errorList.add(exportList.get(i)+"_navi_normal.png");
							}
						} catch (Exception e) {
							errorList.add(exportList.get(i)+"_navi_normal.png");
							e.printStackTrace();
						} finally {
							updateMessage((i*2 + 1) + "/" + exportList.size()*2);
							updateProgress((i*2 + 1), exportList.size()*2);
						}
						
						try {
							BufferedImage img = imageService.getIdolizedCG(exportList.get(i));
							File exportFile = new File(file, exportList.get(i)+"_navi_idolized.png");
							exportFile.getParentFile().mkdirs();
							if (img != null) {
								ImageIO.write(img, "png", exportFile);
							} else {
								errorList.add(exportList.get(i)+"_navi_idolized.png");
							}
						} catch (Exception e) {
							errorList.add(exportList.get(i)+"_navi_idolized.png");
							e.printStackTrace();
						} finally {
							updateMessage((i*2 + 2) + "/" + exportList.size()*2);
							updateProgress((i*2 + 2), exportList.size()*2);
						}
					}

					for (String error : errorList) {
						System.out.println("Error:" + error);
					}
					return null;
				}
			};
			try {
				new ProgressStage(ApplicationContext.stageStack.peek().getStage(), task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
