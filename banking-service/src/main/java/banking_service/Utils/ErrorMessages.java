package banking_service.Utils;

public final class ErrorMessages {

    public static final String FIRST_NAME_BLANK = "Vorname darf nicht leer sein";
    public static final String LAST_NAME_BLANK = "Nachname darf nicht leer sein";
    public static final String BALANCE_NEGATIVE = "Kontostand darf nicht negativ sein";
    public static final String AMOUNT_POSITIVE_REQUIRED = "Betrag muss positiv sein";
    public static final String AMOUNT_NULL = "Betrag darf nicht null sein";
    public static final String USER_ID_NULL = "userId darf nicht null sein";
    public static final String INVOICING_PARTY_BLANK = "invoicingParty darf nicht leer sein";
    public static final String INVOICING_PARTY_UNKNOWN = "Unbekannter Rechnungssteller";
    public static final String DECIMALS_TOO_LONG = "Decimals duerfen maximal 2 Stellen haben";

    public static final String USER_NOT_FOUND = "User nicht gefunden";
    public static final String TRANSACTION_NOT_FOUND = "Transaktion nicht gefunden";

    public static String userNotFound(Long id) { return USER_NOT_FOUND + ": " + id; }
    public static String transactionNotFound(Long id) { return TRANSACTION_NOT_FOUND + ": " + id; }

    private ErrorMessages() {}
}