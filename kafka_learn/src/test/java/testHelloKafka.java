import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class testHelloKafka {
    @Test
    public void testSayHello(){
        HelloKafka helloKafka = new HelloKafka();
        String res = helloKafka.sayHello();
        assertEquals("Hello",res);
    }
}
