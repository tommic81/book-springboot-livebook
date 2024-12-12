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
- [repozytorium DTO](https://github.com/bykowski/springboot2/tree/patterns-dto)
- 
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

### Mapowanie DTO
- Pobrane obiekty z bazy należy jak najszybciej przemapować na DTO.

```
<dependency>
  <groupId>org.modelmapper</groupId>
  <artifactId>modelmapper</artifactId>
  <version>3.0.0</version>
</dependency>
```
### Ograniczanie widoczności
- `@JsonView` - ustawiamy nad polami DTO oraz w metodzie rest api
- Jako parametr podajemy klasy, które mają reprezentować widoki
```
public class Views {
    public static class Public {
    }
}

public class User {
    public int id;

    @JsonView(Views.Public.class)
    public String name;
}
```


### Dokumentowanie API
- [Przykład na github](https://livebooks.pl/materials/v1/52a)
- [SWAGGER 2 – BUDOWANIE, WERYFIKOWANIE I DOKUMENTOWANIE API - PRZYKŁADY](https://livebooks.pl/materials/v1/52b)
- [Swagger UI – przejrzysta wizualizacja zasobów API](https://bykowski.pl/swagger-ui-przejrzysta-wizualizacja-zasobow-api/)
- [Test wiedzy](https://livebooks.pl/materials/v1/52c)
#### Swagger - narzędzie do dokumentowania API
```xml
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-boot-starter</artifactId>
  <version>3.0.0</version>
</dependency>  
```
#### Konfiguracja
- Klasa konfiguracyjna z andoracjami `@Configuration` i `@EnableWebMvc`
- W klasie definiujemy obiekt Docket zamewnaijący metody konfiguracji dla Swagger UI
```
@Configuration
@EnableWebMvc
public class SwaggerConfig{

  @Bean
  public Docket getDocket(){
  	return new Docket(DocumentationType.SWAGGER_2)
  		.select()
  		.apis(RequestHandlerSelectors.any())
  		.paths(PathSelectors.any())
  		.build();
  }
}
```
- adres swaggera: http://localhost:8080/swagger-ui
- Przykład adnotacji dla metody Rest
```
    @ApiOperation(value = "Find student by id", notes = "provide information about student by id")
    @GetMapping("/{id}")
    public Student getStudents(@ApiParam(value = "unique id of student", example = "123") @PathVariable int id) {
        return studentList.stream()
                .filter(student -> student.getId() == id).findFirst().get();
    }
```
- Przykład adnotacji dla modelu
```java
@ApiModel("Personal data of Student")
public class Student {

    @ApiModelProperty("unique id of student")
    private int id;
    private String name;
    private String surname;

    public Student() {
    }

    public Student(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
```
 
 
#### Zawężanie udostępnianej dokumentacji
- Przykładowa definicja ogranicza generowanie dokumentacji dla usług spełniających 2 warunki: endpoint wystawiony na `/api/**` klasa kontrolera znajduje się w ścieżce `pl.bykowski.springbootswaggerexample`
```java

  @Bean
  public Docket getDocket(){
  	return new Docket(DocumentationType.SWAGGER_2)
  		.select()
  		.paths(PathSelectors.ant("/api/**"))
  		.apis(RequestHandlerSelectors.basePackage("pl.bykowski.springbootswaggerexample"))		
  	.build();
  }
```
- Przykład z ApiInfo
```java
@Bean
    public Docket get() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("pl.bykowski.springbootswaggerexample"))
                .build().apiInfo(createApiInfo());
    }

    private ApiInfo createApiInfo() {
        return new ApiInfo("Stundes API",
                "Students database",
                "1.00",
                "http://bykowski.pl",
                new Contact("Przemek", "http://bykowski.pl", "przemek@bykowski.pl"),
                "my own licence",
                "http://bykowski.pl",
                Collections.emptyList()
        );
    }
```

## Spring MVC

- [spring-mvc](https://livebooks.pl/materials/v1/56)
- [Czy Programiście Warto Się Specjalizować?](https://livebooks.pl/materials/v1/57a)
- [Test wiedzy do rozdziału: 03 SPRING MVC](https://livebooks.pl/materials/v1/57b)
### Wzorzec MVC
- Model - jest reprezentacją logiki aplikacji np. klasa Car i jego kilka obiektów
- Controler - przyjmuje dane wejściowe od użytkownika i reaguje na jego poczynania, zarządzając aktualizacje modelu oraz odświeżenie widoków
- View - opisuje jak wyyświetlić pewną część modelu w ramach interfejsu użytkownika

##### Obsługa żadań
1. DispatcherServlet
   + Handler Mapping
   + Controller
   + View Resolver
   + View
   
57