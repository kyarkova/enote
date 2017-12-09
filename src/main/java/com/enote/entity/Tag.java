package com.enote.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Lazy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Lazy
@Entity
@NoArgsConstructor
@Table(name = "tag")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Tag extends AbstractEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tag")
//    private Set<Note> notes;

//    public void setNote(Note note) {
//        this.notes.add(note);
//    }
}