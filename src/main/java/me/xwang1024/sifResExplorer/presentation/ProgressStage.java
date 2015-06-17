package me.xwang1024.sifResExplorer.presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressStage {
	private final Stage stage;
	private ProgressBar pb;
	private Button cancelBtn;

	private Label lb;

	public ProgressStage(final Stage stg, Task<Void> task) throws Exception {
		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.initOwner(stg);
		stage.setTitle("Exporting...");
		Parent root = FXMLLoader
				.load(this.getClass().getClassLoader().getResource("progress.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		lb = (Label) root.lookup("#detailsLabel");
		pb = (ProgressBar) root.lookup("#progressBar");
		cancelBtn = (Button) root.lookup("#cancelBtn");
		pb.progressProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue,
					Number newValue) {
				if (newValue.doubleValue() >= 1) {
					stage.hide();
				}
			}
		});
		lb.textProperty().bind(task.messageProperty());
		pb.progressProperty().bind(task.progressProperty());
		Thread th = new Thread(task);
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				task.cancel();
				stage.hide();
			}
		});
		th.setDaemon(true);
		th.start();
	}
}