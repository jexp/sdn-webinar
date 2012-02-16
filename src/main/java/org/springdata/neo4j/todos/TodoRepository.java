package org.springdata.neo4j.todos;

import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.Collection;
import java.util.List;

public interface TodoRepository extends GraphRepository<Todo> {

    Collection<Todo> findByTagsName(String tag, Sort date);
    Collection<Todo> findByText(String text);

    @Query("start todo=node({0}) match todo<-[:TAGGED]-tag-[:TAGGED]->other return other")
    List<Todo> findCoTagged(Todo tag);
}
