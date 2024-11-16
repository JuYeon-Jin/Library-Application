package com.group.libraryapp.project.domain.loan;

import com.group.libraryapp.project.domain.book.Book;
import com.group.libraryapp.project.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "loan_history")
@Getter
public class LoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private int loanId;

    /*
    TODO Eager(Default) vs. Lazy Fetching 과연 뭐를 사용해야 적절할까 @ManyToOne(fetch = FetchType.LAZY)
    JPA 의 연관 관계에서 데이터를 언제 로드할지 결정하는 방식
    - FetchType.EAGER (즉시 로딩): 엔티티가 로드될 때 연관된 모든 엔티티도 함께 로드됩니다.
    - FetchType.LAZY (지연 로딩): 연관된 엔티티는 실제로 사용될 때까지 로드되지 않고, 필요할 때 데이터를 가져옵니다.

    fetch = FetchType.LAZY 의 사용 예시와 이유
    - @ManyToOne, @OneToOne 관계에서는 FetchType.LAZY 를 사용하여 성능을 최적화할 수 있습니다.
    - 성능 최적화: Lazy 로딩을 사용하면 필요할 때만 데이터를 가져오므로, 불필요한 데이터 로드를 피할 수 있습니다.
    - N+1 문제를 피할 수 있음: 즉시 로딩이 필요하지 않은 경우 Lazy 로딩을 사용하면 더 나은 성능을 유지할 수 있습니다.

    예를 들어, LoanHistory 엔티티에서 User 와 Book 정보를 자주 사용하지 않는다면, Lazy 로딩을 통해 메모리와 조회 성능을 최적화할 수 있습니다.
    * */

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "borrowed_at", nullable = false)
    private LocalDateTime borrowedAt;

    @Column(name = "is_returned", nullable = false)
    private boolean isReturned;



    /**
     * JPA 기본 생성자 (필수)
     */
    protected LoanHistory() { }



    /**
     * 도서 대출용 생성자입니다.
     */
    public LoanHistory(User user, Book book) {
        this.user = user;
        this.book = book;
        this.borrowedAt = LocalDateTime.now();
    }

}
