package blog;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleTests {

    private LocalDate now = LocalDate.now();
    @Test
    void it_should_add_valid_comment() throws CommentAlreadyExistException {
        // given
        var article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        String commentContent = "Amazing article !!!";
        String commentAuthor = "Pablo Escobar";

        // when
        article.addComment(commentContent, commentAuthor, now);

        // then
        assertThat(article.getComments())
                .singleElement()
                .isEqualTo(new Comment(commentContent, commentAuthor, now));
    }


    @Test
    void it_should_throw_an_exception_when_adding_existing_comment() throws CommentAlreadyExistException {
        var article = new Article(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );
        article.addComment("Amazing article !!!", "Pablo Escobar", now);

        assertThatThrownBy(() -> {
            article.addComment("Amazing article !!!", "Pablo Escobar", now);
        }).isInstanceOf(CommentAlreadyExistException.class);
    }
}
