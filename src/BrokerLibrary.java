import Commons.Address;
import MessageMarshaller.Marshaller;
import MessageMarshaller.Message;
import Registry.Registry;
import RequestReply.Requestor;
import Registry.Entry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class BrokerLibrary {

    public static Object lookUp(String serverName) {
        new Configuration();
        Address dest = Registry.instance().get("Dispatcher");

        Message msg = new Message("Client", "GETADDR " + serverName);
        Requestor r = new Requestor("Client");
        Marshaller m = new Marshaller();
        byte[] bytes = m.marshal(msg);

        bytes = r.deliver_and_wait_feedback(dest, bytes);
        Message answer = m.unmarshal(bytes);
        System.out.println("Client received message " + answer.data + " from " + answer.sender);

        String[] words = answer.data.split("\\s+");
        System.out.println(words[1] + " " + words[2]);
        int port = Integer.parseInt(words[1]);
        String destination = words[2];
        Entry serverAddress = new Entry(destination, port);

        String[] parts = serverName.split("Server");
        String serverNamePrefix = parts[0];

        String proxyClientClassName =  serverNamePrefix + "Proxy" + "Client";

        try {

            Class<?> proxyClientClass = Class.forName(proxyClientClassName);
            Constructor<?> constructor = proxyClientClass.getConstructor(Entry.class);
            Object proxyClientInstance = constructor.newInstance(serverAddress);

            return proxyClientInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void rebind(String serverName, int port, String dest, Object weather) {
        new Configuration();

        Address dispatcher = Registry.instance().get("Dispatcher");
        Message msg = new Message("Server", "REQ " + serverName + " " + port + " "+ dest);
        Requestor s_d = new Requestor("Server");
        Marshaller m = new Marshaller();
        byte[] bytes = m.marshal(msg);
        bytes = s_d.deliver_and_wait_feedback(dispatcher, bytes);
        Message answer = m.unmarshal(bytes);
        System.out.println("Server received message " + answer.data + " from " + answer.sender);

        String proxyClassName = "Proxy" + serverName;

        try {
            Class<?> proxyClass = Class.forName(proxyClassName);
            Object proxyInstance = proxyClass.getDeclaredConstructor().newInstance();

            Method startServerMethod = proxyClass.getMethod("startServer", int.class, String.class, Object.class);
            startServerMethod.invoke(proxyInstance, port, dest, weather);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
