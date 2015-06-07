package me.xwang1024.sifResExplorer.presentation;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DataImportDialog {
	private TextField pathField;
	private Button verifyBtn;
	private Label verifyLabel;
	private CheckBox databaseBox;
	private CheckBox imageBox;
	private CheckBox defaultBox;
	private Button continueBtn;

	public DataImportDialog(final Stage stg) throws IOException {
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(stg);
		stage.setTitle("Select data source...");
		Parent root = FXMLLoader.load(this.getClass().getClassLoader()
				.getResource("dataImport.fxml"));
		initElements(root);
		initListener(root);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		ApplicationContext.stageStack.push(stage);
	}

	public void initElements(final Parent root) {
		pathField = (TextField) root.lookup("#pathField");
		verifyBtn = (Button) root.lookup("#verifyBtn");
		verifyLabel = (Label) root.lookup("#verifyLabel");
		databaseBox = (CheckBox) root.lookup("#databaseBox");
		imageBox = (CheckBox) root.lookup("#imageBox");
		defaultBox = (CheckBox) root.lookup("#defaultBox");
		continueBtn = (Button) root.lookup("#continueBtn");
	}

	public void initListener(final Parent root) {
		// 路径显示框和验证按钮、验证说明标签进行绑定
		pathField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				databaseBox.setSelected(false);
				imageBox.setSelected(false);
				defaultBox.setDisable(true);
				continueBtn.setDisable(true);
				if (newValue != null && !newValue.equals("")) {
					// 允许进行下一步
					verifyBtn.setDisable(false);
					verifyLabel.setText("Click button 'Verify' to continue.");
				} else {
					// 否则
					verifyBtn.setDisable(true);
					verifyLabel.setText("Choose or input the data directory first.");
				}
			}
		});

	}
}
