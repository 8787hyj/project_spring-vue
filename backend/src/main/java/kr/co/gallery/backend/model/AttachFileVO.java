package kr.co.gallery.backend.model;


public class AttachFileVO {

	/* id */
	private Integer fileId;
	
	/* 파일 이름 */
	private String fileName;
	
	/* 경로 */
	private String uploadPath;
	
	/* uuid */
	private String uuid;
	
	private Integer boardid;

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
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
		return "AttachFileVO [fileId=" + fileId + ", fileName=" + fileName + ", uploadPath=" + uploadPath + ", uuid="
				+ uuid + ", boardid=" + boardid + "]";
	}

	



	
	
	
	
}
