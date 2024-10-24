# SpringBoot Live Book

## Narodziny SpringBoot
- SpringBoot = Spring Framework + Kontener APlikacji + Konfiguracja

### Spring Context
- **Bean** - obiekt zarządzany
- **Spring Context** - uporządkowany zbiór beanów
- [Cała Prawda o Spring IoC Containers | Wykład Pod Norweską Chmurką](https://livebooks.pl/materials/v1/18)
- [Oficjalna dokumentacja Spring Framework](https://docs.spring.io/spring-framework/reference/)
- [Spring Context – Jak Działa Kontener IoC?](https://bykowski.pl/spring-context-jak-dziala-kontener-ioc/)

- Core container składa się z:
  - Bean
  - Core
  - Context
  - SpEL
  
 - Bean z Core tworza Bean Factory
 - Dziedziczenia: Bean Factory <- Application Context <-WebApplicationContext 
 - XML Bean Factory jest implementacją Bean Factory
 - Bean Factory tworzy Beany korzystająć z konfiguracji oraz POJO
 
 - Konfiguracja:
   - Adnotacje – oznaczenie klas adnotacjami, dzięki którym Spring wie jak dany bean ma zostać zakwalifikowany.
   - Klasa konfiguracyjna – miejsce w którym dostarczamy definicję beanów za pomocą kodu źródłowego (np. Java, Kotlin).
   - Plik XML – ustrukturyzowany plik, który zawiera definicje beanów ujętą w znacznikach XML.

- ApplicationContext dostarcaa  to samo co Bean Factory, a dodatkowo:
  - Zestaw metod umożliwiających na swobodny do beanów.
  - Proste wczytywanie zasobów aplikacji.
  - Możliwość publikowania zdarzeń i nasłuchiwania zdarzeń.
  - Wsparcie dla dostarczania konfiguracji na potrzeby internacjonalizacji.
  
### Definiowanie Beana
- Adnotacje
- `@Component` - ogólny, do mapperów, konwerterów itp.
- `@Repository`- dostęp do danych
- `@Service` - logika biznesowa
- `@Controller/@RestController` - warstwa prezentacja/API

Dobrzy kandydaci do zarzadzania przez kontekst Spring to klasy, które przez cały cykl rzycia maja tylko jeden egzemplarz. 

- Klasa konfiguracyjna
- XML

### Metody wstrzykiwania zależności
- Konstruktor (zalecana) 
- Pole
- Settery

### Hello World
- [Zakładanie projektu](https://livebooks.pl/materials/v1/27)

## Zarządzanie konfiguracją
- Podstawowy plik konfiguracyjny: **application.properties**
- Dobrą praktyką definiowania kluczy jest stosowanie małych liter i oddzielanie słów myślnikami `spring.messages.fallback-to-system-locale=true`
- Innym sposobem jest podanie nazwy klasy + kropka + nazwa zmiennej: `myConfigReader.exampleString=hello!`
- Aby zaczytać konfiguracje do pola w klasie  posługujemy sie adnotacją `@Value` z parametrem `${klucz.do.przyjmowanej.wartosci}`
- [Zarządzanie konfiguracją](https://livebooks.pl/materials/v1/33a)
- [Common Application Properties](https://livebooks.pl/materials/v1/33c)
- [Test wiedzy do rozdziału: 01 KONFIGURACJA I PROFILOWANIE](https://livebooks.pl/materials/v1/34)

```properties
spring.application.name=sp-test
# port jest losowy
server.port=0

page-info.author=Przemek
page-info.creation-date=2019

```

- Konfiguracja z application.properties może byc nadpisana przez:
  - opcje uruchomieniowe VM `-Dpage-info.author=Karol`
  - parametr uruchomieniowy w konsoli: `java -jar .\sp-test-0.0.1-SNAPSHOT.jar --page-info.author=Jacek`
  
- Automatyczne wpisanie parametrow do pól w klasie wykonuje sie za pomocą adnotacji `@ConfigurationProperties(prefix = "page-info")`. Pola muszą mieć **settery** oraz nazwy pól muszą być zgodne nazwami kluczy w konfiguracji po prefixie.

### Profile
- [Profilowanie](https://livebooks.pl/materials/v1/33b)
- Osobna konfiguracja dla różnych środowisk (prod, uat, test itp.)
- Aktywny profil włączamy:
  - z poziomu konfiguracji `spring.profiles.active=dev`
  - podając opcje uruchomieniowe VM `-Dspring.profiles.active=dev`
  - z poziomu konsoli
  
- Za pomocą profili można włączać/wyłączać beany

```
@Component
@Profile("Premium")
public class ShopPremium {
    
    @EventListener(ApplicationReadyEvent.class)
    public void get(){
        System.out.println("ShopPremium");
    }
}
```

## Rest Api
- Implementuje się korzystając z adnotacji:
- `@RestController` - dla klasy
- `@RestMapping` - metoda restowa. Można też adnotować klasy. Wtedy ścieżka dla wszystkich metod będzie poprzedzona wartościa z tej adnotacji.
- [NAJEFEKTYWNIEJSZA DROGA DO TWORZENIA REST API W SPRING](https://livebooks.pl/materials/v1/43)
- [efektywne-rest-api.jpg](https://livebooks.pl/materials/v1/44)
```java
@RestController
@RequestMapping("/api")
public class Shop {

	// http://localhost:8080/api/get-example
	@RequestMapping("/get-example")
	@RequestBody
	public String purchase(){
		return "Siema Byku!"	
	}
}
``` 

### Metody HTTP
- `@RequestMapping` domyślnie stosuje metodę GET
- Można wskazać na inne metody HTTP: GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
- POST  
  - Tworzy nowy zasób.
  - Jest wykorzystywany do wszystkich innych operacji, które nie wpisują się w ramy innych metod.
  - Może służyć do pobierania danych w przypadku kiedy musimy w ramach body dostarczyć dodatkowe parametry.
- PUT
  - Aktualizuje dany zasób na podstawie podanego identyfikatora.
  - Może służyć do tworzenia nowego zasobu jeśli jego identyfikator jest znany.
- PATCH
  - Aktualizuje część wskazanego zasobu.
   
- Dostepne są aliasy metod webowych:
  - `@GetMapping` 
  - `@PostMapping` 
  - `@PutMapping` 
  - `@DeleteMapping` 
  - `@PatchMapping` 

### Parametryzacja metod REST-owych
- Przykazywanie danych do metody odbywa się przez:
  - nagłówek
  - parametr
  - sciężkę
  - ciało
  
#### Przekazywanie przez nagłówek
- Dotyczy metadanych, ustawienia kodowanie, szyfrowania, wybór języka format odpowiedzi itp.
- Stosujemy adnotację `@RequestHeader`

```
@RequestMapping("/get-request-header")
public String requestHeaderExample(@RequestHeader("number") Long number){
	return "delivered by RequestHeader: "+ number;
}
```
#### Przekazywanie przez parametr
- Stosujemy adnotację `@RequestParam`

```java
@RequestMapping("/get-request-param")
public String requestParamExample(@RequestParam("number") Long number){
	return "delivered by RequestParam: "+ number;
}
```

#### Przekazywanie przez adres
- Typowe dla pobierania konkretnego elementu z kolekcji.
- Kiedy API zwraca wiele stron wynikowych
- Stosujemy adnotację `@PathVariable`

```java
@RequestMapping("/get-path-variable/{number}")
public String pathVariableExample(@PathVariable("number") Long number){
	return "delivered by PathVariable: "+ number;
}
```

#### Przekazywanie przez ciało
- Do przekazywania złożonuch obiektów, list, kolekcji.
- Stosujemy adnotację `@RequestBody`.
```java
@RequestMapping("/get-request-body", method=RequestMethod.POST)
public String requestBodyExample(@RequestBody Long number){
	return "delivered by RequestBody: "+ number;
}
```

## Dokumentowanie API