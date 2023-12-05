package inhatc.spring.CV_Site.service;

import inhatc.spring.CV_Site.dto.CvFormDto;
import inhatc.spring.CV_Site.dto.CvImgDto;
import inhatc.spring.CV_Site.dto.CvSearchDto;
import inhatc.spring.CV_Site.entity.CV;
import inhatc.spring.CV_Site.entity.CvImg;
import inhatc.spring.CV_Site.repository.CvImgRepository;
import inhatc.spring.CV_Site.repository.CvRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CvService {
    private final CvRepository cvRepository;
    private final CvImgService cvImgService;
    private final CvImgRepository cvImgRepository;

    public Long saveCv(CvFormDto cvFormDto, MultipartFile cvImgFile) throws IOException {
        // 이력서 등록
        CV cv = cvFormDto.dtoToEntity();
        cvRepository.save(cv);

        // 이력서 이미지 등록
        CvImg cvImg = new CvImg();
        cvImg.setCv(cv);

        cvImgService.saveCvImg(cvImg, cvImgFile);

        return cv.getId();
    }


    public CvFormDto getCvDetail(Long cvId) {
        CvImg cvImg = cvImgRepository.findByCvIdOrderByIdAsc(cvId);

        CvImgDto cvImgDto = CvImgDto.entityToDto(cvImg);

        CV cv = cvRepository.findById(cvId).orElseThrow(() ->
                new EntityNotFoundException("해당 이력서의 id = " + cvId + "이(가) 없습니다."));

        CvFormDto cvFormDto = CvFormDto.entityToDto(cv);
        cvFormDto.setCvImgDto(cvImgDto);

        return cvFormDto;
    }

    @Transactional(readOnly = true)
    public Page<CV> getCvPage(CvSearchDto cvSearchDto, Pageable pageable) {
        return cvRepository.getCvPage(cvSearchDto, pageable);
    }
}
