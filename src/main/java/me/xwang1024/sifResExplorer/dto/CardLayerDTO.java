package me.xwang1024.sifResExplorer.dto;

public class CardLayerDTO {
	private int cardLayerId;
	private String cardLayerAsset;

	public int getCardLayerId() {
		return cardLayerId;
	}

	public void setCardLayerId(int cardLayerId) {
		this.cardLayerId = cardLayerId;
	}

	public String getCardLayerAsset() {
		return cardLayerAsset;
	}

	public void setCardLayerAsset(String cardLayerAsset) {
		this.cardLayerAsset = cardLayerAsset;
	}

	@Override
	public String toString() {
		return "CardLayerDTO [cardLayerId=" + cardLayerId + ", cardLayerAsset=" + cardLayerAsset
				+ "]";
	}

}
