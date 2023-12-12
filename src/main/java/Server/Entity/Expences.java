package Server.Entity;

import Server.Entity.Interface.IntegerValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Expences {
    @Setter
    private Integer expency_id;
    @Setter
    private String expency_type;
    private Integer expency_amount;

    public void setExpency_amount(Integer expency_amount){
        this.expency_amount = IntegerValidate.SetInteger(expency_amount);
    }

    public Expences() {}

    public Expences(Integer expency_id, String expency_type, Integer expency_amount){
        this.expency_id = expency_id;
        this.expency_type = expency_type;
        setExpency_amount(expency_amount);
    }

    @Override
    public String toString(){
        return "Expences{" +
                "expency_id=" + expency_id +
                ", expency_type='" + expency_type + '\'' +
                ", expency_amount='" + expency_amount + '}';
    }
}
