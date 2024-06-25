package org.government.requestms.entity;

import jakarta.persistence.*;
import lombok.*;
import org.government.requestms.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request", schema = "request_ms")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    private String email;
    private String organizationName;
    private String address;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createDate;
    private LocalDateTime lastModified;
    @OneToMany(mappedBy = "request", cascade = CascadeType.REMOVE)
    private List<Comment> comment;
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<Like> like;
    @OneToOne()
    @JoinColumn(name = "category_id")
    private Category category;


}
