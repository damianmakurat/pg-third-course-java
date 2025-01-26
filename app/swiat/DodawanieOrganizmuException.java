package app.swiat;

public class DodawanieOrganizmuException extends RuntimeException {
    public DodawanieOrganizmuException(String message) {
        super(message);
    }

    public DodawanieOrganizmuException(String message, Throwable cause) {
        super(message, cause);
    }
}