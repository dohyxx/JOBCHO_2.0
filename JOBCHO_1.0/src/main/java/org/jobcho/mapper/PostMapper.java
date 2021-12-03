package org.jobcho.mapper;

import java.util.HashMap;
import java.util.List;

import org.jobcho.domain.Criteria;
import org.jobcho.domain.PostVO;

public interface PostMapper {

	int insertPost(PostVO post); //게시글 생성
	List<PostVO> getListPost(HashMap<String, Object> map); //게시글 리스트
	PostVO getPost(int post_num);//게시글 상세조회
	int updatePost(PostVO post); //게시글 수정
	void deletePost(int post_num); //게시글 삭제
	
	int getTotalCount(int board_num); //전체 글 수
	
}
