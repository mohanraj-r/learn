package helloworld;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.printf("Hello, Received %d args\n", args.length);

        for (String arg: args) {
            System.out.println(arg);
        }
    }
}
