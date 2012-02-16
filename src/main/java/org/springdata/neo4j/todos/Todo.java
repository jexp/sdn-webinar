package org.springdata.neo4j.todos;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

@NodeEntity
public class Todo {

	@GraphId
    private Long id;

    private String text;

    private Date date;

    @RelatedTo(type="TAGGED", direction = Direction.INCOMING)
    Set<Tag> tags;

    public Todo() { }

    public Todo(String text, Set<Tag> tags) {
        this.text = text;
        if (tags!=null) this.tags = tags;
        this.date = new Date();
    }

    public String getText() {
        return text;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return text;
    }

    public Long getId() {
        return id;
    }
}
