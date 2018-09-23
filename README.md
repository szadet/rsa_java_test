Idea aplikacji jest symulacja bezpiecznej authoryzacji do serwera z wykorzystaniem różnych algorytmów potęgowanie modularnego z możliwością użycia do czterech wątków w przypadku dekryptowania. 

Przed rozpoczęciem proszę zmienić ustawienia dostępu bazy danych myqsl w src/authorization/Confidential.java.

Odpalić za pomocą mysql -u -p -h 127.0.0.1 account < account.sql

Algorytm drabiny Montgomergo dostępny w joye-ladder.pdf.
Drugi to użycie chinskiego tweirdzenia o resztach.
https://pl.wikipedia.org/wiki/Chi%C5%84skie_twierdzenie_o_resztach

Niestety jest jakiś problem z generowaniem kluczy jeszcze prawodopodobnie BigNumber co objawia się czasami błądem przy kryptowaniu referencji. Referencja(wbudowane potęgowania modularne) używa mnożenbia Montgomergo przez co zawsze będzie zancznie szybsza niż odpowiednik "trial devision". 
W GUI najlepiej można przetestować powinno działać niezawodnia dla login tomek hasło tomek.

Dostępne trzy diagaramy UML prezentują architekturę.
Użyte wzorce projektowe.
- dekorator
- obserwator
- metoda szablonowa
- fabryka
- adapter - wariacja na temat
