package inhatc.spring.CV_Site.dto;

import inhatc.spring.CV_Site.constant.MillitaryService;
import inhatc.spring.CV_Site.entity.CV;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 인자가 있는 생성자
@Builder //인자를 가진 생성자
public class CvFormDto {
    private Long id; //이력서 아이디

    @NotBlank(message = "이력서명은 반드시 입력해야 합니다.")
    private String cvName; //이력서명

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotNull(message = "생년월일은 필수!")
    private LocalDate birth_cv;

    @NotBlank(message = "전화번호는 필수!")
    private String phone;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String addr;

    private MillitaryService millitaryService; //병역사항

    @NotBlank(message = "학력은 필수 입력 값입니다.")
    private String education; //학력

    @NotBlank(message = "자격증은 필수 입력 값입니다.")
    private String certificate; //자격증

    private CvImgDto cvImgDto; //이력서 이미지

    private Long cvImgId; //

    private static ModelMapper modelMapper = new ModelMapper();

    /**
     * CvFormDto 엔티티를 CV로 변환
     * @return
     */
    public CV dtoToEntity() {
        return modelMapper.map(this, CV.class);
    }

    /**
     * CV 엔티티를 CvFormDto로 변환
     * @param cv
     * @return
     */
    public static CvFormDto entityToDto(CV cv) {
        return modelMapper.map(cv, CvFormDto.class);
    }
}
