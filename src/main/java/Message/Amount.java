package Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Amount {
    public List<Integer> getAmount() {
        return amount;
    }

    private final List<Integer> amount = Arrays.asList(500, 1000, 2000, 3000, 5000, 10000, 15000, 25000, 50000, 100000, 200000,
            400000, 800000, 1500000, 3000000);

    public List<String> getStrAmount() {
        List<String> strAmount = new ArrayList<>();
        for (int i=0; i<getAmount().size(); i++) {
            strAmount.add(Integer.toString(getAmount().get(i)));
        }
        return strAmount;
    }
}
