package kr.co.gallery.backend.model;

import java.util.List;

public class BoardVO {
	
	private Integer boardno;
	
	private String content;
	
	private String title;
	
	private String writer;
	
	private Integer boardid;
	
	private List<AttachImageVO> imageList;
	private List<AttachFileVO> fileList;
	public Integer getBoardno() {
		return boardno;
	}
	public void setBoardno(Integer boardno) {
		this.boardno = boardno;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Integer getBoardid() {
		return boardid;
	}
	public void setBoardid(Integer boardid) {
		this.boardid = boardid;
	}
	public List<AttachImageVO> getImageList() {
		return imageList;
	}
	public void setImageList(List<AttachImageVO> imageList) {
		this.imageList = imageList;
	}
	public List<AttachFileVO> getFileList() {
		return fileList;
	}
	public void setFileList(List<AttachFileVO> fileList) {
		this.fileList = fileList;
	}
	@Override
	public String toString() {
		return "BoardVO [boardno=" + boardno + ", content=" + content + ", title=" + title + ", writer=" + writer
				+ ", boardid=" + boardid + ", imageList=" + imageList + ", fileList=" + fileList + "]";
	}
	
	
	


}
