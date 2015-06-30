package me.xwang1024.sifResExplorer.presentation;

import java.io.IOException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.xwang1024.sifResExplorer.controller.CardsExportConfigDialogController;
import me.xwang1024.sifResExplorer.presentation.builder.SIFStage;
import me.xwang1024.sifResExplorer.presentation.builder.impl.UnitsBoxBuilder.UnitLine;

public class CardsExportDialog {
	private TextField pathField;
	private Button exportBtn;

	public CardsExportDialog(final Stage stg, List<UnitLine> idList) throws IOException {
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(stg);
		stage.setTitle("Card export config...");
		FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader()
				.getResource("cardsExport.fxml"));
		Parent root = loader.load();
		CardsExportConfigDialogController controller = loader.getController();
		controller.setIdList(idList);
		initElements(root);
		initListener(root);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		SIFStage importStage = new SIFStage(stage, null);
		ApplicationContext.stageStack.push(importStage);
	}

	public void initElements(final Parent root) {
		pathField = (TextField) root.lookup("#pathField");
		exportBtn = (Button) root.lookup("#exportBtn");
	}

	public void initListener(final Parent root) {
		// 路径显示框和验证按钮、验证说明标签进行绑定
		pathField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
					String newValue) {
				if (newValue != null && !newValue.equals("")) {
					// 允许进行下一步
					exportBtn.setDisable(false);
				} else {
					// 否则
					exportBtn.setDisable(true);
				}
			}
		});

	}
}
