package com.example.pmdr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Builder
@Table(name = "records")
@Getter
@Setter
public class Records {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String task;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    @Tolerate //Necessary since `@Builder` creates a constructor with all arguments, but not the no-arg constructor required by JPA (somehow this interferes with the automatic generation of the no-arg constructor by Lombok?)
    public Records() {
    }

    @Override //We're doing this so that we can compare the data instead of the memory location when testing.
public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Records)) return false;//This will return false even if o is null.
    Records records = (Records) o;
    return task.equals(records.task) &&
           startTime.isEqual(records.startTime) &&
           endTime.isEqual(records.endTime);
    }

    @Override //We need to do this since we overrode the equals method. Otherwise, we might get unexpected behavior.
    public int hashCode() {
        return Objects.hash(task, startTime, endTime);
    }
}
//Note that we should probably consider Users