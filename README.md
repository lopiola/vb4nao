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

Możliwe jest zasymulowanie Nao dzięki programowi webots. Wprawdzie nie ma obsługi mikrofonu i głośników,
ale w konsoli można zobaczyć tekst, jaki jest "mówiony" za pomocą tts. W tym celu należy odpalić webots,
sprawdzić na jakim IP znajduje się robot (np. poprzez menu "connect" w Choreography), ustawić IP w properties.cfg
i uruchomić aplikację.

4. Dokumentacja
-------------------------
Znajduje się na wiki projektu:

https://github.com/lopiola/vb4nao/wiki