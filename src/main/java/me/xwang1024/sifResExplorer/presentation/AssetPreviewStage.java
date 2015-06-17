package me.xwang1024.sifResExplorer.presentation;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.xwang1024.sifResExplorer.presentation.builder.SIFStage;
import me.xwang1024.sifResExplorer.presentation.builder.impl.AssetPreviewStageBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetPreviewStage {
	private static final Logger logger = LoggerFactory.getLogger(MainStage.class);

	public AssetPreviewStage(final Stage father, final String imagPath) throws Exception {
		final Stage stage = new Stage();
		stage.initModality(Modality.NONE);
		stage.initOwner(father);
		stage.setTitle("Asset Preview: " + new File(imagPath).getName());
		FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader()
				.getResource("assetPreview.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		AssetPreviewStageBuilder builder = new AssetPreviewStageBuilder(fxmlLoader);
		builder.setImagPath(imagPath);
		SIFStage showStage = new SIFStage(stage, builder);
		showStage.show();
	}

}
