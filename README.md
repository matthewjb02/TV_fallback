# Swagger UI

We hebben voor onze POSTMAN collection ook een swagger-UI:

- local: http://localhost:8080/swagger-ui/index.html
- public: https://testvision2-team4.victoriousbeach-ec764c0c.westeurope.azurecontainerapps.io/swagger-ui/index.html

# HULP Start Repository

Bij deze de start-repository voor de HULP opdracht.

In deze repository start een lege Spring-boot applicatie. Deze staat net 1 directory dieper dan je gewend
bent. Dat kun je voor nu negeren, maar zorgt er voor dat de overstap in week 3 net iets prettiger loopt.

Na opstarten kun je alvast kijken op

* http://localhost:8080/actuator voor allerhande debug info (heel soms handig)
* http://localhost:8080/h2-console voor je database.

  Het is niet Postgres, maar ik denk dat je de weg wel vindt. Uiteraard mag je PostGres introduceren,
  maar zet dan ook een bijpassende Dockerfile op.

  (als het goed is kloppen de defaults, anders moet je de url/username even uit de application.properties vissen)

# README Edutech Product Services - NoSQL
vanwege artifact quota hit konden we niks deployen op deze repo. Er is een andere repo die alle modules deployt -> https://github.com/matthewjb02/TestVisionTest

## Matthew(document-store)(MongoDB)

### Beschrijving
Voor de examination-module heb ik de PostgreSQL-database vervangen door een MongoDB NoSQL-database. Hiervoor heb ik in MongoDB Atlas, de cloudomgeving van MongoDB, een cluster aangemaakt. In dit cluster heb ik een database opgezet met documenten voor exam en examination.

In de code heb ik onder andere het domein aangepast en de nodige configuraties doorgevoerd om de applicatie te laten werken met MongoDB. Bij het aanmaken, opvragen en bewerken van exam- en examination-objecten maakt de applicatie nu gebruik van deze MongoDB-database. Daarnaast is MongoDB opgenomen in de docker-compose.yml-file die draait op de Azure VM.

### MongoDb bekijken
Ik heb de credientals in Teams gestuurd.
<img width="610" alt="Scherm­afbeelding 2024-12-03 om 23 42 41" src="https://github.com/user-attachments/assets/0d6b55b8-de5c-4a86-91c7-059a074c2c5a">
via het icoontje kan je in intellij als het goed is verbinden met de db.

### MongoDB & Azure bug
Het is mogelijk dat de requests voor het aanmaken, opvragen en bewerken van exam- en examination-objecten niet correct werken in de cloud. Waarschijnlijk komt dit doordat de MongoDB niet goed verbind op Azure, ondanks dat ik deze correct heb geconfigureerd in docker-compose file die op de Azure VM draait

Voor de zekerheid is er een `main_local` branch aangemaakt, waarin dezelfde code staat als in de `main` branch. Het verschil is dat in deze branch alles lokaal draait. Hier zou de MongoDB zonder problemen moeten functioneren.

## Ruben Strydom graph database (neo4j) en key-value database (aerospike)
### Beschrijving
Voor de users module word een key-value database gebruikt omdat dit module te klein is voor de grotere type databases zoals cassandra of mongo.

Voor de grotere module grading is cassandra ook te groot ervoor en omdat er al twee teamleden zijn die een document-store database hebben heb ik besloten om een graph database te gebruiken. Neo4j heb ik gekozen omdat het goed te combineren is met springboot. Ook word het gebruikt door grote bedrijven waardoor je zeker bent dat de database grote aantal data en verkeer aankan.

## Werkende databases
De twee databases werken en kan doormiddel van een docker opgestart en gebruikt worden. De werkende code staat in de dev_local branch.
## Pasquinel Bhikhoe Document storage database voor exam module (Couchbase)
### Beschijving
orgineel wou ik scylla gebruiken vanwege de fijne integratie tussen modules en dit is geprobeeerd maar mislukt. daarna heb ik gekeken naar een andere database en het is couchbase geworden. De scope en Collection fucnties in de bucket zorgen voor fijne partitioning. De code functioneerd lokaal voor het grootste gedeeldte  op de branch dev local maar er moet wel na het opstarten van de docker via deze link http://localhost:8091/ui/index.html#/ een nieuwe lokale  cluster worden aangemaakt volgens de naam en wachtwoord uit de envoirement variabelen. verder heeft het de volgende structuur nodig
- exam
   - exam
     - exam
  
   - course
     - course
   - question
       - openQuestion
       - multipleChoiceQuestion
   - statitics
       - statistics
#
niveau 1 is de bucket
niveau 2 is de scope
niveau 3 is de collection

Qua deployment ben ik er iets te laat achter gekomen dat de vm betaald en vrij duur is, ik was er vanuit gegaan dat de voorbeelden uit de powerpoint niet zo verschrikkeld duur waren en toen ik keek naar deployment was het al een beetje te laat daarvoor. Voor de eindopdracht zal ik ook kijken om alles om te schrijven naar een opensource noSQL zoals apache CouchDB 
# README Deployment
## README algemeen
Er zijn 4 modules gedeployed via azure  te bereiken via deze links.

- Exam webapp - https://exam-aze2emf4etgrapew.northeurope-01.azurewebsites.net/swagger-ui/index.html#/

- Grading webapp - https://inno-testvision-grading-abaybzgufxdvh5cy.northeurope-01.azurewebsites.net/swagger-ui/index.html#/

- Examination  webapp -  https://examination-ewbtf5d0dvgpdjb2.northeurope-01.azurewebsites.net/swagger-ui/index.html#/
 
- Users web app -  https://userss-fje9bmb2b3gtdafe.northeurope-01.azurewebsites.net/swagger-ui/index.html#/ 

- er is comminucatie, alleen werkt de applicatie niet voorbij het starten van de examensessie door een probleem met de applicatie en niet met de comminucatie. Dat moeten we nog intern debuggen en fixen

Dit is de workflow die tot nu toe werkt:

# TestVision Requests

## Flow

### 1. Create Student

{
  "firstName": "string",
  "lastName": "string",
  "extraTimeRight": true,
  "email": "john.doe@student.hu.nl"
}

### 2. Create Teacher (x2)

{
  "firstName": "string",
  "lastName": "string",
  "email": "john.ojde@hu.nl"
}

### 3. Create Course

(Assumed JSON schema not provided.)

### 4. Add Course to Teacher

(Assumed JSON schema not provided.)

### 5. Create Question
{

  "points": 0,
  "question": "string",
  "type": "open"
 }

### 6. Create Exam

(Assumed JSON schema not provided.)

### 7. Add Exam to Course

(Assumed JSON schema not provided.)

### 8. Add Grading Criteria to Course

{
  "openQuestionWeight": 0.5,
  "closedQuestionWeight": 0.5
}

### 9. Add Question to Exam

(Assumed JSON schema not provided.)

### 10 accept exam

### 11. Create Examination

{
  "name": "string",
  "courseId": 1,
  "examId": 1,
  "password": "string",
  "examDate": {
    "startDate": "2024-11-11 15:12:05.208 ",
    "endDate": "2024-11-11 15:12:05.208 "
  },
  "duration": 0,
  "extraTime": 0
}
 
### 11. Add Student to Examination


### 12. Start Exam Session

{
  "examinationId": 1,
  "studentId": 1,
  "password": "string"
}

## README Matthew - Edutech Product Services - Productie Deployment
- Ik heb de virtual machine (VM) in Azure opgezet waarop RabbitMQ en de PostgreSQL-database draait voor de grading module. Hiervoor heb ik Docker en Docker-Compose geïnstalleerd op de VM en vervolgens het docker-compose.yml-bestand uitgevoerd om de services te starten.
- Ik heb een virtueel netwerk gemaakt met een subnetten. de VM en webapps draaien op dit virutele netwerk
- Ik heb per module een webapp op azure gemaakt om ze te deployen. Hiervoor is de Azure Web App gekoppeld aan de GitHub-repository van de applicatie, waarbij de main branch als standaard is ingesteld. de webapp zelf is verbonden met het virutele netwerk op een subnet. GitHub Actions-workflows zijn geconfigureerd om de applicaties te bouwen en te deployen.

## README Ruben - Edutech Product Services - Productie Deployment
- Ik heb virtueel netwerk aangemaakt en daarmee heb ik een aantal vms aangezet om de modules te deployen.
  
## README Pasquinel Edutech Product Services - Productie Deployment
- ik heb matthew geholpen met de deployment en ik heb de messaging voor de examen module gemaakt

# README Messaging

## README Matthew - Edutech Product Services - Messaging

### Modules waar messaging is toegepast door mij

- Grading
- Monoliet

### Hello World Messaging

Als eerste heb ik deze vorm van messaging toegepast tussen de `grading` en `monoliet` module, omdat andere teamleden nog
niet helemaal klaar waren met hun messaging-gedeelte. Voor beide
modules is er een RabbitMQ-package aangemaakt, waarin de benodigde bestanden staan (`config`, `consumer`, `producer`).
De `grading` module is verantwoordelijk voor het sturen en ontvangen van berichten.
Door gebruik te maken van verschillende queues met routing keys en een exchange, zoals geconfigureerd in
de `application.properties`, kunnen de `monoliet` en `grading` modules berichten naar elkaar sturen. Hiervoor zijn ook
API's gemaakt via de `RabbitController`.
#### Demo
1. Start de `grading` module.
2. Start de `monoliet` module.
3. In Postman kun je een GET-request doen naar bijvoorbeeld:
    - `http://localhost:8084/grading/publish?message=hello_world`
    - `http://localhost:8080/monoliet/publish?message=hello_world`

Hiermee stuur je berichten tussen de `monoliet` en `grading` module.

### Uitgaande messaging van `grading` naar `monoliet`
Als tweede heb ik geprobeerd de  `SubmissionService` om te bouwen zodat alles met Messaging gebeurt via produceers en consumers. Dit heb ik voor nu gedaan door uitgaande berichten naar de `monoliet` te versturen, omdat andere teamleden nog niet helemaal klaar waren met hun messaging-gedeelte. 

## README Pasquinel

Ik heb refactoring gedaan voor de Exam module en voor bijna alle dto's en een nieuwe module gemaakt voor insight, wat de
inzage van een toets is.
dit was een usecase uit de handleiding van testvision.
een docent haalt beoordeelde submisisons op en zet ze als individual insight open, dan kan de student commentaar geven,
als diegene het niet eens is met de uitslag van de toets.
Een leraar zou dit dan opnieuw moeten kunnen beoordelen, maar zover was het nog niet af.
Messaging is naar gekeken, maar heb het ivm tijdsgebrek niet kunnen implementeren. Alle foreign klassen wordt via id's
doorgegeven het doet voor nu alleen niet veel nuttigs.
de module draait op eigen poort en is via de lokale swaggerlink te bereiken.

## README Rik

Ik heb geprobeerd om messaging voor een exam toe te passen. Dit is helaas niet helemaal gelukt. Ik kreeg het niet voor
elkaar om dto's te versturen en op te halen. Wel heb ik iets geprobeerd met een string. Als je een exam ophaalt met id
print hij in de console de 2e vraag uit(via rabbit).

# README RPC:

## RPC Rik:

Ik heb de module course gemaakt. Aangezien de applicatie niet helemaal gesplitst/samengevoegd kon worden staat deze in
de
branch: [splits-exam-in-module](https://github.com/huict/hulp-24a-v3b-testvision-team4-v3b/tree/splits-exam-in-module).
Hier staan de Course, Exam, Questions etc., samen met de services en repositories. De DTO's staan in de Commons module.
Op deze branch werkt de monoliet zelf niet meer omdat de dependencies veranderd zijn.
## RPC Ruben:

Ik heb alles in de examination module gemaakt en gezet. De code staat in de examination branch.

## RPC Pasquinel:

...

# README Monoliet:

# Module uitleg

## Teacher exam validation. (Pasquinel Bhikhoe)

valt onder: Teacher,TeacherMail en validation en gedeeltes van exam,course.
Teacher exam validation is een functie wat een bestaande toets uit de wachtlijst pakt.
Aan deze toets zijn twee leraren
Deze toets wordt dan volledig getoond en dan kan de leraar,de validator, de toets accepteren of afwijzen.
Bij het accepteren wordt de toets op de goede lijst gezet en kan de exam gemaakt worden door de student.
Bij het afwijzen moet de validator een reden toevoegen en wordt de toets op een afwijslijst gezet.
De maker moet nu de toets veranderen en opnieuw inleveren, waarna de validator het weer kan beoordelen, etc.

Er is voor dit systeem gekozen omdat dit systeem logisch en effectief is, een vergelijkbaar systeem wordt hier ook op
github gebruikt met pull requests.
Er is niet te veel aandacht besteed aan login omdat dit redelijk eenvoudig is om hierna nog toe te voegen.

## Toetsen kunnen aangemaakt worden. (Rik Prins)

Met deze use case kan een docent een toets aanmaken.
Hiervoor moesten een paar dingen gebeuren:

1. Er kan een cursus aangemaakt worden
1. Er kunnen vragen gemaakt worden
1. Er kan een toets gemaakt worden
1. Er kunnen vragen aan de toets worden gekoppeld
1. De toets kan aan een cursus worden gekoppeld

De domeinklassen die hier nodig waren:
Course, Question en Test.
Hiervoor zijn ook services, repositories en controllers voor gemaakt(voor CRUD).

Todo's:
Er kunnen verschillende type vragen gemaakt worden(multiple choice, open vragen)

## Een student kan een exam starten en afmaken. (Ruben Strydom)

### Wat ik geprogrammeerd heb

- User klasse
- Student klasse
- Exam klasse
- State klasse
- StudentService klasse
- StudentController klasse
- ExamService klasse
- ExamController klasse
- Alle examen gerelateerde request en response dto's
- Unit tests van de ExamService met behulp van mocks
- Ook heb ik kleine aanpassingen gedaan aan Question en Test Klasse om ervoor te zorgen dat een vraag in een toets
  beantwoord kan worden.

### Waar ik aandacht aan heb besteed

- Zorgen dat de ExamService en ExamController tenminste de basis errors op een duidelijke manier kan terug geven met
  behulp van custom exceptions.
- Zorgen dat zoveel requests in records wordt omgezet omdat het veel minder code scheelt.
- Unit tests van de ExamService die meer verhalend zijn en mocks gebruiken.
- Ik heb ook aandacht besteed om de klasses te verdelen in folders die ik een zo goed mogelijke naam probeer te geven.
- ExamService en ExamController met zo'n min mogelijke onnodige code te programmeren

### Waar ik niet aandacht aan heb besteed

- StudentService en StudentController heb ik wat afgerafelt omdat het simpele crud is.

## Een docent kan een ingeleverde toets van een student beoordelen. (Matthew Bijlhout)

### Use case beschrijving

- een docent kan voor een inlevering per vraag punten toekennen en commentaar geven.
- een docent kan voor een inleveing een eindcijfer toekennen en commentaar geven, waardoor automatich de
  toetsstatistieken worden bijgewerkt.

## Waar is DDD toegepast?

### Entity vs Value Objects

#### Entities

- `Submission` met `SubmissionService` en `SubmissionRepository` -> als een toets wordt ingeleverd, wordt er
  een `Submission` aangemaakt. Een docent kan elke vraag apart beoordelen en van commentaar voorzien.
- `Statistics` -> Bij elke beoordeling van een exam worden de toetsstatistieken bijgewerkt.

##### Value Objects

- `GradingCriteria`(hoort bij een `Test`en heeft invloed op de `Grading`)- **DISCLIAMER**: Het value
  object `GradingCriteria` kan momenteel nog niet gebruikt worden in de applicatie en heeft dus de **MINSTE** aandacht
  gekregen. Dit komt doordat de use cases voor Test en Exam nog geen rekening houden met het onderscheid tussen open en
  gesloten vragen.
- `GradingCriteria` is Immutable, met Equals/Hashcode override

### Aggegrates

- `Statistics` is een aggregate van de aggregate root `Test`. Er is ook geen sprake van een `StatisticsRepository` omdat
  de `Statistics` alleen via de `Test` kan worden aangepast.
- `Grading` is een aggregate van de aggregate root `Submission`. Er is ook geen sprake van een `GradingRepository` omdat
  de `Grading` alleen via de `Submission` kan worden aangepast.
