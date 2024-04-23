import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.util.Scanner;

public class ConvertidorDivisas {
    private final Scanner scanner;

    public ConvertidorDivisas() {
        this.scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        System.out.println("Sea bienvenido/a al Conversor de Divisas =]");
        while (true) {
            System.out.println("1) Dolar -> Peso argentino");
            System.out.println("2) Peso argentino -> Dolar");
            System.out.println("3) Dolar -> Real brasileño");
            System.out.println("4) Real brasileño -> Dolar");
            System.out.println("5) Peso colombiano -> Dolar");
            System.out.println("6) Dolar -> Peso colombiano");
            System.out.println("7) Salir");
            System.out.print("Elija una opcion valida: ");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    convertDollarToArgentinePeso();
                    break;
                case 2:
                    convertArgentinePesoToDollar();
                    break;
                case 3:
                    convertDollarToBrazilianReal();
                    break;
                case 4:
                    convertBrazilianRealToDollar();
                    break;
                case 5:
                    convertColombianPesoToDollar();
                    break;
                case 6:
                    convertDollarToColombianPeso();
                    break;
                case 7:
                    System.out.println("Gracias por usar el Conversor de Divisas. ¡Hasta luego!");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción del menú.");
            }
        }
    }

    private double getExchangeRate(String baseCurrency, String targetCurrency) throws IOException {
        String apiKey = "1d83f484abb548eeca5cfb22";
        URL url = new URL("https://api.exchangerate-api.com/v4/latest/" + baseCurrency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Apikey", apiKey);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONObject rates = jsonResponse.getJSONObject("rates");
        return rates.getDouble(targetCurrency);
    }

    private void convertCurrency(String fromCurrency, String toCurrency) throws IOException {
        System.out.print("Ingrese la cantidad de " + fromCurrency + ": ");
        double amount = scanner.nextDouble();
        double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
        double convertedAmount = amount * exchangeRate;
        System.out.println(amount + " " + fromCurrency + " es igual a " + convertedAmount + " " + toCurrency);
    }

    private void convertDollarToArgentinePeso() throws IOException {
        convertCurrency("USD", "ARS");
    }

    private void convertArgentinePesoToDollar() throws IOException {
        convertCurrency("ARS", "USD");
    }

    private void convertDollarToBrazilianReal() throws IOException {
        convertCurrency("USD", "BRL");
    }

    private void convertBrazilianRealToDollar() throws IOException {
        convertCurrency("BRL", "USD");
    }

    private void convertColombianPesoToDollar() throws IOException {
        convertCurrency("COP", "USD");
    }

    private void convertDollarToColombianPeso() throws IOException {
        convertCurrency("USD", "COP");
    }

    public static void main(String[] args) throws IOException {
        ConvertidorDivisas converter = new ConvertidorDivisas();
        converter.start();
    }
}
