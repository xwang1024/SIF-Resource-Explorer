package me.xwang1024.sifResExplorer.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import me.xwang1024.sifResExplorer.model.Unit;
import me.xwang1024.sifResExplorer.presentation.builder.impl.UnitsBoxBuilder.UnitLine;
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
		
	}

	@FXML
	public void onExportIconAction(ActionEvent event) {

	}

	@FXML
	public void onExportNaviAction(ActionEvent event) {

	}
}
