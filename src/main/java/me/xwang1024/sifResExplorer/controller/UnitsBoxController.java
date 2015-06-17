package me.xwang1024.sifResExplorer.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import me.xwang1024.sifResExplorer.presentation.builder.impl.AssetsBoxBuilder.AssetLine;
import me.xwang1024.sifResExplorer.presentation.builder.impl.UnitsBoxBuilder.UnitLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnitsBoxController {
	private static final Logger logger = LoggerFactory.getLogger(UnitsBoxController.class);

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

	@FXML
	public void onSearchAction(ActionEvent event) {
		logger.debug("onSearchAction");
		ObservableList<UnitLine> list = unitsTable.getItems();
		for (int i = 0; i < list.size(); i++) {
			UnitLine item = list.get(i);
			// if (!item.getImagePath().contains(searchTf.getText())
			// && !item.getTexturePath().contains(searchTf.getText())) {
			// list.remove(i);
			// i--;
			// }
		}
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

	}

	@FXML
	public void onExportIconAction(ActionEvent event) {

	}

	@FXML
	public void onExportNaviAction(ActionEvent event) {

	}

	@FXML
	public void onNameBoxAction(ActionEvent event) {

	}

	@FXML
	public void onAttrBoxAction(ActionEvent event) {

	}

	@FXML
	public void onRarityBoxAction(ActionEvent event) {

	}

	@FXML
	public void onSkillEffectBoxAction(ActionEvent event) {

	}
	
	@FXML
	public void onSkillTriggerBoxAction(ActionEvent event) {

	}
	
	@FXML
	public void onLeaderSkillTypeBoxAction(ActionEvent event) {

	}
}
