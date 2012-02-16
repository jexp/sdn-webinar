package org.springdata.neo4j.todos;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.join;

/**
 * @author mh
 * @since 16.02.12
 */
public class Shell {
    private final TodoRepository todos;
    private final TagRepository tags;

    public Shell(TodoRepository todos, TagRepository tags) {
        this.todos = todos;
        this.tags = tags;
    }

    public static void main(String[] args) {
        final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        final TodoRepository todos = ctx.getBean("todoRepository", TodoRepository.class);
        final TagRepository tags = ctx.getBean("tagRepository", TagRepository.class);
        final Shell shell = new Shell(todos, tags);
        shell.execute(args);
    }

    private void execute(String[] args) {
        if (args.length==0) System.err.println("Usage Shell ls|mk|rm [text|id]");
        Command command = Command.valueOf(args[0].toLowerCase());
        command.run(todos,tags, lastArguments(args));
    }

    private String lastArguments(String[] args) {
        return join(asList(args).subList(1, args.length), " ");
    }

    enum Command {
        ls {
            public void run(TodoRepository repository, TagRepository tags, String value) {
                for (Todo todo : repository.findAll()) {
                    System.out.printf("%d. %s %s since %s%n",todo.getId(),todo.getText(),todo.getTags(),todo.getDate());
                }
            }
        }, mk {
            public void run(TodoRepository repository, TagRepository tags, String text) {
                repository.save(new Todo(text,tags.obtainTags(text)));
                ls.run(repository, tags, null);
            }
        }, rm {
            public void run(TodoRepository repository, TagRepository tags, String id) {
                repository.delete(Long.valueOf(id));
                ls.run(repository, tags, null);
            }
        };

        public abstract void run(TodoRepository repository, TagRepository tags, String value);
    }
}
