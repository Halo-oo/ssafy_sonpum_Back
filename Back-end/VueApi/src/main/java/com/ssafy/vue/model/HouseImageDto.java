package com.ssafy.vue.model;

import java.math.BigInteger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HouseImageDto : 아파트 매물 이미지", description = "아파트 매물의 이미지를 나타낸다.")
public class HouseImageDto {

	@ApiModelProperty(value = "이미지 번호")
	private int houseImageId;
	@ApiModelProperty(value = "매물 번호")
	private int houseProductId;
	@ApiModelProperty(value = "저장 폴더")
	private String saveFolder;
	@ApiModelProperty(value = "원본 파일 이름")
	private String originalFileName;
	@ApiModelProperty(value = "저장 파일 이름")
	private String saveFileName;
	
	public int getHouseImageId() {
		return houseImageId;
	}
	
	public void setHouseImageId(int houseImageId) {
		this.houseImageId = houseImageId;
	}
	
	public int getHouseProductId() {
		return houseProductId;
	}
	
	public void setHouseProductId(int houseProductId) {
		this.houseProductId = houseProductId;
	}
	
	public String getSaveFolder() {
		return saveFolder;
	}
	
	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}
	
	public String getOriginalFileName() {
		return originalFileName;
	}
	
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	@Override
	public String toString() {
		return "HouseImageDto [houseImageId=" + houseImageId + ", houseProductId=" + houseProductId + ", saveFolder="
				+ saveFolder + ", originalFileName=" + originalFileName + ", saveFileName=" + saveFileName + "]";
	}
	
}
