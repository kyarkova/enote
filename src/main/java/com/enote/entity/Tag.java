package com.enote.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tag")
@Accessors(chain = true)
@EqualsAndHashCode(of = {"name"}, callSuper = false)
public class Tag extends AbstractEntity {

    @NotEmpty
    @Column(
        name = "name",
        nullable = false,
        unique = true
    )
    private String name;

    @ManyToMany(
        mappedBy = "tags",
        fetch = FetchType.EAGER
    )
    private Set<Note> notes = new HashSet<>();

}
