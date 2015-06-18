package me.xwang1024.sifResExplorer.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import javax.imageio.ImageIO;

import me.xwang1024.sifResExplorer.model.AssetItem;
import me.xwang1024.sifResExplorer.presentation.ApplicationContext;
import me.xwang1024.sifResExplorer.presentation.ProgressStage;
import me.xwang1024.sifResExplorer.presentation.builder.impl.AssetsBoxBuilder.AssetLine;
import me.xwang1024.sifResExplorer.service.AssetService;
import me.xwang1024.sifResExplorer.service.AssetService.PathNode;
import me.xwang1024.sifResExplorer.service.ImageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetsBoxController {
	private static final Logger logger = LoggerFactory.getLogger(AssetsBoxController.class);
	private AssetService assetService = AssetService.getInstance();

	@FXML
	private Label selectStatLb;
	@FXML
	private ComboBox<String> pathBox1;
	@FXML
	private Label pathLb1;
	@FXML
	private ComboBox<String> pathBox2;
	@FXML
	private Label pathLb2;
	@FXML
	private ComboBox<String> pathBox3;
	@FXML
	private TextField searchTf;
	@FXML
	private Button exportSelectedBtn;
	@FXML
	private Button selectAllBtn;
	@FXML
	private Button deselectAllBtn;
	@FXML
	private TableView<AssetLine> assetsTable;

	private String[] currentPath = { null, null, null };

	private ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
		public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
			refreshSelectStat();
		}
	};

	private void refreshSelectStat() {
		ObservableList<AssetLine> list = assetsTable.getItems();
		int total = list.size();
		int selected = 0;
		for (AssetLine item : list) {
			if (item.getSelected()) {
				selected++;
			}
		}
		selectStatLb.setText("(" + selected + "/" + total + ")");
	}

	private void updateTableData() {
		List<AssetItem> l = assetService.getAssetListByPath(searchTf.getText(), currentPath);
		final ObservableList<AssetLine> data = FXCollections.observableArrayList();
		for (AssetItem vo : l) {
			data.add(new AssetLine(false, vo.getImageFilePath(), vo.getRefTextureFilePath(),
					listener));
		}
		assetsTable.setItems(data);
		refreshSelectStat();
	}

	@FXML
	public void onPathBox1Action(ActionEvent event) {
		logger.debug("onPathBox1Action");
		String selectedPath = ((ComboBox<String>) event.getSource()).getSelectionModel()
				.getSelectedItem();
		if (selectedPath == null) {
			return;
		}
		if (selectedPath.startsWith("<") && selectedPath.endsWith(">")) {
			pathLb1.setVisible(false);
			pathBox2.setVisible(false);
			pathLb2.setVisible(false);
			pathBox3.setVisible(false);
		} else {
			System.out.println(selectedPath);
			pathLb1.setVisible(true);
			pathBox2.setVisible(true);
			pathLb2.setVisible(false);
			pathBox3.setVisible(false);
			PathNode node = assetService.getRootNode().getChildren().get(selectedPath);
			ObservableList<String> list = FXCollections.observableArrayList(node.getChildren()
					.keySet());
			Collections.sort(list);
			list.add(0, "<All>");
			pathBox2.setItems(list);
			pathBox2.getSelectionModel().select(0);
		}
		if (selectedPath.equals("<All>")) {
			currentPath = new String[] { null, null, null };
		} else {
			currentPath = new String[] { selectedPath, null, null };
		}
		updateTableData();
	}

	@FXML
	public void onPathBox2Action(ActionEvent event) {
		logger.debug("onPathBox2Action");
		String selectedPath = ((ComboBox<String>) event.getSource()).getSelectionModel()
				.getSelectedItem();
		if (selectedPath == null) {
			return;
		}
		if (selectedPath.startsWith("<") && selectedPath.endsWith(">")) {
			pathLb2.setVisible(false);
			pathBox3.setVisible(false);
		} else {
			System.out.println(selectedPath);
			pathLb2.setVisible(true);
			pathBox3.setVisible(true);
			PathNode node = assetService.getRootNode().getChildren().get(pathBox1.getValue())
					.getChildren().get(selectedPath);
			ObservableList<String> list = FXCollections.observableArrayList(node.getChildren()
					.keySet());
			Collections.sort(list);
			list.add(0, "<All>");
			pathBox3.setItems(list);
			pathBox3.getSelectionModel().select(0);
		}

		if (selectedPath.equals("<All>")) {
			currentPath = new String[] { pathBox1.getValue(), null, null };
		} else {
			currentPath = new String[] { pathBox1.getValue(), selectedPath, null };
		}
		updateTableData();
	}

	@FXML
	public void onPathBox3Action(ActionEvent event) {
		logger.debug("onPathBox3Action");
		String selectedPath = ((ComboBox<String>) event.getSource()).getSelectionModel()
				.getSelectedItem();
		if (selectedPath == null) {
			return;
		}
		if (selectedPath.startsWith("<") && selectedPath.endsWith(">")) {
		} else {
			System.out.println(selectedPath);
		}

		if (selectedPath.equals("<All>")) {
			currentPath = new String[] { pathBox1.getValue(), pathBox2.getValue(), null };
		} else {
			currentPath = new String[] { pathBox1.getValue(), pathBox2.getValue(), selectedPath };
		}
		updateTableData();
	}

	@FXML
	public void onSearchAction(ActionEvent event) {
		logger.debug("onSearchAction");
		updateTableData();
	}

	@FXML
	public void onSelectAllAction(ActionEvent event) {
		logger.debug("onSelectAllAction");
		ObservableList<AssetLine> list = assetsTable.getItems();
		for (AssetLine item : list) {
			item.setSelected(true);
		}
	}

	@FXML
	public void onDeselectAllAction(ActionEvent event) {
		logger.debug("onDeselectAllAction");
		ObservableList<AssetLine> list = assetsTable.getItems();
		for (AssetLine item : list) {
			item.setSelected(false);
		}
	}

	@FXML
	public void onExportSelectedAction(ActionEvent event) {
		logger.debug("onExportSelectedAction");
		ObservableList<AssetLine> list = assetsTable.getItems();
		final LinkedList<String> exportList = new LinkedList<String>();
		for (AssetLine item : list) {
			if (item.getSelected()) {
				logger.debug("Selected:" + item.getImagePath());
				exportList.add(item.getImagePath());
			}
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
						String imagPath = exportList.get(i);
						try {
							BufferedImage img = imageService.getImage(imagPath);
							File exportFile = new File(file, imagPath.replaceAll("\\.imag$", ""));
							exportFile.getParentFile().mkdirs();
							if (img != null) {
								ImageIO.write(img, "png", exportFile);
							} else {
								errorList.add(imagPath);
							}
						} catch (Exception e) {
							errorList.add(imagPath);
							e.printStackTrace();
						} finally {
							updateMessage((i + 1) + "/" + exportList.size());
							updateProgress((i + 1), exportList.size());
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
