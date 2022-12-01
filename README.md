Celem zadania jest zaimplementowanie usługi do rejestracji kont bankowych z subkontami w
PLN, USD.
Wytworzony serwis powinien pozwalać na zakładanie konta oraz wymianę walut między
subkontami.
Wymiana walut odbywa się w parze PLN &lt;-&gt; USD
Wymagania funkcjonalne:
1. Usługa powinna udostępnić API do utworzenia konta użytkownika. Rejestracja konta użytkownika
   powinna zawierać dane:
   a. Imię i nazwisko
   b. Pesel
   c. Stan początkowy konta w złotówkach
2. Jedna osoba może posiadać tylko jedno konto.
3. Aby założyć konto osoba powinna być pełnoletnia.
4. Konto użytkownika identyfikowane jest przez jego PESEL (na potrzeby zadania zakładamy brak
   powtarzających się PESELi)
5. Usługa powinna udostępnić API pobrania informacji o danym koncie wraz z posiadanymi
   funduszami w różnych walutach.
6. Usługa powinna udostępnić API do wymiany wskazanej kwoty we wskazanej walucie na inną
   wskazaną walutę.
7. Usługa powinna obsługiwać wymiany w parach PLN &lt;-&gt; USD
   
Wymagania niefunkcjonalne:
1. Aplikacja powinna być budowana za pomocą wybranego przez kandydata narzędzia do
   budowania (np. Gradle, Maven)
2. Aplikacja powinna zostać napisana w języku Java lub Kotlin.
3. Aplikacja dane o aktualnym kursie wymiany walut powinna pobierać z publicznego API NBP
   (https://api.nbp.pl/)
4. Aplikacja nie musi posiadać persystentnej bazy danych (może używać bazy danych w pamięci)
5. Źródła aplikacji powinny zostać dostarczone w postaci publicznego repozytorium na wybranym
   przez kandydata portalu (np. Github, Gitlab, Bitbucket)