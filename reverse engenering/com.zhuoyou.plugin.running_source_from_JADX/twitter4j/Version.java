package twitter4j;

public final class Version {
    private static final String TITLE = "Twitter4J";
    private static final String VERSION = "4.0.5-SNAPSHOT(build: bbbd24f6c492677f65d3956ed6b74a2ae75d5bc3)";

    private Version() {
        throw new AssertionError();
    }

    public static String getVersion() {
        return VERSION;
    }

    public static void main(String[] args) {
        System.out.println("Twitter4J 4.0.5-SNAPSHOT(build: bbbd24f6c492677f65d3956ed6b74a2ae75d5bc3)");
    }
}
