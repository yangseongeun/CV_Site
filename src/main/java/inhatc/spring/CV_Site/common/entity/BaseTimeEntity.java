package inhatc.spring.CV_Site.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) // JPA에게 해당 Entity는 Auditing 기능을 사용한다는 것을 알려주는 어노테이션
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들도 칼럼으로 인식하도록 함
@Getter // 겟터
@Setter // 셋터
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false) //등록일은 한번만 등록되므로, 수정이 되면 안된다.
    private LocalDateTime regTime; //등록일

    @LastModifiedDate
    private LocalDateTime updateTime; //수정일


}
