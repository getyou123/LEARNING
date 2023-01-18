import com.getyou123.imperial.court.dao.BaseDao;
import com.getyou123.imperial.court.entity.Emp;
import com.getyou123.imperial.court.util.JdbcUtilImperialCourt;
import org.junit.Test;
import java.sql.Connection;
import java.util.List;

public class ImperialCourtTest {

    /**
     * 测试链接是否可以获取到
     */
    @Test
    public void testGetConnection() {
        Connection connection = JdbcUtilImperialCourt.getConnection();
        System.out.println("connection = " + connection);
        JdbcUtilImperialCourt.releaseConnection(connection);
    }

    /**
     * 测试需要的BaseDao
     */

    private BaseDao<Emp> baseDao = new BaseDao<>();
    @Test
    public void testGetSingleBean() {
        String sql = "select emp_id empId,emp_name empName,emp_position empPosition,login_account loginAccount,login_password loginPassword from t_emp where emp_id=?";
        Emp emp = baseDao.getSingleBean(sql, Emp.class, 1);
        System.out.println("emp = " + emp);
    }
    @Test
    public void testGetBeanList() {
        String sql = "select emp_id empId,emp_name empName,emp_position empPosition,login_account loginAccount,login_password loginPassword from t_emp";
        List<Emp> empList = baseDao.getBeanList(sql, Emp.class);
        for (Emp emp : empList) {
            System.out.println("emp = " + emp);
        }
    }

    @Test
    public void testUpdate() {
        String sql = "update t_emp set emp_position=? where emp_id=?";
        String empPosition = "minister";
        String empId = "3";
        int affectedRowNumber = baseDao.update(sql, empPosition, empId);
        System.out.println("affectedRowNumber = " + affectedRowNumber);

    }


}