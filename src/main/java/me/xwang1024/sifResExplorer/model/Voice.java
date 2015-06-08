package me.xwang1024.sifResExplorer.model;

public class Voice {
	private int id;
	private String path;
	private String content;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Voice [id=" + id + ", path=" + path + ", content=" + content
				+ "]";
	}
	
	
}
