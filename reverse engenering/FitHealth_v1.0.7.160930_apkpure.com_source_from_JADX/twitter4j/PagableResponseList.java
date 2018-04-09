package twitter4j;

public interface PagableResponseList<T extends TwitterResponse> extends ResponseList<T>, CursorSupport {
    long getNextCursor();

    long getPreviousCursor();

    boolean hasNext();

    boolean hasPrevious();
}
