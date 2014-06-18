package cn.njcit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/")
public class HelloController {

	@RequestMapping(value ="recharge/callback.htm")
	public @ResponseBody String printWelcome(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String  remoteIp = getRemoteIp(request);
        System.out.println("飞机票  接收到推送的信息,服务器IP:"+remoteIp);
        if("".equals(getRemoteIp(request))){//绑定推送服务器地址。
            System.out.println("接收到51book推送的信息");

        }
        String type = request.getParameter("type");
        System.out.println("51book出票结果:"+type);
        if("1".equals(type)){//出票成功
            String sequenceNo = request.getParameter("sequenceNo");
            String passengerNames =  request.getParameter("passengerNames");
            String ticketNos = request.getParameter("ticketNos");
            String ticketPrice = request.getParameter("ticketPrice");
            String fuelTax = request.getParameter("fuelTax");
            String airportTax = request.getParameter("airportTax");
            String settlePrice = request.getParameter("settlePrice");
            String pnrNo = request.getParameter("pnrNo");
            String oldPnrNo = request.getParameter("oldPnrNo");
            System.out.println("51book 充值成功,本地订单号:"+"外部订单号:"+sequenceNo+"乘客名:"+passengerNames+
                    "票号:"+ticketNos+"票面价:"+ticketPrice+"燃油:"+fuelTax+"机建:"+airportTax+"结算价:"+settlePrice+
                    "当前编码:"+pnrNo+"旧编码:"+oldPnrNo);
        }else if("0".equals(type)){//供应商拒单
            String sequenceNo = request.getParameter("sequenceNo");
            String reason = request.getParameter("reason");
            System.out.println("51book 充值失败，供应商拒单,本地订单号:"+"外部订单号:"+sequenceNo+"拒单原因:"+reason);
        }else if("2".equals(type)){
            String orderNo = request.getParameter("orderNo");
            String refundNo = request.getParameter("refundNo");//退款号
            String refundTime = request.getParameter("refundTime");//退款时间
            String refundPrice = request.getParameter("refundPrice");//退款金额
            System.out.println("51book 充值失败，出票失败,本地订单号:"+"外部订单号:"+orderNo+"退款号:"+refundNo+
                    "退款时间:"+refundTime+"退款金额:"+refundPrice);
        }
        System.out.println(" 返回信息给 51book ");
        PrintWriter pw = response.getWriter();
        pw.print("S");
        pw.flush();
        return "S";
	}

    @RequestMapping(value ="/recharge/zhifubaoReturn")
    public String zhifubaoReturn(HttpServletRequest request,HttpServletResponse response) throws IOException {
       String sequenceNo = request.getParameter("sequenceNo");
       String buyerPaymentAccount = request.getParameter("buyerPaymentAccount");
       String preCharge = request.getParameter("preCharge");
       String orderStatus = request.getParameter("orderStatus");
       System.out.println("sequenceNo:"+sequenceNo+"buyerPaymentAccount:"+buyerPaymentAccount+"preCharge:"+preCharge+"orderStatus:"+orderStatus);
      return null;
    }

    /*
        * 返回客户端ip地址
        *
        * @return
        */
    private String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        ip = ip == null ? "" : ip.trim();

        //System.out.println("客户端ip：" + ip);
        return ip;
    }

    public static void main(String[] args) {
        System.out.println("你好");
    }
}