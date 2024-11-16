package com.group.libraryapp.project.controller;

import com.group.libraryapp.project.service.BookService;
import com.group.libraryapp.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private final BookService bookService;
    private final UserService userService;

    public AdminController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    // 책 등록 PAGE
    @GetMapping("/library")
    public String book() {
        return "view/admin/book";
    }

/* *
    @GetMapping("/admin/library")
    public String adminBookList(Model model) {
        model.addAttribute("userInfo", getSecurityUsername());
        return "view/admin-book-list";
    }
    @GetMapping("/admin/member")
    public String adminMemberList(Model model) {
        model.addAttribute("userInfo", getSecurityUsername());
        return "view/admin-member-list";
    }

    // 도서 등록 (CREATE)
    @PostMapping("/book")
    public String book(BookDTO dto) {
        bookService.saveBook(dto);
        return "redirect:/admin/book";
    }

    // 도서 제목 수정 (UPDATE)
    @PutMapping("/book")
    public String updateBook() {

        return "redirect:/admin/bookList";
    }

    // 도서 삭제 (DELETE)
    @DeleteMapping("/book")
    public String deleteBook() {

        return "redirect:/admin/bookList";
    }

    // 도서 전체 목록 PAGE
    @GetMapping("/bookList")
    public String bookList(Model model) {
        model.addAttribute("books", bookService.bookList());
        return "view/admin/bookList";
    }

    // 유저 전체 목록 PAGE
    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("users", userService.userList());
        return "view/admin/userList";
    }
*/

    /*

        //-------------------------------------------------------------------------------------
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();


        model.addAttribute("id", id);
        model.addAttribute("role", role);
        //-------------------------------------------------------------------------------------

    * */


}
