package me.xwang1024.sifResExplorer.presentation.builder.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import me.xwang1024.sifResExplorer.presentation.ApplicationContext;
import me.xwang1024.sifResExplorer.presentation.AssetPreviewStage;
import me.xwang1024.sifResExplorer.presentation.builder.IStageBuilder;
import me.xwang1024.sifResExplorer.service.AssetService;
import me.xwang1024.sifResExplorer.service.AssetService.PathNode;
import me.xwang1024.sifResExplorer.vo.AssetItemVO;

public class MainStageBuider extends IStageBuilder {
	private final AssetService assetService = AssetService.getInstance();
	private ToggleButton assetsTgBtn;
	private ToggleButton unitsTgBtn;
	private StackPane stack;
	private VBox assetsVBox;
	private VBox unitsVBox;
	private final ToggleGroup group = new ToggleGroup();
	private Label selectStatLb;
	private ComboBox pathBox1;

	private TableView assetsTable;
	private TableColumn checkboxCol;
	private TableColumn imageNameCol;
	private TableColumn textureNameCol;

	private Map<Object, Node> stackMap = new HashMap<Object, Node>();
	
	private ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
		public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
			refreshSelectStat();
		}
	};

	public MainStageBuider(FXMLLoader loader) {
		super(loader);
	}

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

	private void initElements() throws IOException {
		assetsTgBtn = (ToggleButton) loader.getNamespace().get("rawTgBtn");
		unitsTgBtn = (ToggleButton) loader.getNamespace().get("memberTgBtn");
		stack = (StackPane) loader.getNamespace().get("stackPane");

		FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader()
				.getResource("main-assets.fxml"));
		assetsVBox = fxmlLoader.load();

		unitsVBox = FXMLLoader
				.load(this.getClass().getClassLoader().getResource("main-units.fxml"));
		stack.getChildren().add(assetsVBox);

		selectStatLb = (Label) fxmlLoader.getNamespace().get("selectStatLb");
		pathBox1 = (ComboBox) fxmlLoader.getNamespace().get("pathBox1");
		assetsTable = (TableView) ((Parent) loader.getRoot()).lookup("#assetsTable");
		checkboxCol = (TableColumn) assetsTable.getColumns().get(0);
		imageNameCol = (TableColumn) assetsTable.getColumns().get(1);
		textureNameCol = (TableColumn) assetsTable.getColumns().get(2);
	}

	private void buildTable() {
		assetsTable.setRowFactory(tv -> {
			TableRow row = new TableRow();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					AssetLine rowData = (AssetLine) row.getItem();
					System.out.println(rowData.getImagePath());
					try {
						new AssetPreviewStage(ApplicationContext.stageStack.peek().getStage(),
								rowData.getImagePath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			return row;
		});
		// "#" column
		checkboxCol.setCellValueFactory(new PropertyValueFactory("selected"));
		checkboxCol
				.setCellFactory(new Callback<TableColumn<AssetLine, Boolean>, TableCell<AssetLine, Boolean>>() {
					public TableCell<AssetLine, Boolean> call(TableColumn<AssetLine, Boolean> p) {
						return new CheckBoxTableCell<AssetLine, Boolean>();
					}
				});
		// "Image" column
		imageNameCol.setCellValueFactory(new PropertyValueFactory("imagePath"));
		imageNameCol.setEditable(false);
		// "Texture" column
		textureNameCol.setCellValueFactory(new PropertyValueFactory("texturePath"));
		textureNameCol.setEditable(false);
		assetsTable.setEditable(true);
	}

	private void initData() {
		List<AssetItemVO> l = assetService.getAssetList();
		// init comboBox
		PathNode root = assetService.getRootNode();
		ObservableList<String> list = FXCollections
				.observableArrayList(root.getChildren().keySet());
		Collections.sort(list);
		list.add(0, "<All>");
		pathBox1.setItems(list);
		pathBox1.getSelectionModel().select(0);
		// init table data
		final ObservableList<AssetLine> data = FXCollections.observableArrayList();
		for (AssetItemVO vo : l) {
			data.add(new AssetLine(false, vo.getImageFilePath(), vo.getRefTextureFilePath(),
					listener));
		}
		assetsTable.setItems(data);
		refreshSelectStat();
	}

	private void initToggleGroup() {
		// 设置相应数据
		assetsTgBtn.setUserData("Assets");
		unitsTgBtn.setUserData("Units");
		stackMap.put(assetsTgBtn.getUserData(), assetsVBox);
		stackMap.put(unitsTgBtn.getUserData(), unitsVBox);
		// 不允许取消点击
		assetsTgBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ToggleButton btn = (ToggleButton) event.getSource();
				if (!btn.isSelected()) {
					btn.setSelected(true);
				}
			}
		});
		unitsTgBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ToggleButton btn = (ToggleButton) event.getSource();
				if (!btn.isSelected()) {
					btn.setSelected(true);
				}
			}
		});
		// toggle设置
		assetsTgBtn.setToggleGroup(group);
		unitsTgBtn.setToggleGroup(group);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle,
					Toggle newToggle) {
				Toggle tg = group.getSelectedToggle();
				if (tg != null) {
					ObservableList<Node> children = stack.getChildren();
					Object paneTag = tg.getUserData();
					System.out.println(paneTag);
					children.clear();
					children.add(stackMap.get(paneTag));
				}
			}
		});
	}

	@Override
	public void build() throws IOException {
		initElements();
		buildTable();
		initData();
		initToggleGroup();
	}

	public static class AssetLine {
		private BooleanProperty selected;
		private StringProperty imagePath;
		private StringProperty texturePath;

		public AssetLine(boolean selected, String imagePath, String texturePath,
				ChangeListener<Boolean> listener) {
			this.selected = new SimpleBooleanProperty(selected);
			this.imagePath = new SimpleStringProperty(imagePath);
			this.texturePath = new SimpleStringProperty(texturePath);
			if (listener != null) {
				this.selected.addListener(listener);
			}
		}

		public BooleanProperty selectedProperty() {
			return selected;
		}

		public StringProperty imagePathProperty() {
			return imagePath;
		}

		public StringProperty texturePathProperty() {
			return texturePath;
		}

		public void setSelected(boolean selected) {
			this.selected.set(selected);
		}

		public boolean getSelected() {
			return selected.get();
		}

		public String getImagePath() {
			return imagePath.get();
		}

		public String getTexturePath() {
			return texturePath.get();
		}

	}
}
