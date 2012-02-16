package org.springdata.neo4j.todos;

import org.springframework.data.neo4j.repository.GraphRepository;

public interface TagRepository extends GraphRepository<Tag> {

    Tag findByName(String name);
}
