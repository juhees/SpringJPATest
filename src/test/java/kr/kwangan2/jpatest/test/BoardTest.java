package kr.kwangan2.jpatest.test;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.core.BooleanBuilder;

import kr.kwangan2.jpatest.entity.Board;
import kr.kwangan2.jpatest.entity.QBoard;
import kr.kwangan2.jpatest.repository.BoardRepository;
import kr.kwangan2.jpatest.repository.DynamicBoardRepository;
import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest
@Log4j2
public class BoardTest {

   @Autowired
  private BoardRepository boardRepo;

   @Autowired
   private DynamicBoardRepository dynamicboardRepo;
   
   // @Test
   public void testInsertBoard() {
      Board board = new Board("제목", "작성자", "내용", new Date(), 0L);
      boardRepo.save(board);
   }

   // @Test
   public void testGetBoard() {
      Board board = boardRepo.findById(1L).get();
      log.info(board);
   }

   // @Test
   public void testUpdateBoard() {
      Board board = boardRepo.findById(1L).get();
      board.setTitle("수정");
      log.info("수정전 : " + board);
      boardRepo.save(board);
      log.info("수정후 : " + board);
   }

   // @Test
   public void testDeleteBoard() {
      boardRepo.deleteById(1L);
   }

   // @Test
   public void testFindByTitle() {
      for (int i = 1; i < 201; i++) {
         Board board = new Board("제목" + i, "작성자" + i, "내용" + i, new Date(), 0L);
         boardRepo.save(board);
      }

      List<Board> boardList = boardRepo.findByTitle("테스트 제목: 13");
      System.out.println("검색 결과");
      for (Board board : boardList) {
         System.out.println("---->" + board.toString());
      }

   }

   // @Test
   public void testbylike() {
      List<Board> boardlist = boardRepo.findByContentContaining("1");
      System.out.println("검색결과");
      for (Board board : boardlist) {
         System.out.println("---->" + board.toString());
      }
   }

   //@Test
   public void testQueryAnnotation() {
      List<Board> boardlist = boardRepo.queryAnnotationTest1("테스트 제목: 10");
      for (Board board : boardlist) {
         System.out.println("=[===========?> " + board);

      }
   }

   @Test
   public void testQueryAnnotationTest3() {
      List<Object[]> boardList = boardRepo.queryAnnotationTest3("테스트 제목: 10");
      for (Object[] objArray : boardList) {
         log.info(objArray[0] + ":" + objArray[1] + ":" + objArray[2] + ":" + objArray[3]);
      }
   }

   //@Test
   public void testQueryAnnotationTest4() {
      List<Object[]> boardList 
      = boardRepo.queryAnnotationTest3("테스트 제목: 10");
      for (Object[] objArray : boardList) {
         log.info(objArray[0] + ":" + objArray[1] + ":" + objArray[2] + ":" + objArray[3]);
      }
   }
   
   //@Test
   public void testQueryAnnotationTest5() {
	   Pageable paging = PageRequest.of(0, 3, Sort.Direction.DESC, "seq");
	   
	   List<Board> boardList 
	      = boardRepo.queryAnnotationTest5(paging);
	      for (Board board : boardList) {
	         log.info(board);
	      }
   }
   
   
   @Test
   public void testDynamicQuery() {
	   
	   String searchCondition = "TITLE";
	   String searchKeyword = "테스트 제목: 10";
	   
	   BooleanBuilder builder = new BooleanBuilder();
	   QBoard qboard = QBoard.board;
	   
	   if(searchCondition.equals("TITLE")) {
		   builder.and(qboard.title.like("%"+searchKeyword+"%"));
	   }else if(searchCondition.equals("CONTENT")) {
		   builder.and(qboard.content.like("%"+searchKeyword+"%"));
	   }
	   
	   Pageable paging = PageRequest.of(0, 5);
	   
	   Page<Board> boardList = dynamicboardRepo.findAll(builder,paging);
	   
	   log.info("검색 결과");
	   
	   for(Board board : boardList) {
		   log.info("------>"+board);
	   }
   }
   
   
}