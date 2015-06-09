package me.xwang1024.sifResExplorer.presentation.builder.impl;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import me.xwang1024.sifResExplorer.presentation.builder.IStageBuilder;

public class MainStageBuider extends IStageBuilder {
	private ToggleButton rawTgBtn;
	private ToggleButton memberTgBtn;
	private final ToggleGroup group = new ToggleGroup();

	public MainStageBuider(Parent root) {
		super(root);
	}

	private void initElements() {
		rawTgBtn = (ToggleButton) root.lookup("#rawTgBtn");
		memberTgBtn = (ToggleButton) root.lookup("#memberTgBtn");
	}

	private void initComboBox() {

	}

	private void initToggleGroup() {
		// 设置相应数据
		rawTgBtn.setUserData("Raw Material");
		memberTgBtn.setUserData("Member");
		// 不允许取消点击
		rawTgBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ToggleButton btn = (ToggleButton) event.getSource();
				if (!btn.isSelected()) {
					btn.setSelected(true);
				}
			}
		});
		memberTgBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ToggleButton btn = (ToggleButton) event.getSource();
				if (!btn.isSelected()) {
					btn.setSelected(true);
				}
			}
		});
		// toggle设置
		rawTgBtn.setToggleGroup(group);
		memberTgBtn.setToggleGroup(group);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle,
					Toggle newToggle) {
				Toggle tg = group.getSelectedToggle();
				if (tg != null) {
					System.out.println(tg.getUserData());
				}
			}
		});
	}

	@Override
	public void build() {
		initElements();
		initComboBox();
		initToggleGroup();
	}

}
