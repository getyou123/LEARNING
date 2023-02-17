package org.example;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rev.mapper.EmpMapper;
import com.rev.pojo.Emp;
import com.rev.pojo.EmpExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void TestInert() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);

        /**
         * 配置条件是null
         * 那就是查询所有的
         */
        List<Emp> emps = mapper.selectByExample(null);
        System.out.println("结果：" + emps);


        /**
         * QBC示例
         */
        EmpExample empExample = new EmpExample();
        EmpExample.Criteria admin1 = empExample.createCriteria().andEmpNameEqualTo("admin1");
        System.out.println("结果：" + admin1);

        /**
         * 分页测试
         */
        // 开启分页
        PageHelper.startPage(1, 2);
        List<Emp> emp1 = mapper.selectByExample(null);
        System.out.println("分页结果：" + emp1);
        // 生成当前页的前面后面3个页面的信息
        PageInfo<Emp> empPageInfo = new PageInfo<>(emp1, 3);
        System.out.println("当前页面大小：" + empPageInfo.getSize());
        System.out.println("当前页面页码：" + empPageInfo.getPageNum());
        // PageInfo还有其他属性 可以通过getXX获取
        /**
         pageNum:当前页的页码
         pageSize:每页显示的条数
         size:当前页显示的真实条数
         total:总记录数
         pages:总页数
         prePage:上一页的页码
         nextPage:下一页的页码
         isFirstPage/isLastPage:是否为第一页/最后一页 hasPreviousPage/hasNextPage:是否存在上一页/下一页 navigatePages:导航分页的页码数
         navigatepageNums:导航分页的页码，[1,2,3,4,5]
         */


    }

}
