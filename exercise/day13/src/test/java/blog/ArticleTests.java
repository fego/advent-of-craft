package blog;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleTests {
    private Article article;

    @Test
    void should_add_comment_in_an_article() throws CommentAlreadyExistException {
        article = ArticleTestBuilder.anArticle()
                .withComment("Amazing article !!!", "Pablo Escobar")
                .build();

        assertThat(article.getComments()).hasSize(1);

        var comment = article.getComments().get(0);
        assertThat(comment.text()).isEqualTo("Amazing article !!!");
        assertThat(comment.author()).isEqualTo("Pablo Escobar");
    }

    @Test
    void should_add_comment_in_an_article_containing_already_a_comment() throws CommentAlreadyExistException {
        article = ArticleTestBuilder.anArticle()
                .withComment("Amazing article !!!", "Pablo Escobar")
                .withComment("Finibus Bonorum et Malorum", "Al Capone")
                .build();

        assertThat(article.getComments()).hasSize(2);

        var lastComment = article.getComments().getLast();
        assertThat(lastComment.text()).isEqualTo("Finibus Bonorum et Malorum");
        assertThat(lastComment.author()).isEqualTo("Al Capone");
    }

    @Nested
    class Fail {
        @Test
        void when_adding_an_existing_comment() throws CommentAlreadyExistException {
            article = ArticleTestBuilder
                    .anArticle()
                    .withComment("Amazing article !!!", "Pablo Escobar")
                    .build();

            assertThatThrownBy(() -> {
                article.addComment("Amazing article !!!", "Pablo Escobar");
            }).isInstanceOf(CommentAlreadyExistException.class);
        }
    }
}
