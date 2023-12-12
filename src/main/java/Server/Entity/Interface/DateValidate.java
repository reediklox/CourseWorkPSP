package Server.Entity.Interface;

public interface DateValidate {
    static String setDate(String date){
        if (!date.matches("\\d{2}.\\d{2}.\\d{4}")){
            return "01.01.2023";
        }
        return date;
    }
}
