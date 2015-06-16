package me.xwang1024.sifResExplorer.presentation;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.xwang1024.sifResExplorer.config.SIFConfig;
import me.xwang1024.sifResExplorer.config.SIFConfig.ConfigName;
import me.xwang1024.sifResExplorer.presentation.builder.IStageBuilder;
import me.xwang1024.sifResExplorer.presentation.builder.SIFStage;
import me.xwang1024.sifResExplorer.presentation.builder.impl.MainStageBuider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainStage extends Application {
	private static final Logger logger = LoggerFactory.getLogger(MainStage.class);
	private SIFConfig configService = SIFConfig.getInstance();

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader()
				.getResource("main.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("SIF Resource Explorer");
		SIFStage mainStage = new SIFStage(stage, new MainStageBuider(fxmlLoader));
		ApplicationContext.stageStack.push(mainStage);
		
		// 检查配置文件，目前只有数据路径
		configService.loadConfig();
		String dbPath = configService.get(ConfigName.dbPath);
		String assetsPath = configService.get(ConfigName.assetsPath);
		if (dbPath == null || assetsPath == null) { // 如果没有这个配置项目
			logger.debug("没有找到配置项dbPath/assetsPath");
			new DataImportDialog(stage);
		} else if (!(new File(dbPath).exists()) || !(new File(assetsPath).exists())) { // 如果没有这个目录
			logger.debug("dbPath/assetsPath配置的目录已经失效");
			new DataImportDialog(stage);
		} else { // 读取数据文件
			mainStage.show();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}