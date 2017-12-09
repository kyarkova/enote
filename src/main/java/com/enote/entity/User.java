package com.enote.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
@Accessors(chain = true)
@EqualsAndHashCode(of = {"login", "password"}, callSuper = false)
public class User extends AbstractEntity {

    @NotEmpty
    @Column(
        nullable = false,
        unique = true
    )
    private String login;

    @NotEmpty
    @Column(
        name = "password",
        nullable = false
    )
    private String password;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private Set<Notebook> notebooks = new HashSet<>();

}
