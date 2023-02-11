package com.gmail.klewzow;


import lombok.*;
import lombok.extern.log4j.Log4j;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
@Log4j
@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long telegramChatId;

    @Column(name = "first_data")
    @CreationTimestamp
    private LocalDateTime firstLoginDate;

    @Column(name = "user_name")
    private long username;

    @Column(name = "first_name")
    private long firstName;

    @Column(name = "last_name")
    private long lastName;

    @Column(name = "e_mail")
    private long eMail;

    @Column(name = "status")
//    @Enumerated(EnumType.ORDINAL)
    private int status;

    @Column(name = "active")
    private boolean isActive;

}
