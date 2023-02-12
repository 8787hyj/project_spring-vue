package kr.co.gallery.backend.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gallery.backend.dao.BoardDao;
import kr.co.gallery.backend.model.AttachImageVO;
import kr.co.gallery.backend.model.BoardVO;
import kr.co.gallery.backend.service.BoardService;

@RestController
public class BoardController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    BoardService boardService;
    
    @Autowired
    BoardDao boardDao;
    
	/* 게시글 리스트 */
    @GetMapping("/api/board/post/list")
    public ResponseEntity<List<BoardVO>> getBoardList() {
    	log.info("getBoardList ... : ");
    	List<BoardVO> result1 = null;
    	try {
    		result1 = boardService.list();
    	}
    	catch(Exception e)
    	{
    		//e.printStackTrace();
    	}
    	return new ResponseEntity<List<BoardVO>>(result1, HttpStatus.OK);
    }
    
	/* 게시글 상세 페이지 */
    @GetMapping("/api/board/post/view/{id}")
    public ResponseEntity<BoardVO> openPostView(@PathVariable("id") Integer id) {
    	log.info("openPostView ... : " + id);
    	BoardVO result1 = boardService.openPostView(id);
    	System.out.println("result1: "+ result1);
    	return new ResponseEntity<BoardVO>(result1, HttpStatus.OK);
    }
    
	/* 이미지 업로드 */
    @PostMapping("/api/board/upload-image")
	public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("image") MultipartFile[] uploadFile) {
		
    	log.info("uploadImage ... : " + uploadFile);
		/* 이미지 파일 체크 */
    	String type = null;
    	for(MultipartFile multipartFile : uploadFile) {
    		File checkfile = new File(multipartFile.getOriginalFilename());
    		
    		try {
				type = Files.probeContentType(checkfile.toPath());
//				log.info("MIME TYPE : " + type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/* 이미지 타입 체크 */
    		if(!type.startsWith("image")) {
    			Map<String, Object> list = null;
    			return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    		}
    	} // end for
    	
    	
		/* 경로 */
		String uploadFolder = "C:\\upload";
		
		/* 날짜 폴더 경로 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		String datePath = str.replace("-", File.separator);
		
		/* 폴더 생성 */
		File uploadPath = new File(uploadFolder, datePath);
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
			
		}
		Map<String, Object> result1 = new HashMap<>();
		
		List<AttachImageVO> list = new ArrayList<>();
		
		for(MultipartFile multipartFile : uploadFile) {
			
			AttachImageVO vo = new AttachImageVO();
			
			/* 파일 이름 */
			String uploadFileName = multipartFile.getOriginalFilename();
			
			/* uuid 적용 파일 이름 */
			String uuid = UUID.randomUUID().toString();
			uploadFileName = uuid + "_" + uploadFileName;
			
			/* 파일 위치, 파일 이름을 합친 File 객체 */
			File saveFile = new File(uploadPath, uploadFileName);
			
			/* map */
			result1.put("name", multipartFile.getOriginalFilename());
			result1.put("url", "/api/display?fileName=");
			result1.put("path", uploadFileName);
			result1.put("datePath", datePath);
			result1.put("uuid", uuid);
			result1.put("uploadFolder", uploadFolder);
			result1.put("type", type);
			
			/* files 값 확인 */
//			log.info("multi : {}", multipartFile);
//			log.info("file name : {}", multipartFile.getOriginalFilename());
//			log.info("context type : {}", multipartFile.getContentType());
//			log.info("size : {}", multipartFile.getSize());
//			log.info("name : {}", multipartFile.getOriginalFilename());
//			log.info("path : {}", uploadFileName);
//			log.info("datePath : {}", datePath);
//			log.info("uuid : {}", uuid);
//			log.info("uploadPath : {}", uploadPath);
//			log.info("save : {}", uploadFileName);
			
			/* vo */
			vo.setFileName(multipartFile.getOriginalFilename());
			vo.setUploadPath(uploadFolder+"/"+datePath);
			vo.setUuid(uuid);
			list.add(vo);
			
			result1.put("imageList", list);
			System.out.println("result1 : "+list);
			
			/* 파일 저장 */
			try {
				multipartFile.transferTo(saveFile);
				
				/* 썸네일 이미지 */
				File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);
				/* BufferedImage 객체 생성, 크기를 지정하여 흰색 도화지를 만듬 */
				BufferedImage bo_image = ImageIO.read(saveFile);
				/* 인자 : 넓이, 높이, 생성될 이미지의 타입 */
				BufferedImage bt_image = new BufferedImage(300, 500, BufferedImage.TYPE_3BYTE_BGR);
				/* 그림을 그릴수 있도록 하는 과정 */
				Graphics2D graphic = bt_image.createGraphics();
				/* 원본 이미지를 썸네일에 지정한 크기로 변경, 인자 : 그렇놓고자 하는 이미지, 좌표x, 좌표y, 넓이, 높이, ImageObserver 객체 */
				graphic.drawImage(bo_image, 0, 0, 300, 500, null);
				/* 썸네일 이미지를 파일로 만듬, 인자 : 파일로 저장할 이미지, 이미지 형식, 썸네일 이미지가 저장될 경로와 이름으로 생성한 File 객체*/
				ImageIO.write(bt_image, "jpg", thumbnailFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			list.add(vo);
		} // end for
		
		
		return new ResponseEntity<Map<String, Object>>(result1, HttpStatus.OK);
	}
    
	/* 이미지 파일 */
    @GetMapping("/api/display")
    public ResponseEntity<byte[]> getImage(String fileName) {
    	
    	log.info("getImage ... : " + fileName);
    	File file = new File("C:\\upload\\" + fileName);
    	
    	ResponseEntity<byte[]> result = null;
    	try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return result;
    }
    
	/* 이미지 등록 */
    @PostMapping("/api/board/goodsEnroll")
    public String boardEnroll(@RequestBody BoardVO boards) {
    	
    	log.info("boardEnroll ... : " + boards.toString());
    	boardService.boardEnroll(boards);
    	
    	return "OK";
    }
   
	/* 게시글 등록 */
    @PatchMapping("/api/board/post/update/{boardId}")
    @CrossOrigin(origins = "http://localhost:8092/")
    public String boardUpdate(@PathVariable("boardId") Integer boardId, @RequestBody BoardVO boards) {
    	
    	log.info("boardUpdate ... : " + boards.toString());
    	log.info("boardId ... : " + boardId);
    	boards.setBoardid(boardId);
    	boardService.boardUpdate(boards);
    	
    	return "OK";
    }
    
	/* 파일 업로드 */
    @PostMapping("/api/board/uploadByAction")
    public ResponseEntity<Map<String, Object>> uploadByNginx(@RequestParam("file") MultipartFile[] uploadFile) {
    	
    	log.info("uploadByNginx .file. : " + uploadFile);
    	
    	String type = null;
    	/* 이미지 파일 체크 */
    	for(MultipartFile multipartFile : uploadFile) {
    		File checkfile = new File(multipartFile.getOriginalFilename());
    		
    		try {
				type = Files.probeContentType(checkfile.toPath());
//				log.info("MIME TYPE : " + type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/* 이미지 타입 체크 */
    		if(!type.startsWith("text")) {
    			Map<String, Object> list = null;
    			return new ResponseEntity<Map<String, Object>>(list, HttpStatus.OK);
    		}
    	} // end for
    	
    	
		/* 경로 */
		String uploadFolder = "C:\\upload";
		
		/* 날짜 폴더 경로 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		String datePath = str.replace("-", File.separator);
		
		/* 폴더 생성 */
		File uploadPath = new File(uploadFolder, datePath);
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
			
		}
		Map<String, Object> result1 = new HashMap<>();
		
		List<AttachImageVO> list = new ArrayList<>();
		
		for(MultipartFile multipartFile : uploadFile) {
			
			AttachImageVO vo = new AttachImageVO();
			
			/* 파일 이름 */
			String uploadFileName = multipartFile.getOriginalFilename();
			
			/* uuid 적용 파일 이름 */
			String uuid = UUID.randomUUID().toString();
			uploadFileName = uuid + "_" + uploadFileName;
			
			/* 파일 위치, 파일 이름을 합친 File 객체 */
			File saveFile = new File(uploadPath, uploadFileName);
			
			/* map */
			result1.put("name", multipartFile.getOriginalFilename());
			result1.put("url", "/api/display?fileName=");
			result1.put("path", uploadFileName);
			result1.put("datePath", datePath);
			result1.put("uuid", uuid);
			result1.put("uploadFolder", uploadFolder);
			result1.put("type", type);
			
			/* files 값 확인 */
//			log.info("multi : {}", multipartFile);
//			log.info("file name : {}", multipartFile.getOriginalFilename());
//			log.info("context type : {}", multipartFile.getContentType());
//			log.info("size : {}", multipartFile.getSize());
//			log.info("name : {}", multipartFile.getOriginalFilename());
//			log.info("path : {}", uploadFileName);
//			log.info("datePath : {}", datePath);
//			log.info("uuid : {}", uuid);
//			log.info("uploadPath : {}", uploadPath);
//			log.info("save : {}", uploadFileName);
			
			/* vo */
			vo.setFileName(uploadFileName);
			vo.setUploadPath(datePath);
			vo.setUuid(uuid);
			
			/* 파일 저장 */
			try {
				multipartFile.transferTo(saveFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
				result1.put("resCode", "400");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(vo);
		} // end for
		return new ResponseEntity<Map<String, Object>>(result1, HttpStatus.OK);
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}