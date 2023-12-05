package inhatc.spring.CV_Site.service;

import inhatc.spring.CV_Site.entity.CvImg;
import inhatc.spring.CV_Site.repository.CvImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class CvImgService {

    @Value("${cvImgLocation}")
    private String cvImgLocation;

    private final FileService fileService;
    private final CvImgRepository cvImgRepository;

    public void saveCvImg(CvImg cvImg, MultipartFile CvImgFile) throws IOException {
        String originalFileName = CvImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if(!StringUtils.isEmpty(originalFileName)) {
            imgName = fileService.uploadFile(cvImgLocation, originalFileName, CvImgFile.getBytes());
            imgUrl = "/images/cv/" + imgName;
        }

        cvImg.updateItemImg(imgName, originalFileName, imgUrl);
        cvImgRepository.save(cvImg);
    }
}
