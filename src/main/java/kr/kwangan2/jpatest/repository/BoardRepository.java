package kr.kwangan2.jpatest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import kr.kwangan2.jpatest.entity.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {
	
	List<Board> findByTitle(String searchKeyword); //게시 글 제목으로 목록을 조회하는 findByTitle 메소드 추가
	
	//like 연산자 사용하기
	List<Board> findByContentContaining(String searchKeyword);//게시글내용(content)에 특정 단어가 포함된 글 목록을 조회하기 위해서
	
	//여러 조건 사용하기
	List<Board> findByTitleContainingOrContentContaining(String title, String content); //글제목 혹은 내용에 특정단어가 포함된 글 목록 조회
	
	//데이터 정렬하기
	List<Board> findByTitleContainingOrderBySeqDesc(String searchKeyword);//게시글 제목에 특정 단어가 포함된 글 목록을 내림차순으로 조회
	
	//페이징 처리
	//List<Board> findByTitleContaining(String searchKeyword, Pageable paging); //한 화면에 다섯개의 데이터를 보여주기로 하고 첫 페이지에 해당하는 1번부터 다섯개의 데이터만 조회
	
	//page<T>타입 사용하기
	Page<Board> findByTitleContaining(String searchKeyword, Pageable paging);
	
	//위치 기반 파라미터 사용하기
	//@Query("SELECT b FROM Board b WHERE b.title like %?1% ORDER BY b.seq DESC")
	//List<Board> queryAnnotationTest1(String searchKeyword);
	
	//이름 기반 파라미터 사용하기
	@Query("SELECT b FROM Board b " + "WHERE b.title like %:searchKeyword% " + "ORDER BY b.seq DESC")
	List<Board> queryAnnotationTest1(@Param("searchKeyword") String searchKeyword);
	
	//특정 변수만 조회하기
	@Query("SELECT b.seq, b.title, b.writer, b.createDate " 
	+ "FROM Board b " 
	+ "WHERE b.title like %?1% " 
	+ "ORDER BY b.seq DESC")
	List<Object[]> queryAnnotationTest3(@Param("searchKeyword") String searchKeyword);
	
	//네이티브 쿼리 사용하기
	@Query(value="select seq, title, writer, createdate " 
			+ "from Board WHERE title like '%'||?1||'%' " 
			+ "ORDER BY seq DESC", nativeQuery=true)
			List<Object[]> queryAnnotationTest4(String searchKeyword);
	
	//페이징 및 정렬 처리하기
	@Query("select b from Board b order by b.seq desc")
	List<Board> queryAnnotationTest5(Pageable paging);
	
	
	
}
