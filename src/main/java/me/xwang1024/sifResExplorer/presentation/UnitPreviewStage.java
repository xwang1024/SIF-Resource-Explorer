package me.xwang1024.sifResExplorer.presentation;

import java.io.File;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.xwang1024.sifResExplorer.model.Unit;
import me.xwang1024.sifResExplorer.presentation.builder.SIFStage;
import me.xwang1024.sifResExplorer.presentation.builder.impl.AssetPreviewStageBuilder;
import me.xwang1024.sifResExplorer.presentation.builder.impl.UnitPreviewStageBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnitPreviewStage {
	private static final Logger logger = LoggerFactory.getLogger(MainStage.class);

	public UnitPreviewStage(final Stage father, final Unit unit) throws Exception {
		final Stage stage = new Stage();
		stage.initModality(Modality.NONE);
		stage.initOwner(father);
		String frameName = unit.getEponym();
		if(frameName.startsWith("<")) {
			frameName = unit.getName();
		} else {
			frameName += " - " + unit.getName();
		}
		
		stage.setTitle("Unit Preview: " + frameName);
		FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader()
				.getResource("assetPreview.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		UnitPreviewStageBuilder builder = new UnitPreviewStageBuilder(fxmlLoader);
		builder.setUnit(unit);
		SIFStage showStage = new SIFStage(stage, builder);
		showStage.show();
	}
}
