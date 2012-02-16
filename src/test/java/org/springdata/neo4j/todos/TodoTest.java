package org.springdata.neo4j.todos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singleton;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TodoTest {
    @Autowired
    Neo4jTemplate template;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    TagRepository tagRepository;

    @Test
    public void createAndReadTodo() {
        final Todo todo = new Todo("test", null);
        final Todo saved = template.save(todo);
        assertEquals("same text after save", todo.getText(), saved.getText());
        final Todo loaded = template.findOne(saved.getId(), Todo.class);
        assertEquals("same id after load", saved.getId(), loaded.getId());
        assertEquals("same text after load", todo.getText(), saved.getText());
    }

    @Test
    public void createAndReadTodoWithRepository() {
        final Todo todo = new Todo("test", null);
        final Todo saved = todoRepository.save(todo);
        assertEquals("same text after save", todo.getText(), saved.getText());
        final Todo loaded = todoRepository.findOne(saved.getId());
        assertEquals("same id after load", saved.getId(), loaded.getId());
        assertEquals("same text after load", todo.getText(), saved.getText());
    }

    @Test
    public void findTodoWithFindAll() {
        final Todo todo = todoRepository.save(new Todo("test", null));
        final Todo todo2 = todoRepository.save(new Todo("test2", null));
        final Iterable<Todo> loaded = todoRepository.findAll();
        assertThat("has found all todos", loaded, hasItems(todo, todo2));
    }

    @Test
    public void deleteTodo() {
        final Todo todo = todoRepository.save(new Todo("test", null));
        final Long id = todo.getId();
        todoRepository.delete(todo);
        assertThat("todo was deleted", todoRepository.exists(id), is(true));
    }

    @Test
    public void findTodoWithDynamicQuery() {
        final Todo todo = todoRepository.save(new Todo("test", null));
        todoRepository.save(new Todo("test2", null));
        final Iterable<Todo> loaded = todoRepository.findByText("test");
        assertThat("has found the todo with text 'test'", loaded, hasItems(todo));
    }

    @Test
    public void todoKeepsGivenTags() {
        final Tag tag = template.save(new Tag("tag"));
        final Todo todo = todoRepository.save(new Todo("test", singleton(tag)));
        final Todo loaded = todoRepository.findOne(todo.getId());
        assertThat("todo has the tag 'tag'", loaded.getTags(), hasItems(tag));
        assertThat("tag has todos 'test'", tag.getTodos(), hasItems(todo));
    }

    @Test
    public void findTodoWithTraversingDynamicQuery() {
        final Tag tag = template.save(new Tag("tag"));
        final Todo todo = todoRepository.save(new Todo("test", singleton(tag)));
        final Collection<Todo> loaded = todoRepository.findByTagsName("tag", null);
        assertThat("has found the todo with the tag named 'tag'", loaded, hasItems(todo));
    }

    @Test
    public void findTodoWithAnnotatedQuery() {
        final Tag tag = template.save(new Tag("tag"));
        final Todo todo = todoRepository.save(new Todo("test", singleton(tag)));
        final Todo todo2 = todoRepository.save(new Todo("test2", singleton(tag)));
        final List<Todo> loaded = todoRepository.findCoTagged(todo);
        assertThat("has found the other todos also tagged with the tag of 'tag'",
                loaded, hasItem(todo2));
    }
    @Test
    public void testGenerateTags() {
        final String text = "test #tag";
        final Set<Tag> tags = tagRepository.obtainTags(text);
        assertThat("one tag generated",tags.size(),is(1));
        final Tag tag = tags.iterator().next();
        assertThat("tags has name 'tag'", tag.getName(), is("tag"));
    }
}
