package com.fjs.api2dextra.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Abstração dos membros da escola
 */
@MappedSuperclass
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Member implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @NotNull(message = "Character name field is required")
    @NotBlank(message = "Character name field is required")
    @NotEmpty(message = "Character name field is required")
    private String name;

    @NotNull(message = "Character role field is required")
    @NotBlank(message = "Character role field is required")
    @NotEmpty(message = "Character role field is required")
    private String role;

    @Column(name="created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

}