package me.xwang1024.sifResExplorer.presentation;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.xwang1024.sifResExplorer.service.ConfigService;
import me.xwang1024.sifResExplorer.service.ConfigService.ConfigName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainStage extends Application {
	private static final Logger logger = LoggerFactory
			.getLogger(MainStage.class);
	private ConfigService configService;

	public MainStage() {
		super();
		configService = ConfigService.getInstance();
	}

	@Override
	public void start(Stage stage) throws Exception {
		final Parent root = FXMLLoader.load(this.getClass().getClassLoader()
				.getResource("main.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("SIF Resource Explorer");
		ApplicationContext.stageStack.push(stage);
		// 检查配置文件，目前只有数据路径
		configService.loadConfig();
		String dbPath = configService.get(ConfigName.dbPath);
		String assetsPath = configService.get(ConfigName.assetsPath);
		if (dbPath == null || assetsPath == null) { // 如果没有这个配置项目
			logger.debug("没有找到配置项dbPath/assetsPath");
			DataImportDialog md = new DataImportDialog(stage);
		} else if (!(new File(dbPath).exists())
				|| !(new File(assetsPath).exists())) { // 如果没有这个目录
			logger.debug("dbPath/assetsPath配置的目录已经失效");
			DataImportDialog md = new DataImportDialog(stage);
		} else { // 读取数据文件
			stage.show();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}