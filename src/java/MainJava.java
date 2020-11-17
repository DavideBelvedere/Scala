import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainJava {
    public static void main(String[] args){
        List<String> d=new ArrayList<String>().stream().filter(x -> x.equals("")).collect(Collectors.toList());
        Integer capf= Integer.parseInt("01");
        System.out.println("cd");
    }
}
