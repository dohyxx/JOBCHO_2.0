package org.jobcho.controller;

import java.util.List;

import org.jobcho.domain.BoardVO;
import org.jobcho.domain.PostVO;
import org.jobcho.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@AllArgsConstructor
@NoArgsConstructor
@Log4j
public class BoardController {
	
	
	@Autowired
	private BoardService service;
	
	
	@GetMapping("/main")
	public void getBoard() {
	}
	
	
	
	/* REST API
	 * 게시판 리스트 조회(PostMan 확인O)
	 * 메인 화면에서 항상 호출
	 */
	@GetMapping(value = "/list", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<BoardVO>> getListBoard(){
		
		List<BoardVO> board = service.getListBoard();
		System.out.println("board: " + board);
		return new ResponseEntity<>(board, HttpStatus.OK);
	}
	
	
	
	/* REST API
	 * 게시판 생성
	 * 
	 */
	@PostMapping("/{team_num}/new")
	@ResponseBody
	public ResponseEntity<BoardVO> insertBoard(@RequestBody BoardVO board,
																				@PathVariable("team_num") int team_num){
		
		System.out.println("컨트롤러 insertBoard");
		board.setMember_num(1);
		board.setTeam_num(team_num);
		int insertCount = service.insertBoard(board);
		log.info("게시판 생성: " + board);
		
		return insertCount == 1
				? new ResponseEntity<>(HttpStatus.OK)
				:  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/*
	 * 게시판 수정
	 * 
	 */
	
	
	/*
	 * 게시판 삭제
	 * 
	 */
	
	
	
	
	
	
	
	
	
	
	
}
