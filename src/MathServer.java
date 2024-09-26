
import RequestReply.*;
import MessageMarshaller.*;
import Registry.*;
import Commons.Address;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.ServerSocket;


class ServerMathTransformer implements ByteStreamTransformer
{
    private MessageMathServer originalServer;

    public ServerMathTransformer(MessageMathServer s)
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


class MessageMathServer
{
    Object object;
    public MessageMathServer(Object object){
        this.object = object;
    }
    public Message get_answer(Message msg) {
        Message answer;
        String[] words = msg.data.split("\\s+");

        try {
            Class<?> objectClass = object.getClass();
            Method method = objectClass.getMethod(words[0]);

            Object result = method.invoke(object);
            answer = new Message("Server", result.toString());
            System.out.println("Server received " + msg.data + " from " + msg.sender);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return answer;
    }
}


public class MathServer
{
    public static void main(String[] args) {
        Object object = new MathImpl();
        BrokerLibrary.rebind("MathServer",1112, "127.0.0.1", object);
    }

}