package me.xwang1024.sifResExplorer.presentation.builder.impl;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import me.xwang1024.sifResExplorer.model.Unit;
import me.xwang1024.sifResExplorer.presentation.ApplicationContext;
import me.xwang1024.sifResExplorer.presentation.UnitPreviewStage;
import me.xwang1024.sifResExplorer.presentation.builder.AbsStageBuilder;
import me.xwang1024.sifResExplorer.service.UnitService;

public class UnitsBoxBuilder extends AbsStageBuilder {
	private final UnitService unitService;

	private Label selectStatLb;
	private ComboBox<String> nameBox;
	private ComboBox<String> attrBox;
	private ComboBox<String> rarityBox;
	private ComboBox<String> skillEffectBox;
	private ComboBox<String> skillTriggerBox;
	private ComboBox<String> leaderSkillTypeBox;
	private TableView<UnitLine> unitsTable;
	private TableColumn<UnitLine, Boolean> checkboxCol;
	private TableColumn<UnitLine, Integer> idCol;
	private TableColumn<UnitLine, Integer> numCol;
	private TableColumn<UnitLine, String> nameCol;
	private TableColumn<UnitLine, String> eponymCol;
	private TableColumn<UnitLine, String> attrCol;
	private TableColumn<UnitLine, String> rarityCol;
	private TableColumn<UnitLine, Integer> smileCol;
	private TableColumn<UnitLine, Integer> pureCol;
	private TableColumn<UnitLine, Integer> coolCol;
	private TableColumn<UnitLine, String> leaderSkillCol;
	private TableColumn<UnitLine, String> skillNameCol;
	private TableColumn<UnitLine, String> skillEffectCol;
	private TableColumn<UnitLine, String> skillTriggerCol;

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

	public UnitsBoxBuilder(FXMLLoader loader) throws Exception {
		super(loader);
		unitService = UnitService.getInstance();
	}

	private void initElements() {
		selectStatLb = (Label) loader.getNamespace().get("selectStatLb");
		nameBox = (ComboBox<String>) loader.getNamespace().get("nameBox");
		attrBox = (ComboBox<String>) loader.getNamespace().get("attrBox");
		rarityBox = (ComboBox<String>) loader.getNamespace().get("rarityBox");
		skillEffectBox = (ComboBox<String>) loader.getNamespace().get("skillEffectBox");
		skillTriggerBox = (ComboBox<String>) loader.getNamespace().get("skillTriggerBox");
		leaderSkillTypeBox = (ComboBox<String>) loader.getNamespace().get("leaderSkillTypeBox");

		unitsTable = (TableView<UnitLine>) loader.getNamespace().get("unitsTable");
		checkboxCol = (TableColumn<UnitLine, Boolean>) loader.getNamespace().get("checkboxCol");
		idCol = (TableColumn<UnitLine, Integer>) loader.getNamespace().get("idCol");
		numCol = (TableColumn<UnitLine, Integer>) loader.getNamespace().get("numCol");
		nameCol = (TableColumn<UnitLine, String>) loader.getNamespace().get("nameCol");
		eponymCol = (TableColumn<UnitLine, String>) loader.getNamespace().get("eponymCol");
		attrCol = (TableColumn<UnitLine, String>) loader.getNamespace().get("attrCol");
		rarityCol = (TableColumn<UnitLine, String>) loader.getNamespace().get("rarityCol");
		smileCol = (TableColumn<UnitLine, Integer>) loader.getNamespace().get("smileCol");
		pureCol = (TableColumn<UnitLine, Integer>) loader.getNamespace().get("pureCol");
		coolCol = (TableColumn<UnitLine, Integer>) loader.getNamespace().get("coolCol");
		leaderSkillCol = (TableColumn<UnitLine, String>) loader.getNamespace()
				.get("leaderSkillCol");
		skillNameCol = (TableColumn<UnitLine, String>) loader.getNamespace().get("skillNameCol");
		skillEffectCol = (TableColumn<UnitLine, String>) loader.getNamespace()
				.get("skillEffectCol");
		skillTriggerCol = (TableColumn<UnitLine, String>) loader.getNamespace().get(
				"skillTriggerCol");
	}

	private void initTable() {
		unitsTable.setRowFactory(tv -> {
			TableRow<UnitLine> row = new TableRow<UnitLine>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					UnitLine rowData = (UnitLine) row.getItem();
					System.out.println(rowData);
					try {
						new UnitPreviewStage(ApplicationContext.stageStack.peek().getStage(),
								rowData);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			return row;
		});
		checkboxCol.setCellValueFactory(new PropertyValueFactory<UnitLine, Boolean>("selected"));
		checkboxCol
				.setCellFactory(new Callback<TableColumn<UnitLine, Boolean>, TableCell<UnitLine, Boolean>>() {
					public TableCell<UnitLine, Boolean> call(TableColumn<UnitLine, Boolean> p) {
						return new CheckBoxTableCell<UnitLine, Boolean>();
					}
				});
		idCol.setCellValueFactory(new PropertyValueFactory<UnitLine, Integer>("id"));
		idCol.setEditable(false);
		numCol.setCellValueFactory(new PropertyValueFactory<UnitLine, Integer>("num"));
		numCol.setEditable(false);
		nameCol.setCellValueFactory(new PropertyValueFactory<UnitLine, String>("name"));
		nameCol.setEditable(false);
		eponymCol.setCellValueFactory(new PropertyValueFactory<UnitLine, String>("eponym"));
		eponymCol.setEditable(false);
		attrCol.setCellValueFactory(new PropertyValueFactory<UnitLine, String>("attr"));
		attrCol.setEditable(false);
		rarityCol.setCellValueFactory(new PropertyValueFactory<UnitLine, String>("rarity"));
		rarityCol.setEditable(false);
		smileCol.setCellValueFactory(new PropertyValueFactory<UnitLine, Integer>("smile"));
		smileCol.setEditable(false);
		pureCol.setCellValueFactory(new PropertyValueFactory<UnitLine, Integer>("pure"));
		pureCol.setEditable(false);
		coolCol.setCellValueFactory(new PropertyValueFactory<UnitLine, Integer>("cool"));
		coolCol.setEditable(false);
		leaderSkillCol
				.setCellValueFactory(new PropertyValueFactory<UnitLine, String>("leaderSkill"));
		leaderSkillCol.setEditable(false);
		skillNameCol.setCellValueFactory(new PropertyValueFactory<UnitLine, String>("skillName"));
		skillNameCol.setEditable(false);
		skillEffectCol
				.setCellValueFactory(new PropertyValueFactory<UnitLine, String>("skillEffect"));
		skillEffectCol.setEditable(false);
		skillTriggerCol.setCellValueFactory(new PropertyValueFactory<UnitLine, String>(
				"skillTrigger"));
		skillTriggerCol.setEditable(false);

		unitsTable.setEditable(true);
	}

	private void initBox() {
		List<String> nameList = unitService.getNameList();
		List<String> attrList = unitService.getAttrList();
		List<String> rarityList = unitService.getRarityList();
		List<String> leaderSkillTypeList = unitService.getLeaderSkillTypeList();
		List<String> skillEffectList = unitService.getSkillEffectList();
		List<String> skillTriggerList = unitService.getSkillTriggerList();
		ObservableList<String> nameObList = FXCollections.observableArrayList(nameList);
		ObservableList<String> attrObList = FXCollections.observableArrayList(attrList);
		ObservableList<String> rarityObList = FXCollections.observableArrayList(rarityList);
		ObservableList<String> leaderSkillTypeObList = FXCollections
				.observableArrayList(leaderSkillTypeList);
		ObservableList<String> skillEffectObList = FXCollections
				.observableArrayList(skillEffectList);
		ObservableList<String> skillTriggerObList = FXCollections
				.observableArrayList(skillTriggerList);
		nameObList.add(0, "<All>");
		attrObList.add(0, "<All>");
		rarityObList.add(0, "<All>");
		leaderSkillTypeObList.add(0, "<All>");
		skillEffectObList.add(0, "<All>");
		skillTriggerObList.add(0, "<All>");
		nameBox.setItems(nameObList);
		attrBox.setItems(attrObList);
		rarityBox.setItems(rarityObList);
		leaderSkillTypeBox.setItems(leaderSkillTypeObList);
		skillEffectBox.setItems(skillEffectObList);
		skillTriggerBox.setItems(skillTriggerObList);
	}

	private void initTableData() {
		List<Unit> l = unitService.getUnitList();
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

	@Override
	public void build() throws IOException {
		initElements();
		initTable();
		initBox();
		initTableData();
	}

	public static class UnitLine {
		private BooleanProperty selected;
		private IntegerProperty id;
		private IntegerProperty num;
		private StringProperty name;
		private StringProperty eponym;
		private StringProperty attr;
		private StringProperty rarity;
		private IntegerProperty smile;
		private IntegerProperty pure;
		private IntegerProperty cool;
		private StringProperty leaderSkill;
		private StringProperty skillName;
		private StringProperty skillEffect;
		private StringProperty skillTrigger;

		public UnitLine(boolean selected, int id, int num, String name, String eponym, String attr,
				String rarity, int smile, int pure, int cool, String leaderSkill, String skillName,
				String skillEffect, String skillTrigger, ChangeListener<Boolean> listener) {
			super();
			this.selected = new SimpleBooleanProperty(selected);
			this.id = new SimpleIntegerProperty(id);
			this.num = new SimpleIntegerProperty(num);
			this.name = new SimpleStringProperty(name);
			this.eponym = new SimpleStringProperty(eponym);
			this.attr = new SimpleStringProperty(attr);
			this.rarity = new SimpleStringProperty(rarity);
			this.smile = new SimpleIntegerProperty(smile);
			this.pure = new SimpleIntegerProperty(pure);
			this.cool = new SimpleIntegerProperty(cool);
			this.leaderSkill = new SimpleStringProperty(leaderSkill);
			this.skillName = new SimpleStringProperty(skillName);
			this.skillEffect = new SimpleStringProperty(skillEffect);
			this.skillTrigger = new SimpleStringProperty(skillTrigger);

			if (listener != null) {
				this.selected.addListener(listener);
			}
		}

		public BooleanProperty selectedProperty() {
			return selected;
		}

		public IntegerProperty idProperty() {
			return id;
		}

		public IntegerProperty numProperty() {
			return num;
		}

		public StringProperty nameProperty() {
			return name;
		}

		public StringProperty eponymProperty() {
			return eponym;
		}

		public StringProperty attrProperty() {
			return attr;
		}

		public StringProperty rarityProperty() {
			return rarity;
		}

		public IntegerProperty smileProperty() {
			return smile;
		}

		public IntegerProperty pureProperty() {
			return pure;
		}

		public IntegerProperty coolProperty() {
			return cool;
		}

		public StringProperty leaderSkillProperty() {
			return leaderSkill;
		}

		public StringProperty skillNameProperty() {
			return skillName;
		}

		public StringProperty skillEffectProperty() {
			return skillEffect;
		}

		public StringProperty skillTriggerProperty() {
			return skillTrigger;
		}

		public void setSelected(boolean selected) {
			this.selected.set(selected);
		}

		public boolean getSelected() {
			return selected.get();
		}

		public String getEponym() {
			return eponym.get();
		}

		public String getName() {
			return name.get();
		}

		public int getId() {
			return id.get();
		}
	}
}
