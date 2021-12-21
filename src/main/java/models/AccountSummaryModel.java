package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountSummaryModel {
    private String accountName;
    private String creditCard;
    private String balance;

    @JsonIgnore
    private WebElement accountLink;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountSummaryModel)) {
            return false;
        }
        AccountSummaryModel that = (AccountSummaryModel) o;
        return accountName.equals(that.accountName) && Objects.equals(creditCard, that.creditCard) &&
            balance.equals(that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, creditCard, balance);
    }
}
