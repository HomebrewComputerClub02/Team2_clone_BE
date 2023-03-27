package com.homebrewtify.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayRecord {
    @Id
    @Column(name = "play_record_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private LocalDateTime playDate;

    @Enumerated(EnumType.STRING)
    private PlayType type;

    private String dataId;

    @Builder(builderMethodName = "create")
    public PlayRecord(User user, LocalDateTime playDate, PlayType type, String dataId) {
        this.user = user;
        this.playDate = playDate;
        this.type = type;
        this.dataId = dataId;
    }

    @Override
    public String toString() {
        return "PlayRecord{" +
                "id='" + id + '\'' +
                ", playDate=" + playDate +
                ", type=" + type +
                ", dataId='" + dataId + '\'' +
                '}';
    }
}
