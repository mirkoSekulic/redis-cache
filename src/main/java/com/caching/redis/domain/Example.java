package com.caching.redis.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * An Example domain model.
 */
@Entity
@Table(name = "example")
@Data
public class Example implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    private Long id;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @Size(max = 50)
    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "created_date")
    private Instant createdDate;
}
