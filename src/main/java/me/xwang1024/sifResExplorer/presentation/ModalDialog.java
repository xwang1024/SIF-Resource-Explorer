package me.xwang1024.sifResExplorer.presentation;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalDialog {

	public ModalDialog(final Stage stg) throws IOException {
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(stg);
		stage.setTitle("Select data source...");
		Parent root = FXMLLoader.load(this.getClass().getClassLoader()
				.getResource("dataImport.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

}
