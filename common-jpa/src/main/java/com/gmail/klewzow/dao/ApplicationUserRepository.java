package com.gmail.klewzow.dao;

import com.gmail.klewzow.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {
    ApplicationUser findApplicationUserByTelegramChatId(Long chatId);
}
