package games;

public class Result<T> {
    private final Exception exception;
    private final T successEntity;

    private Result(Exception error, T successEntity) {
        this.exception = error;
        this.successEntity = successEntity;
    }

    public static <T> Result<T> success(T entity) {
        return new Result<>(null, entity);
    }

    public static <T> Result<T> error(Exception e) {
        return new Result<>(e, null);
    }

    public T get() {
        if (exception != null) {
            throw new IllegalStateException("result is not a success");
        }
        return successEntity;
    }

    public Exception getError() {
        if (successEntity != null) {
            throw new IllegalStateException("result is not an error");
        }
        return exception;
    }
}
