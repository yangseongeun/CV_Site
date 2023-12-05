package inhatc.spring.CV_Site.dto;

import inhatc.spring.CV_Site.entity.CvImg;
import lombok.*;
import org.modelmapper.ModelMapper;

@Setter
@Getter
@ToString
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 인자가 있는 생성자
@Builder //인자를 가진 생성자
public class CvImgDto {
    private Long id; //이미지 ID

    private String imgName; //이미지 파일 이름
    private String oriImgName; //이미지 원본 파일 이름
    private String imgUrl; //이미지 경로

    public static ModelMapper modelMapper = new ModelMapper();

    public static CvImgDto entityToDto(CvImg cvImg) {
        CvImgDto dto = modelMapper.map(cvImg, CvImgDto.class); //CvImgDto dto = CvImgDto.builder().build(); 를 대체
        return dto;
    }
}
