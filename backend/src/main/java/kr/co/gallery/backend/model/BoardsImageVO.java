package kr.co.gallery.backend.model;

public class BoardsImageVO {

	private Integer boardId;
	private Integer imageId;
	public Integer getBoardId() {
		return boardId;
	}
	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}
	public Integer getImageId() {
		return imageId;
	}
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}
	@Override
	public String toString() {
		return "BoardsImageVO [boardId=" + boardId + ", imageId=" + imageId + "]";
	}

	
	
	
}
