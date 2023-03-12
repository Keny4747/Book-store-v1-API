package pe.todotic.bookstoreapi_s2.web.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
@Data
public class Amount {
    @JsonProperty("currency_code")
    private CurrencyCode currendyCode;

    private String value;

    private BreakDown breakDown;
    public enum CurrencyCode{
        USD
    }
    @Data
    @RequiredArgsConstructor
    public static class BreakDown{
        @NonNull
        @JsonProperty("item_total")
        private Amount itemTotal;
    }
}
