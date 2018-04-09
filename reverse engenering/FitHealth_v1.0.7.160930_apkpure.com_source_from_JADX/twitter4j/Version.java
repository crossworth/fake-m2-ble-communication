package twitter4j;

public final class Version {
    private static final String TITLE = "Twitter4J";
    private static final String VERSION = "4.0.4";

    private Version() {
        throw new AssertionError();
    }

    public static String getVersion() {
        return VERSION;
    }

    public static void main(String[] args) {
        System.out.println("Twitter4J 4.0.4");
    }
}
