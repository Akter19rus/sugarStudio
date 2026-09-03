package com.example.sugarStudioBot.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "fixed_price")
public class FixedPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "fixed_price")
    private BigDecimal priceAtMoment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_visit")
    @ToString.Exclude
    private RecordVisit recordVisit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FixedPrice)) return false;
        return id != null && id.equals(((FixedPrice) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
