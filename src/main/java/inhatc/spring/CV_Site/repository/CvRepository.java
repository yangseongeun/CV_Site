package inhatc.spring.CV_Site.repository;

import inhatc.spring.CV_Site.entity.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CvRepository extends JpaRepository<CV, Long>, QuerydslPredicateExecutor<CV>, CvRepositoryCustom {

}
