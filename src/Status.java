public enum Status {
    TODO("todo"), IN_PROGRESS("in-progress"), DONE("done");
    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status fromValue(String text) {
        for (Status s : Status.values()) {
            if (s.value.equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + text);
    }
}