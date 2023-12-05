package inhatc.spring.CV_Site.service;

import inhatc.spring.CV_Site.constant.MillitaryService;
import inhatc.spring.CV_Site.dto.CvFormDto;
import inhatc.spring.CV_Site.entity.CV;
import inhatc.spring.CV_Site.entity.CvImg;
import inhatc.spring.CV_Site.repository.CvImgRepository;
import inhatc.spring.CV_Site.repository.CvRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class CvServiceTest {

    @Autowired
    CvService cvService;

    @Autowired
    CvRepository cvRepository;

    @Autowired
    CvImgRepository cvImgRepository;

    MultipartFile createMultipartFile() throws Exception {

        MultipartFile multipartFile;

        String path = "D:/cv_site/cv";
        String imageName = "image.jpg";
        MockMultipartFile mockMultipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1});

        return mockMultipartFile;
    }

    @Test
    @DisplayName("이력서 등록 테스트")
    @WithMockUser(username = "user", roles = "USER")
    void saveCv() throws Exception {
        String phone = "010-1111-2222";
        String addr = "홍길동의 주소";
        String education = "홍길동의 학력";
        String certificate = "홍길동의 자격증";

        CvFormDto cvFormDto = CvFormDto.builder()
                .cvName("테스트 이력서")
                .name("홍길동")
                .birth_cv(LocalDate.parse("2023-11-01"))
                .phone(phone)
                .addr(addr)
                .millitaryService(MillitaryService.DONE)
                .education(education)
                .certificate(certificate)
                .build();

        MultipartFile multipartFile = createMultipartFile();
        Long cvId = cvService.saveCv(cvFormDto, multipartFile);

        CvImg cvImg = cvImgRepository.findByCvIdOrderByIdAsc(cvId);
        CV cv = cvRepository.findById(cvId).orElseThrow(EntityNotFoundException::new);

        assertEquals(cvFormDto.getCvName(), cv.getCvName());
        assertEquals(cvFormDto.getName(), cv.getName());
        assertEquals(cvFormDto.getBirth_cv(), cv.getBirth_cv());
        assertEquals(cvFormDto.getPhone(), cv.getPhone());
        assertEquals(cvFormDto.getAddr(), cv.getAddr());
        assertEquals(cvFormDto.getMillitaryService(), cv.getMillitaryService());
        assertEquals(cvFormDto.getEducation(), cv.getEducation());
        assertEquals(cvFormDto.getCertificate(), cv.getCertificate());
    }

}