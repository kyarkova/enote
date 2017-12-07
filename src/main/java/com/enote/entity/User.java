package com.enote.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Lazy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@Lazy
@Entity
@NoArgsConstructor
@Table(name = "user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity {

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY,
        cascade = CascadeType.REMOVE,
        orphanRemoval = true
    )
    private Set<Notebook> notebooks;

}
