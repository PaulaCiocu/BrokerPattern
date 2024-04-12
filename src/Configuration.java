import Registry.Entry;
import Registry.Registry;


class Configuration
{
	public Configuration()
	{

		Entry dispatcher = new Entry("127.0.0.1", 8888);
		Registry.instance().put("Dispatcher", dispatcher);


	}
}