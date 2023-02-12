package kr.co.gallery.backend.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.gallery.backend.model.AttachFileVO;
import kr.co.gallery.backend.model.AttachImageVO;
import kr.co.gallery.backend.model.BoardVO;
import kr.co.gallery.backend.model.BoardsImageVO;

@Repository
public interface BoardDao {

	public List<BoardVO> list();
	
	/* 게시판 등록 */
	public Integer boardEnroll(BoardVO vo);
	/* 이미지 등록 */
	public Integer imageEnroll(AttachImageVO vo);

	public Integer BoardsImageEnroll(Integer boardid, Integer imageid);

	public BoardVO openPostView(Integer id);

	public Integer boardUpdate(BoardVO boards);

	public Integer imageUpdate(AttachImageVO attach);

	public Integer BoardsImageUpdate(Integer boardId, Integer imageId);

	public BoardsImageVO boardsImageSelect(Integer boardId);

	public Integer fileEnroll(AttachFileVO attach);

	public Integer fileUpdate(AttachFileVO attach);
	
}
