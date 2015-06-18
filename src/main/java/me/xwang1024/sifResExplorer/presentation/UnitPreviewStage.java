package me.xwang1024.sifResExplorer.presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.xwang1024.sifResExplorer.presentation.builder.SIFStage;
import me.xwang1024.sifResExplorer.presentation.builder.impl.UnitPreviewStageBuilder;
import me.xwang1024.sifResExplorer.presentation.builder.impl.UnitsBoxBuilder.UnitLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnitPreviewStage {
	private static final Logger logger = LoggerFactory.getLogger(MainStage.class);

	public UnitPreviewStage(final Stage father, final UnitLine unitLine) throws Exception {
		final Stage stage = new Stage();
		stage.initModality(Modality.NONE);
		stage.initOwner(father);
		String frameName = unitLine.getEponym();
		if (frameName.startsWith("<")) {
			frameName = unitLine.getName();
		} else {
			frameName += " - " + unitLine.getName();
		}

		stage.setTitle("Unit Preview: " + frameName);
		FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader()
				.getResource("unitPreview.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		UnitPreviewStageBuilder builder = new UnitPreviewStageBuilder(fxmlLoader);
		builder.setUnit(unitLine);
		SIFStage showStage = new SIFStage(stage, builder);
		showStage.show();
	}
}
