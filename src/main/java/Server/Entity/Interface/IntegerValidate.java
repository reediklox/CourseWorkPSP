package Server.Entity.Interface;

public interface IntegerValidate {
    public static Integer SetInteger(Integer integer){
        if(integer < 0) {return -integer;}
        else return integer;
    }
}
