import Registry.Entry;
import Registry.Registry;


class Configuration
{
	public Configuration()
	{

		Entry dispatcher = new Entry("127.0.0.1", 8888);
		Registry.instance().put("Dispatcher", dispatcher);

		Entry server1 = new Entry("127.0.0.1", 1111);
		Registry.instance().put("Server", server1);
		
		Entry client1 = new Entry("127.0.0.1", 1112);
		Registry.instance().put("Client1", client1);

		//Entry entryc2 = new Entry("127.0.0.1", 1113);
		//Registry.instance().put("Client2", entryc2);

	}
}