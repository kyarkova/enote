package com.enote.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.HashSet;
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
            joinColumns = {@JoinColumn(name = "note_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false)}
    )
    private Set<Tag> tags = new HashSet<>();

    public boolean addTag(Tag tag) {
//        tag.setNote(this);
        return tags.add(tag);
    }
}
