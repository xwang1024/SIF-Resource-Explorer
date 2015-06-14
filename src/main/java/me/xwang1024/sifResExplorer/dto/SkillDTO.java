package me.xwang1024.sifResExplorer.dto;

public class SkillDTO {
	private int skillId;
	private String name;
	private String skillNameAsset;
	private int maxLevel;
	private int effectType;
	private int dischargeType;
	private int triggerType;

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkillNameAsset() {
		return skillNameAsset;
	}

	public void setSkillNameAsset(String skillNameAsset) {
		this.skillNameAsset = skillNameAsset;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public int getEffectType() {
		return effectType;
	}

	public void setEffectType(int effectType) {
		this.effectType = effectType;
	}

	public int getDischargeType() {
		return dischargeType;
	}

	public void setDischargeType(int dischargeType) {
		this.dischargeType = dischargeType;
	}

	public int getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(int triggerType) {
		this.triggerType = triggerType;
	}

	@Override
	public String toString() {
		return "SkillDTO [skillId=" + skillId + ", name=" + name + ", skillNameAsset="
				+ skillNameAsset + ", maxLevel=" + maxLevel + ", effectType=" + effectType
				+ ", dischargeType=" + dischargeType + ", triggerType=" + triggerType + "]";
	}
}
