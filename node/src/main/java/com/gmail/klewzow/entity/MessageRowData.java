package com.gmail.klewzow.entity;



import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.*;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
@Log4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MessageRowData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chat_id")
    private long chatID;



    @Column(name = "message_data_json",
            columnDefinition = "json")
    @Type(type = "json")
    private Update update;
}
