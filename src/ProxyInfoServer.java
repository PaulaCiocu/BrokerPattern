import RequestReply.ByteStreamTransformer;
import RequestReply.ServerReplyer;
import Registry.Entry;
public class ProxyInfoServer {

    public void startServer(int port, String dest, Object weather) {
        ByteStreamTransformer transformer = new ServerTransformer(new MessageServer(weather));

        Entry myAddr = new Entry(dest, port);
        ServerReplyer r = new ServerReplyer("Server", myAddr);

        while (true) {
            r.receive_transform_and_send_feedback(transformer);
        }
    }

}
