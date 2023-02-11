package com.gmail.klewzow.entity;


import com.gmail.klewzow.enums.UserStatus;
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
@Builder
@ToString
@Log4j
@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long telegramUserId;

    @Column(name = "first_data")
    @CreationTimestamp
    private LocalDateTime firstLoginDate;

    @Column(name = "user_name")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "e_mail")
    private String eMail;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

    @Column(name = "active")
    private boolean isActive;


    public int showStatus(){
        return this.status.ordinal();
    }

}
