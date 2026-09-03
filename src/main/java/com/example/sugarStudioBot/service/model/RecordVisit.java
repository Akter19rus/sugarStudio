package com.example.sugarStudioBot.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "record_visit")
public class RecordVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_and_time")
    private LocalDateTime visitDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recordVisit", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<FixedPrice> fixedPrices = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_chat_id", referencedColumnName = "chat_id")
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecordVisit)) return false;
        return id != null && id.equals(((RecordVisit) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
