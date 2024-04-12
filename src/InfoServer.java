
import RequestReply.*;
import MessageMarshaller.*;
import Registry.*;
import Commons.Address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	public Message get_answer(Message msg)
	{
		System.out.println("Server received " + msg.data + " from " + msg.sender);
		Message answer = new Message("Server", "I am alive");
		return answer;
	}
}

public class InfoServer
{


	public static void main(String[] args) {

		new Configuration();

		Address dispatcher = Registry.instance().get("Dispatcher");

		Message msg = new Message("Server", "REQ InfoServer 1111 127.0.0.1");
		Requestor s_d = new Requestor("Server");
		Marshaller m = new Marshaller();
		byte[] bytes = m.marshal(msg);
		bytes = s_d.deliver_and_wait_feedback(dispatcher, bytes);
		Message answer = m.unmarshal(bytes);
		System.out.println("Server received message " + answer.data + " from " + answer.sender);


		ByteStreamTransformer transformer = new ServerTransformer(new MessageServer());

		Entry myAddr = new Entry("127.0.0.1", 1111);
		ServerReplyer r = new ServerReplyer("Server", myAddr);

		while (true) {
			r.receive_transform_and_send_feedback(transformer);
		}
	}


}