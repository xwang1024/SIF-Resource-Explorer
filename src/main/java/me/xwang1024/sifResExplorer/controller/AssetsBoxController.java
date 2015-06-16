package me.xwang1024.sifResExplorer.controller;

import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import me.xwang1024.sifResExplorer.presentation.builder.impl.MainStageBuider.AssetLine;
import me.xwang1024.sifResExplorer.service.AssetService;
import me.xwang1024.sifResExplorer.service.AssetService.PathNode;
import me.xwang1024.sifResExplorer.vo.AssetItemVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetsBoxController {
	private static final Logger logger = LoggerFactory.getLogger(AssetsBoxController.class);
	private AssetService assetService = AssetService.getInstance();

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
		List<AssetItemVO> l;
		if (selectedPath.equals("<All>")) {
			l = assetService.getAssetListByPath(searchTf.getText());
		} else {
			l = assetService.getAssetListByPath(searchTf.getText(), selectedPath);
		}
		final ObservableList<AssetLine> data = FXCollections.observableArrayList();
		for (AssetItemVO vo : l) {
			data.add(new AssetLine(false, vo.getImageFilePath(), vo.getRefTextureFilePath()));
		}
		assetsTable.setItems(data);
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
		
		List<AssetItemVO> l;
		if (selectedPath.equals("<All>")) {
			l = assetService.getAssetListByPath(searchTf.getText(),pathBox1.getValue());
		} else {
			l = assetService.getAssetListByPath(searchTf.getText(), pathBox1.getValue(), selectedPath);
		}
		final ObservableList<AssetLine> data = FXCollections.observableArrayList();
		for (AssetItemVO vo : l) {
			data.add(new AssetLine(false, vo.getImageFilePath(), vo.getRefTextureFilePath()));
		}
		assetsTable.setItems(data);
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
		
		List<AssetItemVO> l;
		if (selectedPath.equals("<All>")) {
			l = assetService.getAssetListByPath(searchTf.getText(),pathBox1.getValue(),pathBox2.getValue());
		} else {
			l = assetService.getAssetListByPath(searchTf.getText(), pathBox1.getValue(),pathBox2.getValue(), selectedPath);
		}
		final ObservableList<AssetLine> data = FXCollections.observableArrayList();
		for (AssetItemVO vo : l) {
			data.add(new AssetLine(false, vo.getImageFilePath(), vo.getRefTextureFilePath()));
		}
		assetsTable.setItems(data);
	}

	@FXML
	public void onSearchAction(ActionEvent event) {
		logger.debug("onSearchAction");
		ObservableList<AssetLine> list = assetsTable.getItems();
		for (int i = 0; i < list.size(); i++) {
			AssetLine item = list.get(i);
			if(!item.getImagePath().contains(searchTf.getText()) && !item.getTexturePath().contains(searchTf.getText())) {
				list.remove(i);
				i--;
			}
		}
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
		for (AssetLine item : list) {
			if (item.getSelected()) {
				logger.debug("Selected:" + item.getImagePath());
			}
		}
	}
}
