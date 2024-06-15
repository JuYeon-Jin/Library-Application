package com.group.libraryapp.project.service.user;

import com.group.libraryapp.project.domain.user.User;
import com.group.libraryapp.project.domain.user.UserRepository;
import com.group.libraryapp.project.dto.book.BookDTO;
import com.group.libraryapp.project.dto.user.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 회원 가입
    public void joinProcess(UserDTO dto) {

        boolean isUser = userRepository.existsByUsername(dto.getUsername());
        if (isUser) {
            return;
        }

        userRepository.save(new User(dto.getUsername(), bCryptPasswordEncoder.encode(dto.getPassword())));
    }

    // 로그인
    public String loginProcess(UserDTO dto) {
        User userData = userRepository.findByUsername(dto.getUsername());
        return userData.getRole();
    }

    // 권한 체크
    public String checkRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        return auth.getAuthority();
        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
            .findFirst()
            .map(GrantedAuthority::getAuthority)
            .orElse(null);
        * */
    }

    // 전체 유저 목록 확인
    /*
    @Transactional(readOnly = true)
    public List<User> userList() {
        return  userRepository.findByDeletedFalse().stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
    }
*/
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
