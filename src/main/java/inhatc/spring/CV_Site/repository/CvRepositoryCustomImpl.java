package inhatc.spring.CV_Site.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inhatc.spring.CV_Site.dto.CvSearchDto;
import inhatc.spring.CV_Site.entity.CV;
import inhatc.spring.CV_Site.entity.QCV;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class CvRepositoryCustomImpl implements CvRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public CvRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        }
        return QCV.cV.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("cvName", searchBy)) {
            return QCV.cV.cvName.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createBy", searchBy)) {
            return QCV.cV.createBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<CV> getCvPage(CvSearchDto cvSearchDto, Pageable pageable) {
        QueryResults<CV> results = queryFactory
                .selectFrom(QCV.cV)
                .where(regDtsAfter(cvSearchDto.getSearchDateType()), searchByLike(cvSearchDto.getSearchBy(), cvSearchDto.getSearchQuery()))
                .orderBy(QCV.cV.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CV> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
