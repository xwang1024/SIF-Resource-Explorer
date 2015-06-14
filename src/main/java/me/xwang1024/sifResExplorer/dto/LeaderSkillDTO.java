package me.xwang1024.sifResExplorer.dto;

public class LeaderSkillDTO {
	private int leaderSkillId;
	private String name;
	private String description;

	public int getLeaderSkillId() {
		return leaderSkillId;
	}

	public void setLeaderSkillId(int leaderSkillId) {
		this.leaderSkillId = leaderSkillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "LeaderSkillDTO [leaderSkillId=" + leaderSkillId + ", name=" + name
				+ ", description=" + description + "]";
	}

}
