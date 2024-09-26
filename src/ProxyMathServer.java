import Registry.Entry;
import RequestReply.ByteStreamTransformer;
import RequestReply.ServerReplyer;

public class ProxyMathServer {
    public void startServer(int port, String dest, Object object) {
        ByteStreamTransformer transformer = new ServerMathTransformer(new MessageMathServer(object));

        Entry myAddr = new Entry(dest, port);
        ServerReplyer r = new ServerReplyer("Server", myAddr);

        while (true) {
            r.receive_transform_and_send_feedback(transformer);
        }
    }
}
