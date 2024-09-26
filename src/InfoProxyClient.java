import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;
import RequestReply.Requestor;
import Registry.Entry;

public class InfoProxyClient implements Weather{

    private Entry serverAddress;

    public InfoProxyClient(Entry serverAddress) {
        this.serverAddress = serverAddress;

    }


    @Override
    public String getWeather() {
        Requestor r = new Requestor("Client");
        Message msgServer = new Message("Client", "getWeather");
        Marshaller mServer = new Marshaller();
        byte[] bytesServer = mServer.marshal(msgServer);

        bytesServer = r.deliver_and_wait_feedback(serverAddress, bytesServer);

        Message serverAnswer = mServer.unmarshal(bytesServer);
        return serverAnswer.data;
    }

    @Override
    public String getTemperature() {
        Requestor r = new Requestor("Client");

        Message msgServer = new Message("Client", "getTemperature");
        Marshaller mServer = new Marshaller();
        byte[] bytesServer = mServer.marshal(msgServer);

        bytesServer = r.deliver_and_wait_feedback(serverAddress, bytesServer);

        Message serverAnswer = mServer.unmarshal(bytesServer);
        return serverAnswer.data;
    }
}
