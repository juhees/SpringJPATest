package kr.kwangan2.jpatest.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import kr.kwangan2.jpatest.entity.Board;

public interface DynamicBoardRepository extends CrudRepository<Board,Long>,
QuerydslPredicateExecutor<Board>{
	

}
