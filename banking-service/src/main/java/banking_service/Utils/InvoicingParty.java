package banking_service.Utils;

import java.util.Arrays;

public enum InvoicingParty {
    ROULETTE("roulette"),
    SLOTMACHINE("slotmachine");

    private final String value;

    InvoicingParty(String value) {
        this.value = value;
    }

    public String getValue() { return value; }

    public static InvoicingParty fromString(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException(ErrorMessages.INVOICING_PARTY_BLANK);
        }
        return Arrays.stream(values())
                .filter(p -> p.value.equalsIgnoreCase(s))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        ErrorMessages.INVOICING_PARTY_UNKNOWN + ": " + s));
    }
}