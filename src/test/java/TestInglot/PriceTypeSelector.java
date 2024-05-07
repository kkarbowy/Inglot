package TestInglot;

public enum PriceTypeSelector {
    PRICE(".price"), PRICE_HAS_DISCOUNT(".price.has-discount");
    private String notation;

    PriceTypeSelector(String notation) {
        this.notation = notation;
    }
    public String getNotation() {
        return notation;
    }
}
