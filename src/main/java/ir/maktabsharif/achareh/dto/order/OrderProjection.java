package ir.maktabsharif.achareh.dto.order;

public interface OrderProjection {
    Long getId();
    String getDescription();
    Long getUser();
    String getSuggestionPrice();

}