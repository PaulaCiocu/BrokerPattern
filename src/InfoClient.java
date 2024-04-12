import Commons.Address;
import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;
import Registry.Registry;
import RequestReply.Requestor;
import Registry.Entry;
public class InfoClient {
	public static void main(String args[])
	{
		new Configuration();

		Address dest= Registry.instance().get("Dispatcher");

		Message msg= new Message("Client","GETADDR InfoServer ");
		Requestor r = new Requestor("Client");
		Marshaller m = new Marshaller();
		byte[] bytes = m.marshal(msg);

		bytes = r.deliver_and_wait_feedback(dest, bytes);
		Message answer = m.unmarshal(bytes);
		System.out.println("Client received message "+answer.data+" from "+answer.sender);

		String[] words = answer.data.split("\\s+");
		System.out.println(words[1] + " " + words[2]);
		int port = Integer.parseInt(words[1]);
		String destination = words[2];
		Entry serverAddress = new Entry(destination,port);

		Message msgServer =new Message("Client", "Hi I am the client ");
		Marshaller mServer = new Marshaller();
		byte[] bytesServer = mServer.marshal(msgServer);

		bytesServer = r.deliver_and_wait_feedback(serverAddress,bytesServer);

		Message serverAnswer = m.unmarshal(bytesServer);
		System.out.println("Client received message "+serverAnswer.data+" from "+serverAnswer.sender);





	}

}
