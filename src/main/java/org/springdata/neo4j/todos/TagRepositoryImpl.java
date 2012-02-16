package org.springdata.neo4j.todos;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagRepositoryImpl implements TagGenerator {
    @Autowired TagRepository tags;

    @Override
    public Set<Tag> obtainTags(String text) {
        final Pattern p = Pattern.compile("#(\\w{3,})");
        final Matcher matcher = p.matcher(text);
        Set<Tag> result=new HashSet<Tag>();
        while (matcher.find()) {
            result.add(obtainTag(matcher.group(1)));
        }
        return result;
    }

    @Override
    public Tag obtainTag(String name) {
        Tag tag = tags.findByName(name);
        if (tag!=null) return tag;
        return tags.save(new Tag(name));
    }
}
