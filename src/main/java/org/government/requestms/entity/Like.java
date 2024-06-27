package org.government.requestms.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(
    name = "like", schema = "request_ms"
)
@Getter
@Setter
public class Like {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer likeId;
    private String email;
    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime lastModified;
    @ManyToOne
    private Request request;


}
