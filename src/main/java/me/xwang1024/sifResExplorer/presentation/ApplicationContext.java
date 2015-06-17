package me.xwang1024.sifResExplorer.presentation;

import java.io.File;
import java.util.Stack;

import me.xwang1024.sifResExplorer.presentation.builder.SIFStage;

public class ApplicationContext {
	public static Stack<SIFStage> stageStack = new Stack<SIFStage>();
	public static File tracedFile;
}
