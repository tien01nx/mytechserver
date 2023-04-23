package com.example.mytech.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "image")
public class Image {

    @GenericGenerator(name = "random_id", strategy = "com.example.mytech.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "link", unique = true)
    private String link;

    @Column(name = "size")
    private long size;

    @Column(name = "uploaded_at")
    private Timestamp uploadedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}
