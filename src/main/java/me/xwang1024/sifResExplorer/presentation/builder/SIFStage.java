package me.xwang1024.sifResExplorer.presentation.builder;

import java.io.IOException;

import javafx.stage.Stage;

public class SIFStage {
	private boolean isBuild = false;
	private Stage stage;
	private AbsStageBuilder builder;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public AbsStageBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(AbsStageBuilder builder) {
		this.builder = builder;
	}

	public void show() throws Exception {
		if (builder != null && !isBuild) {
			builder.build();
		}
		stage.show();
	}

	public void hide() {
		stage.hide();
	}

	public void close() {
		stage.close();
	}

	public SIFStage() {
		// TODO Auto-generated constructor stub
	}

	public SIFStage(Stage stage, AbsStageBuilder builder) {
		super();
		this.stage = stage;
		this.builder = builder;
	}
}
