//package cn.njcit;
//
//import org.apache.commons.lang.time.DateFormatUtils;
//import org.apache.commons.lang.time.DateUtils;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Calendar;
//import java.util.Date;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration("file:src/main<%=request.getContextPath()%>/resources/META-INF/context/appContext-core.xml")
//public class AppTests {
//   /* private MockMvc mockMvc;
//
//    @SuppressWarnings("SpringJavaAutowiringInspection")
//    @Autowired
//    protected WebApplicationContext wac;
//
//    @Before
//    public void setup() {
//        this.mockMvc = webAppContextSetup(this.wac).build();
//    }
//
//    @Test
//    public void simple() throws Exception {
//        mockMvc.perform(get("/recharge/callback/233.htm"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("hello"));
//    }*/
//    @Test
//    public void test(){
////        String arrarys[] = {"aaaa","bbbb"};
////        System.out.println(arrarys);
////
////        System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(1401984000000L);
//        System.out.println(DateFormatUtils.format(calendar,"yyyy-MM-dd HH:mm:ss"));
//    }
//
//}
