package kalyan.ogirala.UBStest;

import java.util.Scanner;
import java.util.Optional;
import service.CalculatorService;

public class CalculatorApp {
    public static void main(String[] args) throws Exception {
        String inputText;
        do {
            System.out.print("Enter Text:");
            inputText = new Scanner(System.in).nextLine();
            Optional<Integer> result = CalculatorService.getCalculatorServiceInstance().add(inputText);
            if (result.isPresent())
                System.out.println("Sum: " + result.get());
            else
                System.out.println("Invalid input!");
        }
        while (!inputText.toUpperCase().equals("QUIT"));
    }
}
