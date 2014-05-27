vb4nao
======

Voice Browser for NAO

1. Budowanie projektu
---------------------

    ~$ make

Projekt zostanie zbudowany i wszystkie potrzebne pliki trafią do katalogu release


2. Uruchamianie aplikacji
-------------------------

    release ~$ ./vb4nao.sh


3. Symulowanie robota
-------------------------

Możliwe jest zasymulowanie NAO dzięki programowi webots. Wprawdzie nie ma obsługi mikrofonu i głośników, \ 
ale w konsoli można zobaczyć tekst, jaki jest "mówiony" za pomocą tts. W ty mcelu należy odpalić webots, \
sprawdzić na jakim IP stoi robot (np. poprzez menu "connect" w Choreography), ustawić IP w properties.cfg \
i odpalić aplikację.


5. Wizja:
-------------------------
   
   Niniejszy dokument stanowi wizję aplikacji, która wykorzystuje możliwości robota NAO do przeglądania internetu za pomocą komunikacji głosowej.
   
Cele projektu
   Projekt ma na celu stworzenie funkcjonalnej przeglądarki głosowej dla robota NAO. Użytkownik ma możliwość zadania strony internetowej, która będzie mu odczytana zgodnie z jego żądaniami. Zostanie zbadana przydatność aplikacji do wykonywania prostych operacji na stronach WWW. Docelowo mogłaby ona znaleźć zastosowanie jako narzędzie dla osób niepełnosprawnych.
   
Wymagania funkcjonalne
   - Predefiniowanie adresów stron
   - Możliwość podania dowolnego adresu
   - Wybór sekcji strony do odczytania
   - Zestaw wygodnych komend sterujących
   - Możliwość śledzenia działania programu w konsoli
   - Możliwość uruchomienia zarówno na emulatorze jak i prawdziwym robocie
   
Wymagania niefunkcjonalne
   - Łatwa instalacja
   - Łatwa obsługa
   - Obsługa popularnych platform (PC, Linux)
   
Ograniczenia programu
   - Program będzie obsługiwał jedynie strony, które są napisane w standaryzowany sposób i da się wydzielić na nich sekcje
   - Robot będzie reagował jedynie na język angielski
   
Technologia
   - Java (Naoqi Java SDK)
   - Wbudowany Text To Speech
   - Wbudowany Speech Recognition
   - Alternatywnie delegacja rozpoznawania mowy np. do chmury, jeśli powyższe nie wystarczy
   7. Sposób działania aplikacji
   Aplikacja będzie działać na zasadzie dialogu z użytkownikiem. Robot będzie nasłuchiwał, czekając na komendy. Jeśli zarejestrowany dźwięk zostanie prawidłowo zinterpretowany, wykonane zostanie zapytanie HTTP o zawartość strony. Następnie zawartość ta zostanie przeanalizowana pod kątem zawartości, co spowoduje wydzielenie sekcji. Użytkownik będzie mógł wybrać sekcję do odczytania. W każdej chwili możliwe będzie powrócenie do stanu początkowego i wybranie innej strony, czy też przerwanie obecnej operacji. Za pomocą prostych komunikatów robot będzie informował o statusie obecnej operacji lub ewentualnych błędach.
   
Realizacja
   Pierwsza wersja programu będzie testowana przy użyciu symulatora Choreography. Docelowo aplikacja będzie działać na rzeczywistym robocie.