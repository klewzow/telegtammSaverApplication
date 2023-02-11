package com.gmail.klewzow.dao;

import com.gmail.klewzow.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {
    ApplicationUser findApplicationUserByTelegramUserId(Long chatId);
}
