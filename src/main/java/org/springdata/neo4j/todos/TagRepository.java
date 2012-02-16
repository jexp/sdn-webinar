package org.springdata.neo4j.todos;

import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.Set;

public interface TagRepository extends GraphRepository<Tag>, TagGenerator {
    Tag findByName(String name);
}
