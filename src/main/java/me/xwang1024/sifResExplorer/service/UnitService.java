package me.xwang1024.sifResExplorer.service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import me.xwang1024.sifResExplorer.config.SIFConfig;
import me.xwang1024.sifResExplorer.data.CardDao;
import me.xwang1024.sifResExplorer.data.ImagDao;
import me.xwang1024.sifResExplorer.data.UnitDao;
import me.xwang1024.sifResExplorer.data.impl.CardDaoImpl;
import me.xwang1024.sifResExplorer.data.impl.ImagDaoImpl;
import me.xwang1024.sifResExplorer.data.impl.UnitDaoImpl;
import me.xwang1024.sifResExplorer.dto.SkillDTO;
import me.xwang1024.sifResExplorer.dto.UnitDTO;
import me.xwang1024.sifResExplorer.model.Unit;

public class UnitService {
	private static UnitService instance;
	private ImagDao imageDao;
	private UnitDao unitDao;
	private CardDao cardDao;
	private List<String> nameList;
	private List<String> attrList;
	private List<String> rarityList;
	private List<String> skillEffectList;
	private List<String> skillTriggerList;
	private List<String> leaderSkillTypeList;
	private List<Unit> unitList;

	private UnitService() throws ClassNotFoundException, FileNotFoundException, SQLException {
		imageDao = new ImagDaoImpl();
		unitDao = new UnitDaoImpl();
		cardDao = new CardDaoImpl();
		initUnitList();
	}

	public static UnitService getInstance() throws ClassNotFoundException, FileNotFoundException,
			SQLException {
		if (instance == null) {
			instance = new UnitService();
		}
		return instance;
	}

	private void initUnitList() throws SQLException {
		List<UnitDTO> dtoList = unitDao.getAllUnits();
		unitList = new ArrayList<Unit>(dtoList.size());

		// ------Statistics Set------
		Set<String> nameSet = new HashSet<String>();
		Set<String> attrSet = new HashSet<String>();
		Set<String> raritySet = new HashSet<String>();
		Set<String> skillEffectSet = new HashSet<String>();
		Set<String> skillTriggerSet = new HashSet<String>();
		Set<String> leaderSkillTypeSet = new HashSet<String>();
		// --------------------------

		for (UnitDTO dto : dtoList) {
			Unit u = new Unit();
			u.setId(dto.getUnitId());
			u.setUnitNo(dto.getUnitNumber());
			u.setName(dto.getName());
			String eponym = dto.getEponym();
			if (eponym == null || eponym.equals("null")) {
				u.setEponym("<Empty>");
			} else {
				u.setEponym(dto.getEponym());
			}
			String attr = unitDao.getAttrName(dto.getAttrId());
			u.setAttribute(attr);
			String rarity = unitDao.getRarityName(dto.getRarity());
			u.setRarity(rarity);
			String skillName;
			String skillEffectType;
			String skillEffectTrigger;
			if (dto.getDefaultSkillId() != 0) {
				int skillId = dto.getDefaultSkillId();
				SkillDTO skillDto = unitDao.getSkill(skillId);
				skillName = skillDto.getName();
				skillEffectType = unitDao.getSkillEffectTypeName(skillDto.getEffectType());
				skillEffectTrigger = unitDao.getSkillTriggerTypeName(skillDto.getTriggerType());
			} else {
				skillName = "<Unavailable>";
				skillEffectType = "<Unavailable>";
				skillEffectTrigger = "<Unavailable>";
			}
			u.setSkillName(skillName);
			u.setSkillEffect(skillEffectType);
			u.setSkillTrigger(skillEffectTrigger);
			String leaderSkillType;
			if (dto.getDefaultLeaderSkillId() != 0) {
				leaderSkillType = unitDao.getLeaderSkill(dto.getDefaultLeaderSkillId()).getName();
			} else {
				leaderSkillType = "<Unavailable>";
			}
			u.setLeaderSkillType(leaderSkillType);
			u.setMessage(dto.getMessage());
			u.setStamina(dto.getMaxStamina());
			u.setSmile(dto.getMaxSmile());
			u.setPure(dto.getMaxPure());
			u.setCool(dto.getMaxCool());

			nameSet.add(dto.getName());
			attrSet.add(attr);
			raritySet.add(rarity);
			skillEffectSet.add(skillEffectType);
			skillTriggerSet.add(skillEffectTrigger);
			leaderSkillTypeSet.add(leaderSkillType);
			unitList.add(u);
		}
		nameList = new ArrayList<String>(nameSet);
		Collections.sort(nameList);
		attrList = new ArrayList<String>(attrSet);
		Collections.sort(attrList);
		rarityList = new ArrayList<String>(raritySet);
		Collections.sort(rarityList);
		skillEffectList = new ArrayList<String>(skillEffectSet);
		Collections.sort(skillEffectList);
		skillTriggerList = new ArrayList<String>(skillTriggerSet);
		Collections.sort(skillTriggerList);
		leaderSkillTypeList = new ArrayList<String>(leaderSkillTypeSet);
		Collections.sort(leaderSkillTypeList);
	}

	public List<String> getNameList() {
		return nameList;
	}

	public List<String> getAttrList() {
		return attrList;
	}

	public List<String> getRarityList() {
		return rarityList;
	}

	public List<String> getLeaderSkillTypeList() {
		return leaderSkillTypeList;
	}

	public List<String> getSkillEffectList() {
		return skillEffectList;
	}

	public List<String> getSkillTriggerList() {
		return skillTriggerList;
	}

	public List<Unit> getUnitList() {
		return unitList;
	}

	public List<Unit> getUnitListByConditions(String keywords, String name, String attr,
			String rarity, String leaderSkill, String skillEffect, String skillTrigger) {
		List<Unit> l = new LinkedList<Unit>();
		for (Unit u : unitList) {
			if (keywords != null) {
				if (!u.toFlatString().contains(keywords)) {
					continue;
				}
			}
			if (name != null) {
				if (!u.getName().equals(name)) {
					continue;
				}
			}
			if (attr != null) {
				if (!u.getAttribute().equals(attr)) {
					continue;
				}
			}
			if (rarity != null) {
				if (!u.getRarity().equals(rarity)) {
					continue;
				}
			}
			if (leaderSkill != null) {
				if (!u.getLeaderSkillType().equals(leaderSkill)) {
					continue;
				}
			}
			if (skillEffect != null) {
				if (!u.getSkillEffect().equals(skillEffect)) {
					continue;
				}
			}
			if (skillTrigger != null) {
				if (!u.getSkillTrigger().equals(skillTrigger)) {
					continue;
				}
			}
			l.add(u);
		}
		return l;
	}

	public static void main(String[] args) throws Exception {
		SIFConfig.getInstance().loadConfig();
		UnitService.getInstance().initUnitList();
	}
}
