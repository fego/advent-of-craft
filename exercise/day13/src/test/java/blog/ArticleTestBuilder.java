package blog;

public class ArticleTestBuilder {
    private final Article article;

    private ArticleTestBuilder(String name, String content) {
        this.article = new Article(name, content);
    }

    public static ArticleTestBuilder anArticle() {
        return new ArticleTestBuilder(
                "Lorem Ipsum",
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore");
    }

    public ArticleTestBuilder withComment(String content, String author) throws CommentAlreadyExistException {
        article.addComment(content, author);
        return this;
    }

    public Article build() {
        return article;
    }
}
