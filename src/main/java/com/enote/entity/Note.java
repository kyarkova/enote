package com.enote.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "note",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title", "notebook_id"}))
@Accessors(chain = true)
@EqualsAndHashCode(of = {"title", "notebook"}, callSuper = false)
public class Note extends AbstractEntity {

    @NotEmpty
    @Column(
        name = "title",
        nullable = false
    )
    private String title;

    @ManyToOne
    @JoinColumn(
        name = "notebook_id",
        nullable = false
    )
    private Notebook notebook;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "note_tag",
        joinColumns = @JoinColumn(
            name = "note_id",
            referencedColumnName = "id",
            nullable = false
        ),
        inverseJoinColumns = @JoinColumn(
            name = "tag_id",
            referencedColumnName = "id",
            nullable = false
        )
    )
    private Set<Tag> tags = new HashSet<>();

}
