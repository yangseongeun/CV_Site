package inhatc.spring.CV_Site.repository;

import inhatc.spring.CV_Site.entity.CvImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvImgRepository extends JpaRepository<CvImg, Long> {

    CvImg findByCvIdOrderByIdAsc(Long cvId);
}
