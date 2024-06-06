package com.group.libraryapp.project.service.user;

import com.group.libraryapp.project.domain.user.User;
import com.group.libraryapp.project.domain.user.UserRepository;
import com.group.libraryapp.project.dto.user.request.UserRequest;
import com.group.libraryapp.project.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ADMIN 권한 체크
    @Transactional
    public Boolean isAdmin(String private_id) {
        Optional<User> user = userRepository.findById(private_id);
        if (user.isPresent()) {
            String role = String.valueOf(user.get().getRole());
            return "ADMIN".equals(role);
        }

        return false;
    }

    // 로그인 → pk 반환
    @Transactional
    public String authenticate(String id, String pw) {
        User user = userRepository.findByIdAndPw(id, pw)
                    .orElseThrow(NullPointerException::new);

        return user.getPrivateId();
    }

    // 유저 정보 저장
    @Transactional
    public void saveUser(UserRequest request) {
        userRepository.save(new User(request.getId(), request.getPw()));
    }

    // 전체 유저 목록 확인
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    // 검색 유저 목록 확인

    /*
    // CREATE   * 회원 가입
    @Transactional
    public void saveUser(UserCreateRequest request) {
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    // READ   * 회원 전체 목록 조회
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    // UPDATE   * 비밀번호 수정
    @Transactional
    public void updateUser(UserUpdateRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
        userRepository.save(user);
    }

    // DELETE   * 회원 탈퇴
    @Transactional
    public void deleteUser(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);
        if (user == null) {
            throw new IllegalArgumentException();
        }

        userRepository.delete(user);
    }

    */


}
