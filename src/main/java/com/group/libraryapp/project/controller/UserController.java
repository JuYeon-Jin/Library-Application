package com.group.libraryapp.project.controller;

import com.group.libraryapp.project.dto.book.BookDTO;
import com.group.libraryapp.project.dto.book.BookLoanDTO;
import com.group.libraryapp.project.service.book.BookService;
import com.group.libraryapp.project.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final BookService bookService;
    private final UserService userService;

    public UserController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    // 대여 도서 목록
    @GetMapping("/myBook")
    public String myBookList(Model model) {
        model.addAttribute("books", bookService.loanBookList());
        return "view/user/loanHistory";
    }

    // 도서 전체 목록
    @GetMapping("/bookList")
    public String bookList(Model model) {
        model.addAttribute("books", bookService.bookList());
        return "view/user/bookList";
    }

    // 도서 대여 (CREATE)
    @PostMapping("/loanBook")
    public String loanBook(BookDTO dto) {
        bookService.loanBook(dto.getBookId());
        return "redirect:/user/bookList";
    }

    // 도서 반납 (PUT)
    @PostMapping("/returnBook")
    public String returnBook(BookLoanDTO dto) {
        bookService.returnBook(dto.getId(), dto.getBookId());
        return "redirect:/user/myBook";
    }

    // 도서 예약
    @PostMapping("/reservation")
    public String reservation() {
        // 서비스 미구현
        return "redirect:/user/bookList";
    }


/*

    // READ   * 회원 전체 목록 조회
    @GetMapping("/admin/userlist")
    public List<UserResponse> getUsers(ModelAndView mav) {
        return userService.getUsers();
        //        mav.setViewName("/post/my-posts");
        //        mav.addObject("posts",postService.allPosts());
    }
    // UPDATE   * 비밀번호 수정
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
       userService.updateUser(request);
    }
    // DELETE   * 회원 탈퇴
    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }
    */

}
