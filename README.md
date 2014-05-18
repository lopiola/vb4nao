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

