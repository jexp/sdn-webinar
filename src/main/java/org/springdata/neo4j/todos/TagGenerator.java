package org.springdata.neo4j.todos;

import java.util.Set;

/**
 * @author mh
 * @since 16.02.12
 */
public interface TagGenerator {
    Set<Tag> obtainTags(String text);

    Tag obtainTag(String name);
}
