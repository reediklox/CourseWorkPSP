package Server.Entity;

import Server.Entity.Interface.IntegerValidate;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Companies {
    @Setter
    private Integer company_id;
    @Setter
    private String company_name;
    @Setter
    private String company_address;
    @Setter
    private String company_mobile_number;
    private Integer offered_price;

    public void setOffered_price(Integer offered_price){
        this.offered_price = IntegerValidate.SetInteger(offered_price);
    }

    public Companies(){}

    public Companies(Integer company_id, String company_name, String company_address, String company_mobile_number, Integer offered_price)
    {
        this.company_id = company_id;
        this.company_name = company_name;
        this.company_address = company_address;
        this.company_mobile_number = company_mobile_number;
        setOffered_price(offered_price);
    }
    public Companies(String company_name, String company_address, String company_mobile_number, Integer offered_price)
        {
            this.company_name = company_name;
            this.company_address = company_address;
            this.company_mobile_number = company_mobile_number;
            setOffered_price(offered_price);
        }

    @Override
    public String toString(){
        return "Companies{" +
                "company_id=" + company_id +
                ", company_name='" + company_name + '\'' +
                ", company_address='" + company_address + '\'' +
                ", company_mobile_number='" + company_mobile_number + '\'' +
                ", offered_price='" + offered_price + '}';
    }

}
