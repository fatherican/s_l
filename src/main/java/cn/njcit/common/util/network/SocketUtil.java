package cn.njcit.common.util.network;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author YK
 */
public class SocketUtil {

	public static String getUrl(String ip, String port, String path) {
		int nPort = new Integer(port).intValue();
		Socket s = null;
		try {
			s = new Socket(ip, nPort);

			if (s.isConnected()) {
				System.out.println("connected");
			}
			OutputStream output = s.getOutputStream();
			StringBuilder sb = new StringBuilder();
			sb.append("GET " + path + " HTTP/1.1\r\n");
			sb.append("Host:" + ip + "\r\n");
			sb.append("Connection:Close\r\n");
			sb.append("\r\n");

			output.write(sb.toString().getBytes("utf8"));
			// log("Send:" + String.format("%n") + sb.toString());
			output.flush();

			InputStream in = s.getInputStream();
			BufferedInputStream bisIn = new BufferedInputStream(in);

			// log(bisIn.available());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					bisIn, "gb2312"));

			StringBuilder html = new StringBuilder();

			// 等到ready。
			while (!reader.ready()) {
			}

			// 读数据
			String line;
			do {
				line = reader.readLine();
				if (line != null) {
					html.append(line);
				}
			} while (line != null);

			in.close();
			return html.toString();

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return "没有寻找到客户端";
		} catch (IOException ex) {
			ex.printStackTrace();
			return "IO解析异常";
		} finally {
			try {
				s.close();
			} catch (IOException ex2) {
				ex2.printStackTrace();
			}
		}
	}
}