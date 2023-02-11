package com.gmail.klewzow.dao;

import com.gmail.klewzow.entity.MessageRowData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRowDataDAO extends JpaRepository<MessageRowData, Long> {
}
