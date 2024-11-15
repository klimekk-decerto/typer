# 1 - Proof of concept przy użyciu Chat GPT

## Pierwszy prompt

To podsumowując przygotuj mi moduł Springowy na podstawie fasady CompetitionFacade, która będzie miała publiczne metody:

1. Utwórz rozgrywkę - administrator może podać utworzyć nową rozgrywkę podając typ (liga lub turniej), jej nazwę, liczbę drużyn biorących udział w rozgrywkach i jeśli jest to turniej to na ile grup mają być podzielone drużyny.  Harmonogram rozgrywek będzie różnie generowany zależnie jaki jest to typ rozgrywek. Dla ligi każda drużyna musi zagrać z każdą drużyną. Mecze są rozgrywane w ramach rundy. W ramach jednej rundy każda drużyna musi zagrać dokładnie raz. Dla turnieju każda drużyna musi zagrać z każdą drużyną w ramach jednej grupy. Mecze ze wszystkich grup są rozgrywane w ramach tej samej rundy.
2. Ułóż początek drabiny turniejowej - jeśli jest to turniej i zostały rozegrane wszystkie rundy to użytkownik może wybrać, które drużyny przeszły do drabinki turniejowej. Rodzaje drabinek to 1/4 i 1/8. Użytkownik wybiera, która drużyna gra z którą. Jeden etap drabinki to też kolejna runda rozgrywek.
3. Wybierz datę meczu - każdy mecz musi mieć określoną datę odbycia się.
4. Typuj wynik meczu - każdy użytkownik może typować mecz, który jeszcze się nie rozpoczął. Użytkownik może obstawić dokładny wynik lub wybrać, że będzie remis. Jeśli użytkownik trafi dokładny wynik spotkania to otrzymuje 3 pkt w rankingu typowania. Jeśli użytkownik trafi drużynę wygraną to otrzymuje 1 pkt w rankingu typowania. Jeśli trafi remis to otrzymuje 1 pkt. Jeśli nie trafi żadnego to 0 pkt.
5. Podejrzyj ranking - tabela wszystkich użytkowników typujących z listą punktów, które zdobyli typując mecze.
6. Uzupełnij wynik - administrator może wprowadzić rezultat meczu, który już się odbył. Po wprowadzeniu rezultatu meczy rozliczani są użytkownicy, którzy obstawili ten mecz.


## Mała poprawka
Popraw tworzenie rozgrywki. Niech w fasadzie będą osobne metody: jedna do tworzenia rozgrywki ligowej, a druga do tworzenia rozgrywki turniejowej.
Do stworzenia rozgrywki ligowej użytkownik musi podać listę nazw drużyn zamiast ilości.
Do stworzenia rozgrywki turniejowej użytkownik musi podać listę nazw grup, gdzie każda musi zawierać listę nazw drużyn w ramach tej grupy.
Podaj mi sam kod bez dodatkowego tłumaczenia.