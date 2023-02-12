package kr.co.gallery.backend.model;


public class AttachImageVO {

	/* id */
	private Integer imageId;
	
	/* 파일 이름 */
	private String fileName;
	
	/* 경로 */
	private String uploadPath;
	
	/* uuid */
	private String uuid;
	
	private Integer boardid;

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getBoardid() {
		return boardid;
	}

	public void setBoardid(Integer boardid) {
		this.boardid = boardid;
	}

	@Override
	public String toString() {
		return "AttachImageVO [imageId=" + imageId + ", fileName=" + fileName + ", uploadPath=" + uploadPath + ", uuid="
				+ uuid + ", boardid=" + boardid + "]";
	}



	
	
	
	
}
