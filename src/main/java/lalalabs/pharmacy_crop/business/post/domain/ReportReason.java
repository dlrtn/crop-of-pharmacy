package lalalabs.pharmacy_crop.business.post.domain;

public enum ReportReason {
    OTHER(0),
    ADVERTISEMENT_OR_PORNOGRAPHY(1),
    IRRITATING_CONTENT(2),
    SPAM(3);

    private final int value;

    ReportReason(int value) {
        this.value = value;
    }

    public static ReportReason from(int reportReason) {
        return switch (reportReason) {
            case 0 -> OTHER;
            case 1 -> ADVERTISEMENT_OR_PORNOGRAPHY;
            case 2 -> IRRITATING_CONTENT;
            case 3 -> SPAM;
            default -> throw new IllegalArgumentException("Unexpected value: " + reportReason);
        };
    }

    public int getValue() {
        return value;
    }
}
