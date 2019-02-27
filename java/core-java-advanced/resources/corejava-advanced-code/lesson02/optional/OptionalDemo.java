package optional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("../alice.txt")),
                StandardCharsets.UTF_8);
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));

        Optional<String> optionalValue = wordList.stream().filter(s -> s.contains("fred"))
                .findFirst();
        System.out.println(optionalValue.orElse("No word") + " contains fred");

        Optional<String> optionalString = Optional.empty();
        String result = optionalString.orElse("N/A");
        System.out.println("result: " + result);
        result = optionalString.orElseGet(() -> System.getProperty("user.dir"));
        System.out.println("result: " + result);
     
        optionalValue = wordList.stream().filter(s -> s.contains("red")).findFirst();
        optionalValue.ifPresent(s -> System.out.println(s + " contains red"));

        System.out.println(inverse(4.0).flatMap(OptionalDemo::squareRoot));
        System.out.println(inverse(-1.0).flatMap(OptionalDemo::squareRoot));
        System.out.println(inverse(0.0).flatMap(OptionalDemo::squareRoot));
        Optional<Double> result2 = Optional.of(-4.0).flatMap(OptionalDemo::inverse)
                .flatMap(OptionalDemo::squareRoot);
        System.out.println(result2);
    }

    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}
