package me.xwang1024.sifResExplorer.data;

import java.sql.SQLException;
import java.util.List;

import me.xwang1024.sifResExplorer.dto.LeaderSkillDTO;
import me.xwang1024.sifResExplorer.dto.SkillDTO;
import me.xwang1024.sifResExplorer.dto.SkillLevelDetailDTO;
import me.xwang1024.sifResExplorer.dto.UnitDTO;

public interface UnitDao {
	public List<Integer> getUnitIdList() throws SQLException;

	public List<Integer> getUnitNumList() throws SQLException;

	public UnitDTO getUnitById(int id) throws SQLException;

	public UnitDTO getUnitByNum(int num) throws SQLException;

	public List<UnitDTO> getAllUnits() throws SQLException;

	public String getAttrName(int attrId) throws SQLException;

	public String getRarityName(int rarityId) throws SQLException;

	public LeaderSkillDTO getLeaderSkill(int leaderSkillId) throws SQLException;

	public SkillDTO getSkill(int skillId) throws SQLException;

	public List<SkillLevelDetailDTO> getSkillLevelDetail(int skillId) throws SQLException;
	
	public String getSkillEffectTypeName(int skillEffectType);
	
	public String getTriggerTypeName(int triggerType);
}
