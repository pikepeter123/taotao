import com.taotao.content.jedis.JedisClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 31364 on 2018/3/1.
 * description:
 */
public class JedisTest {

    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
    }

    @Test
    public void testJedisSingle() throws Exception {
        Jedis jedis = new Jedis("192.168.25.128", 6379);
        jedis.set("hello", "123");
        Thread.sleep(10000);
        System.out.println(jedis.get("hello"));
    }

    @Test
    public void test1() throws Exception {
        Jedis jedis = new Jedis("192.168.25.128", 6379);
        System.out.println(jedis.get("hello"));
    }

    @Test
    public void testJedisPool() throws Exception {
        JedisPool jedisPool = new JedisPool("192.168.25.128", 6379);
        Jedis jedis = jedisPool.getResource();
//        jedis.expire("hello", -1);
        System.out.println(jedis.get("hello"));
        jedis.close();
        jedisPool.close();
    }

    @Test
    public void testJedisCluster() throws Exception {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.128", 7001));
        nodes.add(new HostAndPort("192.168.25.128", 7002));
        nodes.add(new HostAndPort("192.168.25.128", 7003));
        nodes.add(new HostAndPort("192.168.25.128", 7004));
        nodes.add(new HostAndPort("192.168.25.128", 7005));
        nodes.add(new HostAndPort("192.168.25.128", 7006));
//        在系统中可以是单例的
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("hello", "123456");
        String hello = jedisCluster.get("hello");
        System.out.println(hello);
//        系统结束前关闭jediscluster
    }

    @Test
    public void testJedisClientPool() throws Exception {
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        String result = jedisClient.get("hello");
        System.out.println(result);
    }
}
