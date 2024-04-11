import Commons.Address;
import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;
import Registry.Registry;
import RequestReply.Requestor;

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
	}

}
