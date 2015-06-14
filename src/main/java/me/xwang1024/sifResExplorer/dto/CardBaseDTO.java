package me.xwang1024.sifResExplorer.dto;

public class CardBaseDTO {
	private int cardBaseId;
	private int layerOrder;
	private int cardLayerId;

	/**
	 * 获得卡片ID
	 * 
	 * @return
	 */
	public int getCardBaseId() {
		return cardBaseId;
	}

	/**
	 * 对应card_base_m表中的card_base_id
	 * 
	 * @param cardBaseId
	 */
	public void setCardBaseId(int cardBaseId) {
		this.cardBaseId = cardBaseId;
	}

	/**
	 * 获得所在的层次
	 * 
	 * @return
	 */
	public int getLayerOrder() {
		return layerOrder;
	}

	/**
	 * 对应card_base_m表中的layer_order
	 * 
	 * @param layerOrder
	 */
	public void setLayerOrder(int layerOrder) {
		this.layerOrder = layerOrder;
	}

	/**
	 * 获得对应的CardLayer的ID
	 * 
	 * @return
	 */
	public int getCardLayerId() {
		return cardLayerId;
	}

	/**
	 * 对应card_base_m表中的card_layer_id
	 * 
	 * @param cardLayerId
	 */
	public void setCardLayerId(int cardLayerId) {
		this.cardLayerId = cardLayerId;
	}

	@Override
	public String toString() {
		return "CardBaseDTO [cardBaseId=" + cardBaseId + ", layerOrder=" + layerOrder
				+ ", cardLayerId=" + cardLayerId + "]";
	}

}
