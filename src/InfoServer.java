
import RequestReply.*;
import MessageMarshaller.*;
import Registry.*;
import Commons.Address;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.ServerSocket;


class ServerTransformer implements ByteStreamTransformer
{
	private MessageServer originalServer;

	public ServerTransformer(MessageServer s)
	{
		originalServer = s;
	}

	public byte[] transform(byte[] in)
	{
		Message msg;
		Marshaller m = new Marshaller();
		msg = m.unmarshal(in);

		Message answer = originalServer.get_answer(msg);

		byte[] bytes = m.marshal(answer);
		return bytes;

	}
}


class MessageServer
{
	Object weather;
	public MessageServer(Object weather){
		this.weather = weather;
	}
	public Message get_answer(Message msg) {
		Message answer;
		String[] words = msg.data.split("\\s+");

		try {
			Class<?> weatherClass = weather.getClass();
			Method method = weatherClass.getMethod(words[0]);

			Object result = method.invoke(weather);
			answer = new Message("Server", result.toString());
			System.out.println("Server received " + msg.data + " from " + msg.sender);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return answer;
	}
}


public class InfoServer
{
	public static void main(String[] args) {
		Object weather = new WeatherDataCenter();
		BrokerLibrary.rebind("InfoServer",1111, "127.0.1.0 ",weather);
	}

}