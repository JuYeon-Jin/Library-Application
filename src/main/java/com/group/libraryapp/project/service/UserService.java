package com.group.libraryapp.project.service;

import com.group.libraryapp.project.domain.user.User;
import com.group.libraryapp.project.domain.user.UserRepository;
import com.group.libraryapp.project.dto.user.SignUpDTO;
import com.group.libraryapp.project.exception.custom.DuplicateUsernameException;
import com.group.libraryapp.project.exception.custom.InvalidFormatException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 사용자 관련 비즈니스 로직을 담당하는 서비스 클래스입니다.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    /**
     * 회원 가입을 처리하는 메서드입니다.
     * validateSignupData() 를 통해 유효성을 검사 한 후 사용자 정보를 등록하며,
     * 이 때, 비밀번호는 bCrypt 로 암호화되어 저장됩니다.
     *
     * @param dto 회원 가입 정보를 포함하는 UserDTO 객체
     */
    public void signUp(SignUpDTO dto) {
        validateSignupData(dto.getUsername(), dto.getPassword());
        userRepository.save(new User(dto.getUsername(), bCryptPasswordEncoder.encode(dto.getPassword())));
    }


    /**
     * 회원 가입 시 입력된 ID와 비밀번호의 유효성을 검증하는 메서드입니다.
     * ID는 영문자, 숫자, 한글로만 구성되어야 하며, 길이는 5자 이상 10자 이하이어야 합니다.
     * 비밀번호는 소문자와 숫자로만 구성되어야 하며, 길이는 5자 이상 10자 이하이어야 합니다.
     * 유효하지 않은 경우 예외를 발생시킵니다.
     *
     * @param id 사용자 ID
     * @param password 사용자 비밀번호
     *
     * @throws DuplicateUsernameException ID가 이미 존재하는 경우 발생합니다.
     * @throws InvalidFormatException    ID 또는 비밀번호가 규격에 맞지 않는 경우 발생합니다.
     */
    private void validateSignupData(String id, String password) {
        if (userRepository.existsByUsername(id)) {
            throw new DuplicateUsernameException("이미 존재하는 ID(" + id + ") 입니다.");
        }

        String regexId = "^[A-Za-z0-9가-힣]+$";
        if (!id.matches(regexId)) throw new InvalidFormatException("ID는 영문자, 숫자, 한글로만 구성되어야 합니다.");
        if (id.toCharArray().length < 3 || id.toCharArray().length > 6) throw new InvalidFormatException("ID는 3자 이상 6자 이하로 입력해주세요.");

        String regexPw = "^[a-z0-9]+$";
        if (!password.matches(regexPw)) throw new InvalidFormatException("비밀번호는 영문 소문자와 숫자로만 구성되어야 합니다.");
        if (password.toCharArray().length < 4 || password.toCharArray().length > 10) throw new InvalidFormatException("비밀번호는 4자 이상 10자 이하로 입력해주세요.");
    }


    // TODO 클라이언트에서 자바스크립트로 입력 검증 필요
    // TODO 트랜잭션 적용할지에 대한 고민 (필요성, 효율성 등)

    /* *
    // TODO 관리자 - 전체 유저 목록 확인 (정렬 추가)
    public List<UserDTO> userList() {
        return  userRepository.findByDeletedFalseAndRole("ROLE_USER").stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }
    */
}
