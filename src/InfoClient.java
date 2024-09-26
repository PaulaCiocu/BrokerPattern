import Commons.Address;
import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;
import Registry.Registry;
import RequestReply.Requestor;
import Registry.Entry;
public class InfoClient {
	public static void main(String args[])
	{
		Weather proxyClient = (Weather) BrokerLibrary.lookUp("InfoServer");
		System.out.println(proxyClient.getWeather());
		System.out.println(proxyClient.getTemperature());
	}

}
