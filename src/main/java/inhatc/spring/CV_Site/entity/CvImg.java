package inhatc.spring.CV_Site.entity;

import inhatc.spring.CV_Site.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 인자가 있는 생성자
@Builder //인자를 가진 생성자
public class CvImg extends BaseEntity {
    @Id //기본키 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_img_id")
    private Long id; //상품 이미지 ID

    private String imgName; //상품 이미지 파일 이름
    private String oriImgName; //이미지 원본 파일 이름
    private String imgUrl; //이미지 경로


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    private CV cv; //이력서

    /**
     * 이미지 수정
     * @param imgName
     * @param oriImgName
     * @param imgUrl
     */
    public void updateItemImg(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
