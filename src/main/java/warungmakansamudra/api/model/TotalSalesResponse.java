package warungmakansamudra.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalSalesResponse {

    private Long eatIn;

    private Long takeAway;

    private Long online;
}
