package inhatc.spring.CV_Site.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 인자가 있는 생성자
@Builder //인자를 가진 생성자
public class MemberFormDto {
    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name; //Member의 이름

    @NotEmpty(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email; //Member의 이메일

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    @Length(min = 4, max = 16, message = "비밀번호는 4자 이상 16자 이하로 입력해주세요.")
    private String password; //Member의 비밀번호

    @NotNull(message = "생년월일은 필수 항목입니다.")
    private LocalDate birthdate; //Member의 생년월일

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    private String nickname; //Member의 닉네임
}
