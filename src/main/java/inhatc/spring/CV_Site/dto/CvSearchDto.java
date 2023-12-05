package inhatc.spring.CV_Site.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 인자가 있는 생성자
@Builder //인자를 가진 생성자
public class CvSearchDto {

    private String searchDateType;
    private String searchBy;
    private String searchQuery = "";
}
