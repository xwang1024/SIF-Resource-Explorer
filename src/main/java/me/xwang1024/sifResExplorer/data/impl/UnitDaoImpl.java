package me.xwang1024.sifResExplorer.data.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.xwang1024.sifResExplorer.config.SIFConfig;
import me.xwang1024.sifResExplorer.config.SIFConfig.ConfigName;
import me.xwang1024.sifResExplorer.data.UnitDao;
import me.xwang1024.sifResExplorer.dto.LeaderSkillDTO;
import me.xwang1024.sifResExplorer.dto.SkillDTO;
import me.xwang1024.sifResExplorer.dto.SkillLevelDetailDTO;
import me.xwang1024.sifResExplorer.dto.UnitDTO;

public class UnitDaoImpl implements UnitDao {
	private String dbPath;
	private Connection conn;

	public UnitDaoImpl() throws ClassNotFoundException, SQLException, FileNotFoundException {
		dbPath = SIFConfig.getInstance().get(ConfigName.dbPath);
		File cardDbFile = new File(new File(dbPath), "unit/unit.db_");
		if (!cardDbFile.exists()) {
			throw new FileNotFoundException(cardDbFile.getAbsolutePath() + " doesn't exist!");
		}
		dbPath = cardDbFile.getAbsolutePath();
		conn = SqlLiteConnection.getInstance().getConnection(dbPath);
	}

	@Override
	public List<Integer> getUnitIdList() throws SQLException {
		List<Integer> list = new ArrayList<Integer>();
		PreparedStatement preStatement = conn.prepareStatement("select unit_id from unit_m");
		ResultSet rs = preStatement.executeQuery();
		while (rs.next()) {
			Integer i = new Integer(rs.getInt("unit_id"));
			list.add(i);
		}
		return list;
	}

	@Override
	public List<Integer> getUnitNumList() throws SQLException {
		List<Integer> list = new ArrayList<Integer>();
		PreparedStatement preStatement = conn.prepareStatement("select unit_number from unit_m");
		ResultSet rs = preStatement.executeQuery();
		while (rs.next()) {
			Integer i = new Integer(rs.getInt("unit_number"));
			list.add(i);
		}
		return list;
	}

	@Override
	public UnitDTO getUnitById(int id) throws SQLException {
		PreparedStatement preStatement = conn
				.prepareStatement("select * from unit_m where unit_id=?");
		preStatement.setInt(1, id);
		ResultSet rs = preStatement.executeQuery();
		if (rs.next()) {
			UnitDTO dto = new UnitDTO();
			dto.setUnitId(rs.getInt("unit_id"));
			dto.setUnitNumber(rs.getInt("unit_number"));
			dto.setUnitTypeId(rs.getInt("unit_type_id"));
			dto.setEponym(rs.getString("eponym"));
			dto.setName(rs.getString("name"));
			dto.setNormalCardId(rs.getInt("normal_card_id"));
			dto.setIdolizeCardId(rs.getInt("rank_max_card_id"));
			dto.setMaxBondCardId(0);
			dto.setMaxLevelCardId(0);
			dto.setAllMaxCardId(rs.getInt("rank_max_card_id"));
			dto.setNormalAvatarPath(rs.getString("normal_icon_asset"));
			dto.setIdolizeAvatarPath(rs.getString("rank_max_icon_asset"));
			dto.setNormalCGAssetId(rs.getInt("normal_unit_navi_asset_id"));
			dto.setIdolizeCGAssetId(rs.getInt("rank_max_unit_navi_asset_id"));
			dto.setRarity(rs.getInt("rarity"));
			dto.setAttrId(rs.getInt("attribute_id"));
			dto.setDefaultSkillId(rs.getInt("default_unit_skill_id"));
			dto.setSkillAssetVoiceId(0);
			dto.setDefaultLeaderSkillId(rs.getInt("default_leader_skill_id"));
			dto.setMessage("");
			dto.setMaxNormalBond(rs.getInt("before_love_max"));
			dto.setMaxIdolizeBond(rs.getInt("after_love_max"));
			dto.setMaxNormalLevel(rs.getInt("before_level_max"));
			dto.setMaxIdolizeLevel(rs.getInt("after_level_max"));
			dto.setLevelUpPatternId(rs.getInt("unit_level_up_pattern_id"));
			dto.setMaxStamina(rs.getInt("hp_max"));
			dto.setMaxSmile(rs.getInt("smile_max"));
			dto.setMaxPure(rs.getInt("pure_max"));
			dto.setMaxCool(rs.getInt("cool_max"));
			dto.setIdolizeCost(rs.getInt("rank_up_cost"));
			return dto;
		}
		return null;
	}

	@Override
	public UnitDTO getUnitByNum(int num) throws SQLException {
		PreparedStatement preStatement = conn
				.prepareStatement("select * from unit_m where unit_number=?");
		preStatement.setInt(1, num);
		ResultSet rs = preStatement.executeQuery();
		if (rs.next()) {
			UnitDTO dto = new UnitDTO();
			dto.setUnitId(rs.getInt("unit_id"));
			dto.setUnitNumber(rs.getInt("unit_number"));
			dto.setUnitTypeId(rs.getInt("unit_type_id"));
			dto.setEponym(rs.getString("eponym"));
			dto.setName(rs.getString("name"));
			dto.setNormalCardId(rs.getInt("normal_card_id"));
			dto.setIdolizeCardId(rs.getInt("rank_max_card_id"));
			dto.setMaxBondCardId(0);
			dto.setMaxLevelCardId(0);
			dto.setAllMaxCardId(rs.getInt("rank_max_card_id"));
			dto.setNormalAvatarPath(rs.getString("normal_live_asset"));
			dto.setIdolizeAvatarPath(rs.getString("level_love_max_live_asset"));
			dto.setNormalCGAssetId(rs.getInt("normal_unit_navi_asset_id"));
			dto.setIdolizeCGAssetId(rs.getInt("rank_max_unit_navi_asset_id"));
			dto.setRarity(rs.getInt("rarity"));
			dto.setAttrId(rs.getInt("attribute_id"));
			dto.setDefaultSkillId(rs.getInt("default_unit_skill_id"));
			dto.setSkillAssetVoiceId(0);
			dto.setDefaultLeaderSkillId(rs.getInt("default_leader_skill_id"));
			dto.setMessage(rs.getString("unit_message"));
			dto.setMaxNormalBond(rs.getInt("before_love_max"));
			dto.setMaxIdolizeBond(rs.getInt("after_love_max"));
			dto.setMaxNormalLevel(rs.getInt("before_level_max"));
			dto.setMaxIdolizeLevel(rs.getInt("after_level_max"));
			dto.setLevelUpPatternId(rs.getInt("unit_level_up_pattern_id"));
			dto.setMaxStamina(rs.getInt("hp_max"));
			dto.setMaxSmile(rs.getInt("smile_max"));
			dto.setMaxPure(rs.getInt("pure_max"));
			dto.setMaxCool(rs.getInt("cool_max"));
			dto.setIdolizeCost(rs.getInt("rank_up_cost"));
			return dto;
		}
		return null;
	}

	@Override
	public List<UnitDTO> getAllUnits() throws SQLException {
		List<UnitDTO> list = new ArrayList<UnitDTO>();
		PreparedStatement preStatement = conn.prepareStatement("select * from unit_m");
		ResultSet rs = preStatement.executeQuery();
		while (rs.next()) {
			UnitDTO dto = new UnitDTO();
			dto.setUnitId(rs.getInt("unit_id"));
			dto.setUnitNumber(rs.getInt("unit_number"));
			dto.setUnitTypeId(rs.getInt("unit_type_id"));
			dto.setEponym(rs.getString("eponym"));
			dto.setName(rs.getString("name"));
			dto.setNormalCardId(rs.getInt("normal_card_id"));
			dto.setIdolizeCardId(rs.getInt("rank_max_card_id"));
			dto.setMaxBondCardId(0);
			dto.setMaxLevelCardId(0);
			dto.setAllMaxCardId(rs.getInt("rank_max_card_id"));
			dto.setNormalAvatarPath(rs.getString("normal_icon_asset"));
			dto.setIdolizeAvatarPath(rs.getString("rank_max_icon_asset"));
			dto.setNormalCGAssetId(rs.getInt("normal_unit_navi_asset_id"));
			dto.setIdolizeCGAssetId(rs.getInt("rank_max_unit_navi_asset_id"));
			dto.setRarity(rs.getInt("rarity"));
			dto.setAttrId(rs.getInt("attribute_id"));
			dto.setDefaultSkillId(rs.getInt("default_unit_skill_id"));
			dto.setSkillAssetVoiceId(0);
			dto.setDefaultLeaderSkillId(rs.getInt("default_leader_skill_id"));
			dto.setMessage("");
			dto.setMaxNormalBond(rs.getInt("before_love_max"));
			dto.setMaxIdolizeBond(rs.getInt("after_love_max"));
			dto.setMaxNormalLevel(rs.getInt("before_level_max"));
			dto.setMaxIdolizeLevel(rs.getInt("after_level_max"));
			//dto.setDefaultSkillCapacity(rs.getInt("default_removable_skill_capacity"));
			//dto.setMaxSkillCapacity(rs.getInt("max_removable_skill_capacity"));
			dto.setLevelUpPatternId(rs.getInt("unit_level_up_pattern_id"));
			dto.setMaxStamina(rs.getInt("hp_max"));
			dto.setMaxSmile(rs.getInt("smile_max"));
			dto.setMaxPure(rs.getInt("pure_max"));
			dto.setMaxCool(rs.getInt("cool_max"));
			dto.setIdolizeCost(rs.getInt("rank_up_cost"));
			list.add(dto);
		}
		return list;
	}

	@Override
	public String getAttrName(int attrId) throws SQLException {
		PreparedStatement preStatement = conn
				.prepareStatement("select name from unit_attribute_m where attribute_id=?");
		preStatement.setInt(1, attrId);
		ResultSet rs = preStatement.executeQuery();
		if (rs.next()) {
			return rs.getString("name");
		}
		return "Encrypted";
	}

	@Override
	public String getUnitNameImgPath(int type_id) throws SQLException {
		PreparedStatement preStatement = conn
				.prepareStatement("select name_image_asset from unit_type_m where unit_type_id = ?");
		preStatement.setInt(1, type_id);
		ResultSet rs = preStatement.executeQuery();
		if (rs.next()) {
			return rs.getString("name_image_asset");
		}
		return null;
	}

	@Override
	public String getRarityName(int rarityId) throws SQLException {
		String result = "";
		switch(rarityId){
		case 1:
			result = "N";break;
		case 2:
			result = "R";break;
		case 3:
			result = "SR";break;
		case 4:
			result = "UR";break;
		case 5:
			result = "SSR";break;
		}
		return result;
	}

	@Override
	public LeaderSkillDTO getLeaderSkill(int leaderSkillId) throws SQLException {
		PreparedStatement preStatement = conn
				.prepareStatement("select * from unit_leader_skill_m where unit_leader_skill_id=?");
		preStatement.setInt(1, leaderSkillId);
		ResultSet rs = preStatement.executeQuery();
		if (rs.next()) {
			LeaderSkillDTO dto = new LeaderSkillDTO();
			dto.setLeaderSkillId(rs.getInt("unit_leader_skill_id"));
			dto.setName(rs.getString("name"));
			dto.setDescription(rs.getString("description"));
			return dto;
		}
		return null;
	}

	@Override
	public SkillDTO getSkill(int skillId) throws SQLException {
		PreparedStatement preStatement = conn
				.prepareStatement("select * from unit_skill_m where unit_skill_id=?");
		preStatement.setInt(1, skillId);
		ResultSet rs = preStatement.executeQuery();
		if (rs.next()) {
			SkillDTO dto = new SkillDTO();
			dto.setSkillId(rs.getInt("unit_skill_id"));
			dto.setName(rs.getString("name"));
			dto.setSkillNameAsset(rs.getString("skill_name_asset"));
			dto.setMaxLevel(rs.getInt("max_level"));
			dto.setEffectType(rs.getInt("skill_effect_type"));
			dto.setDischargeType(rs.getInt("discharge_type"));
			dto.setTriggerType(rs.getInt("trigger_type"));
			return dto;
		}
		return null;
	}

	@Override
	public List<SkillLevelDetailDTO> getSkillLevelDetail(int skillId) throws SQLException {
		List<SkillLevelDetailDTO> list = new ArrayList<SkillLevelDetailDTO>();
		PreparedStatement preStatement = conn
				.prepareStatement("select * from unit_skill_level_m where unit_skill_id=?");
		preStatement.setInt(1, skillId);
		ResultSet rs = preStatement.executeQuery();
		while (rs.next()) {
			SkillLevelDetailDTO dto = new SkillLevelDetailDTO();
			dto.setSkillId(rs.getInt("unit_skill_id"));
			dto.setSkillLevel(rs.getInt("skill_level"));
			dto.setDescription(rs.getString("description"));
			dto.setEffectRange(rs.getInt("effect_range"));
			dto.setEffectValue(rs.getDouble("effect_value"));
			dto.setDischargeTime(rs.getDouble("discharge_time"));
			dto.setTriggerValue(rs.getInt("trigger_value"));
			dto.setActivationRate(rs.getInt("activation_rate"));
			list.add(dto);
		}
		return list;
	}

	@Override
	public String getSkillEffectTypeName(int skillEffectType) {
		switch (skillEffectType) {
		case 0:
			return "<Unavailable>";
		case 4:
			return "Discount+";
		case 5:
			return "Discount++";
		case 9:
			return "Life+";
		case 11:
			return "Score+";
		default:
			System.err.println("Unknown Effect ID: " + skillEffectType);
			return "<Unknown>";
		}
	}

	@Override
	public String getSkillTriggerTypeName(int triggerType) {
		switch (triggerType) {
		case 0:
			return "<Unavailable>";
		case 1:
			return "Time";
		case 3:
			return "Note";
		case 4:
			return "Combo";
		case 5:
			return "Score";
		case 6:
			return "Perfect";
		case 12:
			return "Star";
		default:
			System.err.println("Unknown Trigger ID: " + triggerType);
			return "<Unknown>";
		}
	}

	public static void main(String[] args) throws Exception {
		SIFConfig.getInstance().loadConfig();
		List<SkillLevelDetailDTO> l = new UnitDaoImpl().getSkillLevelDetail(1);
		for (SkillLevelDetailDTO dto : l) {
			System.out.println(dto);
		}
//		System.out.println(new UnitDaoImpl().getSkill(1));
//		System.out.println(new UnitDaoImpl().getRarityName(1));
	}

	@Override
	public String getUnitNaviAsset(int unitNaviAssetId) throws SQLException {
		PreparedStatement preStatement = conn
				.prepareStatement("select unit_navi_asset from unit_navi_asset_m where unit_navi_asset_id=?");
		preStatement.setInt(1, unitNaviAssetId);
		ResultSet rs = preStatement.executeQuery();
		if (rs.next()) {
			return rs.getString("unit_navi_asset");
		}
		return null;
	}

}
