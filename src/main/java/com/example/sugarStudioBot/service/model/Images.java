package com.example.sugarStudioBot.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;


@EqualsAndHashCode(exclude = "id", callSuper = false)
@Data
@Entity
@Table(name = "images")
public class Images {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "media_type")
    private String mediaType;

    @Lob
    @JdbcTypeCode(Types.LONGVARBINARY)
    private byte[] data;

    @Column(name = "file_id")
    private String fileId;
}
