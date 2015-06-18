package me.xwang1024.sifResExplorer.presentation.builder.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import me.xwang1024.sifResExplorer.presentation.builder.AbsStageBuilder;
import me.xwang1024.sifResExplorer.service.ImageService;

public class AssetPreviewStageBuilder extends AbsStageBuilder {
	private ImageService imageService = new ImageService();
	private ScrollPane scrollPane;
	private StackPane nodeContainer;
	private ImageView imageView;

	private String imagPath;

	public AssetPreviewStageBuilder(FXMLLoader root) throws Exception {
		super(root);
	}

	public String getImagPath() {
		return imagPath;
	}

	public void setImagPath(String imagPath) {
		this.imagPath = imagPath;
	}
	
	private ImageView getImageView() throws IOException {
		BufferedImage bufImage = imageService.getImage(imagPath);
		WritableImage image = SwingFXUtils.toFXImage(bufImage, null);
		ImageView view = new ImageView(image);
		view.setFitWidth(bufImage.getWidth());
		view.setFitHeight(bufImage.getHeight());
		return view;
	}
	
	private void setNewImageView(ImageView newNode) {
		Pane parent = this.imageView != null ? (Pane) this.imageView.getParent() : null;
		if (parent != null) {
			parent.getChildren().remove(this.imageView);
			parent.getChildren().add(newNode);
		}
		this.imageView = newNode;

		imageView.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> observableValue,
					Bounds oldBounds, Bounds newBounds) {
				nodeContainer.setPrefSize(
						Math.max(newBounds.getMaxX(), scrollPane.getViewportBounds().getWidth()),
						Math.max(newBounds.getMaxY(), scrollPane.getViewportBounds().getHeight()));
			}
		});
	}

	private void initElements() throws IOException {
		scrollPane = (ScrollPane) ((Parent)loader.getRoot()).lookup("#scrollPane");
		nodeContainer = new StackPane();
		imageView = getImageView();
		
	}
	
	private void initScrollPane() throws IOException {
		nodeContainer.setStyle("-fx-background-color: #333;");
		nodeContainer.getChildren().add(imageView);
		scrollPane.setContent(nodeContainer);
		scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
			@Override
			public void changed(ObservableValue<? extends Bounds> observableValue,
					Bounds oldBounds, Bounds newBounds) {
				nodeContainer.setPrefSize(
						Math.max(imageView.getBoundsInParent().getMaxX(), newBounds.getWidth()),
						Math.max(imageView.getBoundsInParent().getMaxY(), newBounds.getHeight()));
			}
		});
		setNewImageView(getImageView());
	}

	

	@Override
	public void build() throws IOException {
		initElements();
		initScrollPane();
	}

	
}
