package service;

import java.util.List;

import domain.BoardVO;

public interface BoardService {

	int register(BoardVO bvo);

	List<BoardVO> getList();

	BoardVO getDetail(int bno);

	int update(BoardVO bvo);

	int delete(int bno);

}
