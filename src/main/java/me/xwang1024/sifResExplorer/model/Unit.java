package me.xwang1024.sifResExplorer.model;

import java.util.Arrays;

public class Unit {
	private int id;
	private int unitNo;
	private String name;
	private String eponym;
	private Card[] card; // normal, idolize normal, idolize rankMax, idolize
							// bondMax, idolize doubleMax
	private String[] avatar; // normal, idolize
	private String[] cg; // normal, idolize
	private String rarity;
	private String attribute;
	private UnitSkill unitSkill;
	private String skillName;
	private String skillEffect;
	private String skillTrigger;
	private LeaderSkill leaderSkill;
	private String leaderSkillType;
	private String message;
	private int stamina;
	private int smile;
	private int pure;
	private int cool;
	private int rankupCost;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(int unitNo) {
		this.unitNo = unitNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEponym() {
		return eponym;
	}

	public void setEponym(String eponym) {
		this.eponym = eponym;
	}

	public Card[] getCard() {
		return card;
	}

	public void setCard(Card[] card) {
		this.card = card;
	}

	public String[] getAvatar() {
		return avatar;
	}

	public void setAvatar(String[] avatar) {
		this.avatar = avatar;
	}

	public String[] getCg() {
		return cg;
	}

	public void setCg(String[] cg) {
		this.cg = cg;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public UnitSkill getUnitSkill() {
		return unitSkill;
	}

	public void setUnitSkill(UnitSkill unitSkill) {
		this.unitSkill = unitSkill;
	}

	public LeaderSkill getLeaderSkill() {
		return leaderSkill;
	}

	public void setLeaderSkill(LeaderSkill leaderSkill) {
		this.leaderSkill = leaderSkill;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getSmile() {
		return smile;
	}

	public void setSmile(int smile) {
		this.smile = smile;
	}

	public int getPure() {
		return pure;
	}

	public void setPure(int pure) {
		this.pure = pure;
	}

	public int getCool() {
		return cool;
	}

	public void setCool(int cool) {
		this.cool = cool;
	}

	public int getRankupCost() {
		return rankupCost;
	}

	public void setRankupCost(int rankupCost) {
		this.rankupCost = rankupCost;
	}

	public String getLeaderSkillType() {
		return leaderSkillType;
	}

	public void setLeaderSkillType(String leaderSkillType) {
		this.leaderSkillType = leaderSkillType;
	}

	public String getSkillEffect() {
		return skillEffect;
	}

	public void setSkillEffect(String skillEffect) {
		this.skillEffect = skillEffect;
	}

	public String getSkillTrigger() {
		return skillTrigger;
	}

	public void setSkillTrigger(String skillTrigger) {
		this.skillTrigger = skillTrigger;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	@Override
	public String toString() {
		return "Unit [id=" + id + ", unitNo=" + unitNo + ", name=" + name + ", eponym=" + eponym
				+ ", card=" + Arrays.toString(card) + ", avatar=" + Arrays.toString(avatar)
				+ ", cg=" + Arrays.toString(cg) + ", rarity=" + rarity + ", attribute=" + attribute
				+ ", unitSkill=" + unitSkill + ", skillName=" + skillName + ", skillEffect="
				+ skillEffect + ", skillTrigger=" + skillTrigger + ", leaderSkill=" + leaderSkill
				+ ", leaderSkillType=" + leaderSkillType + ", message=" + message + ", stamina="
				+ stamina + ", smile=" + smile + ", pure=" + pure + ", cool=" + cool
				+ ", rankupCost=" + rankupCost + "]";
	}

}
