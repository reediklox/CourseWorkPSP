package Server.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.random.RandomGenerator;

@Getter
@Setter
public class Tax {
    private Integer tax_id;
    private String tax_type;
    private Integer tax_percent;

    public Tax() {}

    public Tax(Integer tax_id, String tax_type, Integer tax_percent){
        this.tax_id = tax_id;
        this.tax_type = tax_type;
        if (tax_percent > 100 || tax_percent < 1){
            tax_percent = RandomGenerator.getDefault().nextInt(1, 101);
        }
        this.tax_percent = tax_percent;
    }
}
