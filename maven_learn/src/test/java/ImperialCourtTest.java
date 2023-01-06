import com.getyou123.imperial.court.util.JdbcUtilImperialCourt;
import org.junit.Test;
import java.sql.Connection;

public class ImperialCourtTest {
    @Test
    public void testGetConnection() {
        Connection connection = JdbcUtilImperialCourt.getConnection();
        System.out.println("connection = " + connection);
        JdbcUtilImperialCourt.releaseConnection(connection);
    }
}