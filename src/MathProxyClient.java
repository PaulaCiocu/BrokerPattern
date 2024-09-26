import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;
import Registry.Entry;
import RequestReply.Requestor;

public class MathProxyClient implements Math{
    private Entry serverAddress;

    public MathProxyClient(Entry serverAddress) {
        this.serverAddress = serverAddress;

    }

    @Override
    public String getMultiplication() {
        Requestor r = new Requestor("Client");
        Message msgServer = new Message("Client", "getMultiplication");
        Marshaller mServer = new Marshaller();
        byte[] bytesServer = mServer.marshal(msgServer);

        bytesServer = r.deliver_and_wait_feedback(serverAddress, bytesServer);

        Message serverAnswer = mServer.unmarshal(bytesServer);
        return serverAnswer.data;
    }

    @Override
    public String getSum() {
        Requestor r = new Requestor("Client");

        Message msgServer = new Message("Client", "getSum");
        Marshaller mServer = new Marshaller();
        byte[] bytesServer = mServer.marshal(msgServer);

        bytesServer = r.deliver_and_wait_feedback(serverAddress, bytesServer);

        Message serverAnswer = mServer.unmarshal(bytesServer);
        return serverAnswer.data;
    }

}
