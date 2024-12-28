import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Poproszenie użytkownika o podanie trzech pierwszych cyfr numeru konta
        System.out.println("Podaj trzy pierwsze cyfry numeru konta:");
        String bankCodeInput = scanner.nextLine();

        if (bankCodeInput.length() != 3 || !bankCodeInput.matches("\\d+")) {
            System.out.println("Podano nieprawidłowy kod banku. Upewnij się, że podajesz trzy cyfry.");
            return;
        }

        // URL pliku z informacjami o bankach
        String urlString = "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";

        try {
            // Pobranie pliku tekstowego z Internetu
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            boolean bankFound = false;

            // Przeszukiwanie pliku linia po linii
            while ((line = reader.readLine()) != null) {
                // Podział linii na części na podstawie tabulatorów
                String[] parts = line.split("\t");

                // Sprawdzenie, czy linia zawiera co najmniej 2 elementy: kod i nazwę banku
                if (parts.length >= 2) {
                    String bankCode = parts[0].trim();  // Skrócony numer banku (pierwsza kolumna)
                    String bankName = parts[1].trim();  // Nazwa banku (druga kolumna)

                    // Sprawdzenie, czy kod banku zaczyna się od cyfr podanych przez użytkownika
                    if (bankCode.startsWith(bankCodeInput)) {
                        System.out.println("Skrócony numer banku: " + bankCode);
                        System.out.println("Nazwa banku: " + bankName);
                        bankFound = true;
                        break;
                    }
                }
            }

            reader.close();

            if (!bankFound) {
                System.out.println("Nie znaleziono banku o podanym numerze.");
            }

        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas pobierania lub przetwarzania pliku: " + e.getMessage());
        }
    }
}
