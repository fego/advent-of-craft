package blog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Article {
    private final String name;
    private final String content;
    private final List<Comment> comments;

    public Article(String name, String content, List<Comment> comments) {
        this.name = name;
        this.content = content;
        this.comments = comments;
    }

    public Article(String name, String content) {
        this.name = name;
        this.content = content;
        this.comments = new ArrayList<>();
    }

    private Article addComment(
            String text,
            String author,
            LocalDate creationDate) throws CommentAlreadyExistException {
        var comment = new Comment(text, author, creationDate);

        if (comments.contains(comment)) {
            throw new CommentAlreadyExistException();
        }
        var newComments = new ArrayList<>(comments);
        newComments.add(comment);
        return new Article(this.name, this.content, Collections.unmodifiableList(newComments));
    }

    public Article addComment(String text, String author) throws CommentAlreadyExistException {
        return addComment(text, author, LocalDate.now());
    }

    public List<Comment> getComments() {
        return comments;
    }
}

