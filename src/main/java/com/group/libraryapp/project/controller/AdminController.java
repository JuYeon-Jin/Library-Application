package com.group.libraryapp.project.controller;

import com.group.libraryapp.project.dto.book.BookDTO;
import com.group.libraryapp.project.service.book.BookService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    // 책 등록 PAGE
    @GetMapping("/book")
    public String book(Model model) {
        return "view/admin/book";
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
        // model.addAttribute("users", userService.userList());
        return "view/admin/userList";
    }

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
