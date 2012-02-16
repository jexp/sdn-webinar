package org.springdata.neo4j.todos;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.neo4j.helpers.collection.IteratorUtil.asCollection;

@RequestMapping("/")
@Controller
public class TodoController {
	
    @Autowired TodoRepository todos;
    @Autowired TagRepository tags;

    @RequestMapping(method = RequestMethod.POST)
    public String create(Model model, String text) {
        Set<Tag> newTags = tags.obtainTags(text);
        final Todo todo = new Todo(text, newTags);
        final Todo newTodo = todos.save(todo);
        model.addAttribute("todo", newTodo);
        return list(model);
    }

    @RequestMapping
    public String list(Model model) {
        model.addAttribute("todos", asCollection(todos.findAll()));
        model.addAttribute("tags", asCollection(tags.findAll()));
        return "list";
    }

    @RequestMapping("/tag/{tag}")
    public String list(Model model, @PathVariable("tag") String tagName) {
        model.addAttribute("todos",todos.findByTagsName(tagName,new Sort(Sort.Direction.DESC, "todo.date")));
        model.addAttribute("tags",asCollection(tags.findAll()));
        return "list";
    }
    @RequestMapping("/tag2/{tag}")
    public String list2(Model model, @PathVariable("tag") String tagName) {
        final Tag tag = tags.findByName(tagName);
        model.addAttribute("todos",tag.getTodos());
        model.addAttribute("tags",asCollection(tags.findAll()));
        return "list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
        Todo todo = todos.findOne(id);
        if (todo!=null) {
            todos.delete(todo);
        }
        return "list";
    }
}
