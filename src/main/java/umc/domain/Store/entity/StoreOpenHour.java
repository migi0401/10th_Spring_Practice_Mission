package umc.domain.Store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.global.common.BaseEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreOpenHour extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "OpenDay", nullable = false)
    private DayOfWeek openDay;

    @Column(name = "open_hour", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_hour")
    private LocalTime closeTime;

    @Column(name = "day_off", nullable = false)
    private Boolean dayOff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
}
