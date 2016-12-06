package cn.ms.gateway.base.processor.bypass;

/**
 * 分流消息
 * 
 * @author lry
 */
public class BypassMessage {
	
	private String id;// 交易ID
	private double price;// 交易金额

	public BypassMessage() {
	}

	public BypassMessage(String id, double price) {
		this.id = id;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}