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
import me.xwang1024.sifResExplorer.data.CardDao;
import me.xwang1024.sifResExplorer.dto.CardBaseDTO;
import me.xwang1024.sifResExplorer.dto.CardDTO;
import me.xwang1024.sifResExplorer.dto.CardLayerDTO;

public class CardDaoImpl implements CardDao {
	private String dbPath;
	private Connection conn;

	public CardDaoImpl() throws ClassNotFoundException, SQLException, FileNotFoundException {
		dbPath = SIFConfig.getInstance().get(ConfigName.dbPath);
		File cardDbFile = new File(new File(dbPath), "unit/card.db_");
		if (!cardDbFile.exists()) {
			throw new FileNotFoundException(cardDbFile.getAbsolutePath() + " doesn't exist!");
		}
		dbPath = cardDbFile.getAbsolutePath();
		conn = SqlLiteConnection.getInstance().getConnection(dbPath);
	}

	@Override
	public List<CardBaseDTO> getCardBase(int cardBaseId) throws SQLException {
		List<CardBaseDTO> list = new ArrayList<CardBaseDTO>(10);
		PreparedStatement preStatement = conn
				.prepareStatement("select * from card_base_m where card_base_id=?");
		preStatement.setInt(1, cardBaseId);
		ResultSet rs = preStatement.executeQuery();
		while (rs.next()) {
			CardBaseDTO dto = new CardBaseDTO();
			dto.setCardBaseId(cardBaseId);
			dto.setLayerOrder(rs.getInt("layer_order"));
			dto.setCardLayerId(rs.getInt("card_layer_id"));
			list.add(dto);
		}
		return list;
	}

	@Override
	public CardLayerDTO getCardLayer(int cardLayerId) throws SQLException {
		PreparedStatement preStatement = conn
				.prepareStatement("select * from card_layer_m where card_layer_id=?");
		preStatement.setInt(1, cardLayerId);
		ResultSet rs = preStatement.executeQuery();
		if (rs.next()) {
			CardLayerDTO dto = new CardLayerDTO();
			dto.setCardLayerId(cardLayerId);
			dto.setCardLayerAsset(rs.getString("card_layer_asset"));
			return dto;
		}
		return null;
	}

	@Override
	public CardDTO getCard(int cardId) throws SQLException {
		PreparedStatement preStatement = conn
				.prepareStatement("select * from card_m where card_id=?");
		preStatement.setInt(1, cardId);
		ResultSet rs = preStatement.executeQuery();
		if (rs.next()) {
			CardDTO dto = new CardDTO();
			dto.setCardId(cardId);
			dto.setCardBaseId(rs.getInt("card_base_id"));
			dto.setUnitNaviAssetId(rs.getInt("unit_navi_asset_id"));
			dto.setNaviLayerOrder(rs.getInt("navi_layer_order"));
			dto.setNaviRotation(rs.getInt("navi_rotation"));
			dto.setNaviMoveX(rs.getInt("navi_move_x"));
			dto.setNaviMoveY(rs.getInt("navi_move_y"));
			dto.setNaviSizeRatio(rs.getDouble("navi_size_ratio"));
			return dto;
		}
		return null;
	}

	@Override
	public List<Integer> getCardIdList() throws SQLException {
		List<Integer> list = new ArrayList<Integer>();
		PreparedStatement preStatement = conn.prepareStatement("select card_id from card_m");
		ResultSet rs = preStatement.executeQuery();
		while (rs.next()) {
			Integer i = new Integer(rs.getInt("card_id"));
			list.add(i);
		}
		return list;
	}
}
