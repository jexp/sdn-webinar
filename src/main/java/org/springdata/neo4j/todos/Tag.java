package org.springdata.neo4j.todos;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Collection;

@NodeEntity
public class Tag {
    @Indexed
    String name;

    @RelatedTo(type="TAGGED")
    Collection<Todo> todos;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Collection<Todo> getTodos() {
        return todos;
    }

    @Override
    public String toString() {
        return name;
    }
}
