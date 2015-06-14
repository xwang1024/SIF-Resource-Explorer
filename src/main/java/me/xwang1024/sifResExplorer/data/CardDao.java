package me.xwang1024.sifResExplorer.data;

import java.sql.SQLException;
import java.util.List;

import me.xwang1024.sifResExplorer.dto.CardBaseDTO;
import me.xwang1024.sifResExplorer.dto.CardDTO;
import me.xwang1024.sifResExplorer.dto.CardLayerDTO;

public interface CardDao {
	public List<CardBaseDTO> getCardBase(int cardBaseId) throws SQLException;

	public CardLayerDTO getCardLayer(int cardLayerId) throws SQLException;
	
	public CardDTO getCard(int cardId) throws SQLException;
	
	public List<Integer> getCardIdList() throws SQLException;
}
