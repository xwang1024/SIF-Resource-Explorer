package me.xwang1024.sifResExplorer.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		final Parent root = FXMLLoader.load(this.getClass().getClassLoader()
				.getResource("main.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("SIF Resource Explorer");
		stage.hide();
		ModalDialog md = new ModalDialog(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}