package org.jobcho.controller;


import java.util.HashMap;

import org.jobcho.domain.Criteria;
import org.jobcho.domain.PageInfo;
import org.jobcho.domain.PostVO;
import org.jobcho.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/post/*")
@Log4j
@AllArgsConstructor
public class PostController {

	@Autowired
	private PostService service;
	
	
	//게시글 전체 리스트 + 페이지 처리
	@GetMapping("/list")
	public void getListPost(Criteria cri, Model model, @RequestParam("board_num") int board_num){	
	
		int total = service.getTotalCount(board_num);
		
		log.info("게시글 리스트");
		log.info("전체 글 수: " + total);
		System.out.println("게시글 리스트!!" + board_num);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("board_num", board_num);
		map.put("cri", cri);
		
		model.addAttribute("postList", service.getListPost(map));
		model.addAttribute("pageMaker", new PageInfo(cri, total));
		model.addAttribute("board_num", board_num);
	}
	
	
	//게시글 상세조회
	@GetMapping({"/get", "/update"})
	public void getPost(@RequestParam("post_num") int post_num, 
								    @ModelAttribute("cri") Criteria cri, Model model) { //상세화면에서 목록으로 갈때 페이지처리
		
		log.info("게시글 상세조회: " + post_num);
		
		model.addAttribute("post", service.getPost(post_num));
	}
	
	
	//게시글 생성 1.등록폼 이동
	@GetMapping("/register")
	public void register() {
	}
	
	//게시글 생성 2.DB저장, 리스트로 이동
	@PostMapping("/register")
	public String register(PostVO post, RedirectAttributes rttr) {
		
		log.info("게시글 등록: " + post.getPost_title());
		
		post.setBoard_num(62);
		post.setMember_num(1);
		
		
		rttr.addFlashAttribute("result", service.insertPost(post));
		
		return "redirect:/post/list";
	}
	
	
	//게시글 수정
	@PostMapping("/update")
	public String updatePost(PostVO post, RedirectAttributes rttr,
											 @ModelAttribute("cri") Criteria cri) {
		
		log.info("게시글 수정: " + post.getPost_num());
		
		if(service.updateBoard(post) == 1) {
			rttr.addFlashAttribute("result", " success");
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:/post/list";
	}
	
	
	
	//게시글 삭제
	@PostMapping("/delete")
	public String deletePost(@RequestParam("post_num") int post_num, 
											@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		service.deletePost(post_num);
		
		log.info("삭제 완료! " + post_num);
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:/post/list";
	}
	
	
	
	
	
	
	
	
		
}
