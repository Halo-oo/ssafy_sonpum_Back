package com.ssafy.vue.model;

import java.math.BigInteger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseProductCheckDto : 매물의 안전성 검사", description = "매물의 안전성을 검사한다.")
public class HouseProductCheckDto {

	@ApiModelProperty(value = "매물 유형(다세대, 다가구)")
	private int houseProductType;
	@ApiModelProperty(value = "근저당")
	private BigInteger mortgage;
	@ApiModelProperty(value = "보증금")
	private BigInteger deposit;
	@ApiModelProperty(value = "시세 값")
	private String marketPrice;
	
	public int getHouseProductType() {
		return houseProductType;
	}
	
	public void setHouseProductType(int houseProductType) {
		this.houseProductType = houseProductType;
	}
	
	public BigInteger getMortgage() {
		return mortgage;
	}
	
	public void setMortgage(BigInteger mortgage) {
		this.mortgage = mortgage;
	}
	
	public BigInteger getDeposit() {
		return deposit;
	}
	
	public void setDeposit(BigInteger deposit) {
		this.deposit = deposit;
	}
	
	public String getMarketPrice() {
		return marketPrice;
	}
	
	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	@Override
	public String toString() {
		return "HouseProductCheckDto [houseProductType=" + houseProductType + ", mortgage=" + mortgage + ", deposit="
				+ deposit + ", marketPrice=" + marketPrice + "]";
	}
	
}
