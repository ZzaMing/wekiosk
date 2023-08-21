package com.project.wekiosk.faq.domain;

import com.project.wekiosk.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qno;

    private String qtitle;

    private String qcontent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void changeQtitle(String qtitle) {
        this.qtitle = qtitle;
    }

    public void changeQcontent(String qcontent) {
        this.qcontent = qcontent;
    }

}
