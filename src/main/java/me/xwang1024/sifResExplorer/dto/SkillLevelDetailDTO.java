package me.xwang1024.sifResExplorer.dto;

public class SkillLevelDetailDTO {
	private int skillId;
	private int skillLevel;
	private String description;
	private int effectRange;
	private double effectValue;
	private double dischargeTime;
	private int triggerValue;
	private int activationRate;

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEffectRange() {
		return effectRange;
	}

	public void setEffectRange(int effectRange) {
		this.effectRange = effectRange;
	}

	public double getEffectValue() {
		return effectValue;
	}

	public void setEffectValue(double effectValue) {
		this.effectValue = effectValue;
	}

	public double getDischargeTime() {
		return dischargeTime;
	}

	public void setDischargeTime(double dischargeTime) {
		this.dischargeTime = dischargeTime;
	}

	public int getTriggerValue() {
		return triggerValue;
	}

	public void setTriggerValue(int triggerValue) {
		this.triggerValue = triggerValue;
	}

	public int getActivationRate() {
		return activationRate;
	}

	public void setActivationRate(int activationRate) {
		this.activationRate = activationRate;
	}

	@Override
	public String toString() {
		return "SkillLevelDetail [skillId=" + skillId + ", skillLevel=" + skillLevel
				+ ", description=" + description + ", effectRange=" + effectRange
				+ ", effectValue=" + effectValue + ", dischargeTime=" + dischargeTime
				+ ", triggerValue=" + triggerValue + ", activationRate=" + activationRate + "]";
	}
}
