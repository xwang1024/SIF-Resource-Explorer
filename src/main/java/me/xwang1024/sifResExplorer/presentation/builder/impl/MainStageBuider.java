package me.xwang1024.sifResExplorer.presentation.builder.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import me.xwang1024.sifResExplorer.presentation.builder.AbsStageBuilder;

public class MainStageBuider extends AbsStageBuilder {
	private FXMLLoader assetsLoader;
	private FXMLLoader unitsLoader;

	private ToggleButton assetsTgBtn;
	private ToggleButton unitsTgBtn;
	private StackPane stack;
	private VBox assetsVBox;
	private VBox unitsVBox;
	private final ToggleGroup group = new ToggleGroup();

	private Map<Object, Node> stackMap = new HashMap<Object, Node>();

	public MainStageBuider(FXMLLoader loader) throws Exception {
		super(loader);
		assetsLoader = new FXMLLoader(this.getClass().getClassLoader()
				.getResource("main-assets.fxml"));
		unitsLoader = new FXMLLoader(this.getClass().getClassLoader()
				.getResource("main-units.fxml"));
	}

	private void initElements() throws IOException {
		assetsTgBtn = (ToggleButton) loader.getNamespace().get("rawTgBtn");
		unitsTgBtn = (ToggleButton) loader.getNamespace().get("memberTgBtn");
		stack = (StackPane) loader.getNamespace().get("stackPane");

		assetsVBox = assetsLoader.load();
		unitsVBox = unitsLoader.load();
		stack.getChildren().add(assetsVBox);
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
	public void build() throws Exception {
		initElements();
		initToggleGroup();
		AssetsBoxBuilder assetBuilder = new AssetsBoxBuilder(assetsLoader);
		assetBuilder.build();
		UnitsBoxBuilder unitsBuilder = new UnitsBoxBuilder(unitsLoader);
		unitsBuilder.build();
	}

}
