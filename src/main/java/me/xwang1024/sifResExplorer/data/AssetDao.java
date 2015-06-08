package me.xwang1024.sifResExplorer.data;

import me.xwang1024.sifResExplorer.model.Voice;

public interface AssetDao {
	public String getBackgroundPath(int id);
	public String getBGMPath(int id);
	public String getSEPath(int id);
	public Voice getVoice(int id);
}
