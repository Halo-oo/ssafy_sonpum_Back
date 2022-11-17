package com.ssafy.vue.model;

public class BoardReportImageDto {

	private int boardReportImageId;
	private int articleNo;
	private String saveFolder;
	private String originalFileName;
	private String saveFileName;
	
	public int getBoardReportImageId() {
		return boardReportImageId;
	}
	
	public void setBoardReportImageId(int boardReportImageId) {
		this.boardReportImageId = boardReportImageId;
	}
	
	public int getArticleNo() {
		return articleNo;
	}
	
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
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
		return "FileInfoDto [boardReportImageId=" + boardReportImageId + ", articleNo=" + articleNo + ", saveFolder="
				+ saveFolder + ", originalFileName=" + originalFileName + ", saveFileName=" + saveFileName + "]";
	}

}
