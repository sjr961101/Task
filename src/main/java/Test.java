
public class Test {
    public static void main(String[] args) {
        FetchTemperature fetchTemperature =new FetchTemperature();
        System.out.println(fetchTemperature.getTemperature("江苏","苏州","吴中").get());
    }
}
