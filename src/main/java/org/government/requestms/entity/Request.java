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
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "email")
    private String email;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @OneToMany(mappedBy = "request", cascade = CascadeType.REMOVE)
    private List<Comment> comment;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<Like> like;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

}
