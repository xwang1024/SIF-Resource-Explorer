package me.xwang1024.sifResExplorer.presentation.builder;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public abstract class AbsStageBuilder {
	protected FXMLLoader loader;

	public FXMLLoader getRoot() {
		return loader;
	}

	public void setRoot(FXMLLoader loader) {
		this.loader = loader;
	}

	public AbsStageBuilder(FXMLLoader loader) throws Exception {
		this.loader = loader;
	}

	public abstract void build() throws Exception;
}
