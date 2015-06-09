package me.xwang1024.sifResExplorer.controller;

import java.io.File;
import java.io.IOException;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import me.xwang1024.sifResExplorer.config.SIFConfig;
import me.xwang1024.sifResExplorer.presentation.ApplicationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataImportDialogController {
	private static final Logger logger = LoggerFactory
			.getLogger(DataImportDialogController.class);
	private SIFConfig configService;
	private String dbPath;
	private String assetsPath;
	@FXML
	private TextField pathField;
	@FXML
	private Button chooseBtn;
	@FXML
	private Button verifyBtn;
	@FXML
	private Label verifyLabel;
	@FXML
	private CheckBox databaseBox;
	@FXML
	private CheckBox imageBox;
	@FXML
	private CheckBox defaultBox;
	@FXML
	private Button exitBtn;
	@FXML
	private Button continueBtn;

	public DataImportDialogController() {
		configService = SIFConfig.getInstance();
	}

	@FXML
	public void onChooseAction(ActionEvent event) {
		logger.debug("onChooseAction");
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Choose raw data directory...");
		File f = chooser.showDialog(((Node) event.getTarget()).getScene()
				.getWindow());
		if (f != null) {
			pathField.setText(f.getAbsolutePath());
		}
	}

	@FXML
	public void onVerifyAction(ActionEvent event) {
		logger.debug("onVerifyAction");
		// 目前只进行简单的验证，递归判断有没有db和assets文件夹
		String path = pathField.getText();
		if (path == null || path.equals("")) {
			// 不可能事件
			return;
		}
		final File f = new File(path);
		if (!f.exists()) {
			verifyLabel.setText("This dir contains doesn't exist.");
			return;
		}
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				boolean[] checker = { false, false }; // db, assets
				verify(f, checker);
				return null;
			}

			@Override
			protected void failed() {
				super.failed();
				updateMessage("Completed.");
				verifyLabel.textProperty().unbind();
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				updateMessage("Completed.");
				verifyLabel.textProperty().unbind();
			}

			private void verify(File f, boolean[] checker) {
				// 如果全部检查完成就结束
				boolean isComleted = true;
				for (boolean check : checker) {
					isComleted &= check;
				}
				if (isComleted) {
					defaultBox.setDisable(false);
					continueBtn.setDisable(false);
					return;
				}
				// 否则
				if (f.isFile()) {
					return;
				} else if (f.isDirectory()) {
					String dirName = f.getName();
					updateMessage("Verify dir - " + dirName);
					if (dirName.equals("db") && f.list().length > 0) {
						checker[0] = true;
						dbPath = f.getAbsolutePath();
						databaseBox.setSelected(true);
					}
					if (dirName.equals("assets") && f.list().length > 0) {
						checker[1] = true;
						assetsPath = f.getAbsolutePath();
						imageBox.setSelected(true);
					}
					if (f.listFiles() != null) {
						for (File child : f.listFiles()) {
							verify(child, checker);
						}
					}
				}
			}
		};
		verifyLabel.textProperty().bind(task.messageProperty());
		task.run();
	}

	@FXML
	public void onExitAction(ActionEvent event) {
		logger.debug("onExitAction");
		Stage stage = ApplicationContext.stageStack.pop();
		stage.close();
	}

	@FXML
	public void onContinueAction(ActionEvent event) throws IOException {
		logger.debug("onContinueAction");
		configService.set("dbPath", dbPath);
		configService.set("assetsPath", assetsPath);
		if(defaultBox.isSelected()) {
			configService.saveConfig();
		}
		ApplicationContext.stageStack.pop().close();
		ApplicationContext.stageStack.peek().show();
	}

}
