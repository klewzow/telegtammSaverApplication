package com.gmail.klewzow.DAO;

import com.gmail.klewzow.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {
    ApplicationUser findApplicationUserByTelegramChatId(Long chatId);
}
