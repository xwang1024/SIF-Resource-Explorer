package me.xwang1024.sifResExplorer.presentation.builder;

import java.io.IOException;

import javafx.stage.Stage;

public class SIFStage {
	private boolean isBuild = false;
	private Stage stage;
	private IStageBuilder builder;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public IStageBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(IStageBuilder builder) {
		this.builder = builder;
	}

	public void show() throws IOException {
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

	public SIFStage(Stage stage, IStageBuilder builder) {
		super();
		this.stage = stage;
		this.builder = builder;
	}
}
