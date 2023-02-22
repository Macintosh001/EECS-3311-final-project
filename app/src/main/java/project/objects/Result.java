package project.objects;

public class Result<R, E> {
    private R result;
    private E error;

    public Result(R result, E error) {
        this.result = result;
        this.error = error;
    }

    public R getResult() {
        return result;
    }

    public E getError() {
        return error;
    }
}
