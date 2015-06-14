package me.xwang1024.sifResExplorer.dto;

public class UnitDTO {
	private int unitId;
	private int unitNumber;
	private int unitTypeId;
	private String name;
	private int normalCardId;
	private int idolizeCardId;
	private int maxLevelCardId;
	private int maxBondCardId;
	private int allMaxCardId;
	private String normalAvatarPath;
	private String idolizeAvatarPath;
	private int normalCGAssetId;
	private int idolizeCGAssetId;
	private int rarity;
	private int attrId;
	private int defaultSkillId;
	private int skillAssetVoiceId;
	private int defaultLeaderSkillId;
	private String message;
	private int maxNormalBond;
	private int maxIdolizeBond;
	private int maxNormalLevel;
	private int maxIdolizeLevel;
	private int levelUpPatternId;
	private int maxStamina;
	private int maxSmile;
	private int maxPure;
	private int maxCool;
	private int idolizeCost;

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(int unitNumber) {
		this.unitNumber = unitNumber;
	}

	public int getUnitTypeId() {
		return unitTypeId;
	}

	public void setUnitTypeId(int unitTypeId) {
		this.unitTypeId = unitTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNormalCardId() {
		return normalCardId;
	}

	public void setNormalCardId(int normalCardId) {
		this.normalCardId = normalCardId;
	}

	public int getIdolizeCardId() {
		return idolizeCardId;
	}

	public void setIdolizeCardId(int idolizeCardId) {
		this.idolizeCardId = idolizeCardId;
	}

	public int getMaxLevelCardId() {
		return maxLevelCardId;
	}

	public void setMaxLevelCardId(int maxLevelCardId) {
		this.maxLevelCardId = maxLevelCardId;
	}

	public int getMaxBondCardId() {
		return maxBondCardId;
	}

	public void setMaxBondCardId(int maxBondCardId) {
		this.maxBondCardId = maxBondCardId;
	}

	public int getAllMaxCardId() {
		return allMaxCardId;
	}

	public void setAllMaxCardId(int allMaxCardId) {
		this.allMaxCardId = allMaxCardId;
	}

	public String getNormalAvatarPath() {
		return normalAvatarPath;
	}

	public void setNormalAvatarPath(String normalAvatarPath) {
		this.normalAvatarPath = normalAvatarPath;
	}

	public String getIdolizeAvatarPath() {
		return idolizeAvatarPath;
	}

	public void setIdolizeAvatarPath(String idolizeAvatarPath) {
		this.idolizeAvatarPath = idolizeAvatarPath;
	}

	public int getNormalCGAssetId() {
		return normalCGAssetId;
	}

	public void setNormalCGAssetId(int normalCGAssetId) {
		this.normalCGAssetId = normalCGAssetId;
	}

	public int getIdolizeCGAssetId() {
		return idolizeCGAssetId;
	}

	public void setIdolizeCGAssetId(int idolizeCGAssetId) {
		this.idolizeCGAssetId = idolizeCGAssetId;
	}

	public int getRarity() {
		return rarity;
	}

	public void setRarity(int rarity) {
		this.rarity = rarity;
	}

	public int getDefaultSkillId() {
		return defaultSkillId;
	}

	public void setDefaultSkillId(int defaultSkillId) {
		this.defaultSkillId = defaultSkillId;
	}

	public int getSkillAssetVoiceId() {
		return skillAssetVoiceId;
	}

	public void setSkillAssetVoiceId(int skillAssetVoiceId) {
		this.skillAssetVoiceId = skillAssetVoiceId;
	}

	public int getDefaultLeaderSkillId() {
		return defaultLeaderSkillId;
	}

	public void setDefaultLeaderSkillId(int defaultLeaderSkillId) {
		this.defaultLeaderSkillId = defaultLeaderSkillId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getMaxNormalLevel() {
		return maxNormalLevel;
	}

	public void setMaxNormalLevel(int maxNormalLevel) {
		this.maxNormalLevel = maxNormalLevel;
	}

	public int getMaxIdolizeLevel() {
		return maxIdolizeLevel;
	}

	public void setMaxIdolizeLevel(int maxIdolizeLevel) {
		this.maxIdolizeLevel = maxIdolizeLevel;
	}

	public int getLevelUpPatternId() {
		return levelUpPatternId;
	}

	public void setLevelUpPatternId(int levelUpPatternId) {
		this.levelUpPatternId = levelUpPatternId;
	}

	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}

	public int getMaxSmile() {
		return maxSmile;
	}

	public void setMaxSmile(int maxSmile) {
		this.maxSmile = maxSmile;
	}

	public int getMaxPure() {
		return maxPure;
	}

	public void setMaxPure(int maxPure) {
		this.maxPure = maxPure;
	}

	public int getMaxCool() {
		return maxCool;
	}

	public void setMaxCool(int maxCool) {
		this.maxCool = maxCool;
	}

	public int getIdolizeCost() {
		return idolizeCost;
	}

	public void setIdolizeCost(int idolizeCost) {
		this.idolizeCost = idolizeCost;
	}

	public int getAttrId() {
		return attrId;
	}

	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}

	public int getMaxNormalBond() {
		return maxNormalBond;
	}

	public void setMaxNormalBond(int maxNormalBond) {
		this.maxNormalBond = maxNormalBond;
	}

	public int getMaxIdolizeBond() {
		return maxIdolizeBond;
	}

	public void setMaxIdolizeBond(int maxIdolizeBond) {
		this.maxIdolizeBond = maxIdolizeBond;
	}

	@Override
	public String toString() {
		return "UnitDTO [unitId=" + unitId + ", unitNumber=" + unitNumber + ", unitTypeId="
				+ unitTypeId + ", name=" + name + ", normalCardId=" + normalCardId
				+ ", idolizeCardId=" + idolizeCardId + ", maxLevelCardId=" + maxLevelCardId
				+ ", maxBondCardId=" + maxBondCardId + ", allMaxCardId=" + allMaxCardId
				+ ", normalAvatarPath=" + normalAvatarPath + ", idolizeAvatarPath="
				+ idolizeAvatarPath + ", normalCGAssetId=" + normalCGAssetId
				+ ", idolizeCGAssetId=" + idolizeCGAssetId + ", rarity=" + rarity + ", attrId="
				+ attrId + ", defaultSkillId=" + defaultSkillId + ", skillAssetVoiceId="
				+ skillAssetVoiceId + ", defaultLeaderSkillId=" + defaultLeaderSkillId
				+ ", message=" + message + ", maxNormalBond=" + maxNormalBond + ", maxIdolizeBond="
				+ maxIdolizeBond + ", maxNormalLevel=" + maxNormalLevel + ", maxIdolizeLevel="
				+ maxIdolizeLevel + ", levelUpPatternId=" + levelUpPatternId + ", maxStamina="
				+ maxStamina + ", maxSmile=" + maxSmile + ", maxPure=" + maxPure + ", maxCool="
				+ maxCool + ", idolizeCost=" + idolizeCost + "]";
	}

}
