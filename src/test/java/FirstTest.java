import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;

public class FirstTest {
        private FetchTemperature fetchTemperature = new FetchTemperature();


        @Test
        public void successTest(){
                System.out.println("正常运行");
                Optional<Integer> result = fetchTemperature.getTemperature("江苏","苏州","吴中");
                Assert.assertEquals(Optional.ofNullable(23),result);
        }

        @Test
        public void failedTestBack(){
                System.out.println("传空");
                Assert.assertEquals(Optional.ofNullable(23),fetchTemperature.getTemperature("","",""));
        }

        @Test
        public void failedTestNull(){
                System.out.println("传null");
                ArithmeticException exception = Assertions.assertThrows(
                        ArithmeticException.class,()->{
                                fetchTemperature.getTemperature(null,null,null);
                        }
                );
        }

}
