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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;

@Data
@Lazy
@Entity
@NoArgsConstructor
@Table(name = "note")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Note extends AbstractEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "notebook_id", nullable = false)
    private Notebook notebook;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "note_tag",
        joinColumns = { @JoinColumn(name = "note_id", nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "tag_id", nullable = false) }
    )
    private Set<Tag> tags;

}
