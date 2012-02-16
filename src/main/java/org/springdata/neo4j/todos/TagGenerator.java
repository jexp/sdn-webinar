package org.springdata.neo4j.todos;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author mh
 * @since 16.02.12
 */
public interface TagGenerator {
    @Transactional
    Set<Tag> obtainTags(String text);

    @Transactional
    Tag obtainTag(String name);
}
