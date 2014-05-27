vb4nao
======

Projekt umożliwia przeglądanie stron internetowych przy pomocy interfejsu głosowego robota Nao. Aplikacja pozwala na dyktowanie robotowi adresów dowolnych stron oraz korzystanie z predefiniowanych w programie adresów. Robot po dokonaniu parsowania umożliwia wybór poszczególnych sekcji strony, które może następnie przeczytać. Komunikacja między użytkownikiem, a robotem może przebiegać zarówno głosowo, jak i za pośrednictwem konsoli.

1. Budowanie projektu
---------------------

Do uruchomienia aplikacji potrzebne jest oprogramowanie:

   - Java RE (w wersji 6 lub nowsza)
   - Maven
   - Git

Aby zbudować i uruchomić program należy wykonać:

    ~$ git clone https://github.com/lopiola/vb4nao.git
    ~$ cd vb4nao/
    ~$ make

Projekt zostanie ściągnięty, zbudowany i wszystkie potrzebne pliki trafią do katalogu release


2. Uruchamianie aplikacji
-------------------------

Linux/OS X:

    ~$ cd release/
    release ~$ ./vb4nao.sh
Windows:

    ~$ cd release/
    release ~$ vb4nao.cmd

3. Symulowanie robota
-------------------------

Możliwe jest zasymulowanie Nao dzięki programowi webots. Wprawdzie nie ma obsługi mikrofonu i głośników, \ 
ale w konsoli można zobaczyć tekst, jaki jest "mówiony" za pomocą tts. W tym celu należy odpalić webots, \
sprawdzić na jakim IP znajduje się robot (np. poprzez menu "connect" w Choreography), ustawić IP w properties.cfg \
i uruchomić aplikację.


4. Wizja:
-------------------------
   
**Cel projektu**

Projekt ma na celu stworzenie funkcjonalnej przeglądarki głosowej dla robota NAO. Użytkownik ma możliwość zadania strony dowolnej internetowej, która będzie mu odczytana zgodnie z jego żądaniami. Zostanie zbadana przydatność aplikacji do wykonywania prostych operacji na stronach WWW. Docelowo mogłaby ona znaleźć zastosowanie jako narzędzie dla osób niepełnosprawnych.
   
**Wymagania funkcjonalne**
   - Predefiniowanie adresów stron
   - Możliwość podania dowolnego adresu
   - Wybór sekcji strony do odczytania
   - Zestaw wygodnych komend sterujących
   - Możliwość śledzenia działania programu w konsoli
   - Możliwość uruchomienia zarówno na emulatorze jak i prawdziwym robocie
   
**Wymagania niefunkcjonalne**
   - Łatwa instalacja
   - Łatwa obsługa
   - Obsługa popularnych platform (Windows, Linux)
   
**Ograniczenia programu**
   - Program będzie obsługiwał jedynie strony, które są napisane w standaryzowany sposób i da się wydzielić na nich sekcje
   - Robot będzie reagował jedynie na język angielski
   
**Technologia**
   - Java (Naoqi Java SDK)
   - Wbudowany Text To Speech
   - Wbudowany Speech Recognition

**Sposób działania aplikacji**

Aplikacja będzie działać na zasadzie dialogu z użytkownikiem. Robot nasłuchuje, czekając na komendy. Jeśli zarejestrowany dźwięk zostanie prawidłowo zinterpretowany, wykonane zostanie zapytanie HTTP pobierające zawartość strony. Po analizie treści strony zostaną wydzielone poszczególne sekcje wraz z tytułami. Użytkownik będzie mógł wybrać konkretną sekcję do odczytania. W każdej chwili możliwe będzie powrócenie do stanu początkowego i wybranie innej strony, czy też przerwanie obecnej operacji. Za pomocą prostych komunikatów robot będzie informował o statusie obecnej operacji lub ewentualnych błędach.
   
**Realizacja**

Pierwsza wersja programu będzie testowana przy użyciu symulatora Choreography. Docelowo aplikacja będzie działać na rzeczywistym robocie.
