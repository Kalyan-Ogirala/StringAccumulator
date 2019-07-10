package service;

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class CalculatorService {

    private CalculatorService() {
    }

    private static CalculatorService calculatorService;

    public static CalculatorService getCalculatorServiceInstance() {
        if (CalculatorService.calculatorService == null) {
            CalculatorService.calculatorService = new CalculatorService();
        }
        return CalculatorService.calculatorService;
    }

    public Optional<Integer> add(String inputText){
        Optional<Integer> sum = Optional.empty();
        try {
            //Check if the input text is empty
            if (inputText.trim().isEmpty())
                return Optional.of(0);

            //Remove leading and trailing double quotes (“,”,")
            inputText = StringUtils.strip(inputText, "“”\"");

            //Replace known delimiters
            String unformatedText = inputText.replace(System.getProperty("line.separator"), "~").replace("\\n", "~").replaceAll(",", "~");

            //Support for different delimiters
            if((unformatedText.substring(0, 2).equals("//"))){
                String formattedText = unformatedText.substring(unformatedText.indexOf('~') + 1, unformatedText.length());
                List<String> delimiters = Arrays.asList(unformatedText.substring(2, unformatedText.indexOf('~')).split("\\|"));
                unformatedText = formattedText.replaceAll(delimiters.stream().map(p-> Pattern.quote(p)).collect(Collectors.joining("|","(",")")), "~");
            }

            //Check if the input contains negative numbers and throw exception
            List<Integer> negativeNumbers =  Arrays.stream(unformatedText.split("~")).map(String::trim).map(i -> Integer.parseInt(i)).filter(i -> i < 0).collect(Collectors.toList());
            if (negativeNumbers.size() != 0)
                throw new RuntimeException("negatives not allowed: " + negativeNumbers.toString());

            //Calculate Sum of numbers below 1000
            sum = Optional.of(Arrays.stream(unformatedText.split("~")).map(String::trim).map(i -> Integer.parseInt(i)).filter(n -> n <= 1000).reduce(0, (a, b) -> a + b));
        }
        catch (NumberFormatException ex) { sum = Optional.empty(); }
        return sum;
    }
}
