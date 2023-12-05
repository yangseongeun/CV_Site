package inhatc.spring.CV_Site.repository;

import inhatc.spring.CV_Site.dto.CvSearchDto;
import inhatc.spring.CV_Site.entity.CV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CvRepositoryCustom {
    Page<CV> getCvPage(CvSearchDto cvSearchDto, Pageable pageable);
}
