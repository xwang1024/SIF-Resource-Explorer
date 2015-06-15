package me.xwang1024.sifResExplorer.presentation.builder;

import java.io.IOException;

import javafx.scene.Parent;

public abstract class IStageBuilder {
	protected Parent root;

	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}
	
	public IStageBuilder(Parent root) {
		this.root = root;
	}
	
	public abstract void build() throws IOException;
}
