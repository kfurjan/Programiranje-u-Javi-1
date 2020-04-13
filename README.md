# Programiranje-u-Javi-1

Projekt potreban za kolegij Programiranje u Javi 1

## Opis projekta

Potrebno je kreirati aplikaciju koja omogućava ažuriranje podataka o povezanim entitetima, prema želji.
Prilikom izgradnje aplikacije važno je poštovati najbolje principe objektno orijentirane paradigme i korišteći biblioteke (Class Library). Aplikacija sadrži više vrsta korisnika, pa je nužno osigurati 2 role - Administrator i Korisnik(User)

## Baza podataka

- Aplikacija podatke sprema u Microsoft SQLServer bazu podataka
- Aplikacija koristi   DDL skriptu za kreiranje svih tablica koje će biti korištene u aplikaciji
- Aplikacija pomoću skripte briše sve podatke iz tablice
- Sav rad sa bazom podataka odvija se pozivanjem procedura iz Java koda, korištenjem Repository obrasca

## Administrator

- Administrator mora biti najmanje jedan korisnik sa pripadajućim korisničko-ime/lozinka (username/password) parom koji će biti kreiran pomoću procedure i okinut nakon inicijalizacijske skripte
- Aplikaciju u ispravno stanje za korištenje od strane User-a postavlja Administrator
- Prilikom ulaska u aplikaciju, administrator ima mogućnost brisanja svih podataka iz baze (čime se brišu i sve slike sa podatkovnog sustava) i uploadanja novih podataka u bazu, pozivom RSS čitača (RSS Parser) - spomenuto predstavlja jednostavno i cjelokupno administratorsko sučelje

## Korisnik

- Ulaz u aplikaciju je restriktiran Login formom na kojoj je moguća i jednostavna registracija korisnika role User (username/password)
- Nakon ulaska u aplikaciju, User-u je predstavljena forma za pregled i promjenu entiteta (CRUD operacije), u ovome primjeru Movie

## RSS parser

- RSS parser je komponenta aplikacije koja sa određenog URL-a parsira sve podatke iz XML-a i sprema ih u bazu podataka za naknadno korištenje
- Primjer: <https://www.blitz-cinestar.hr/rss.aspx?najava=1>
- Slike je potrebno download-ati u lokalni direktorij (assets) te u bazu podataka spremiti njihove relativne putanje
- Potrebno je implementirati XML download entiteta korištenjem JAXB biblioteke

## Forme i navigacija

- Forme unutar aplikacije moraju biti kvalitetno organizirane (primjer: JTabbedPane, prilikom čega svaki JPanel predstavlja vlastita klasa prema principu jedne odgovornosti - Single-responsibility principle)
- Potrebno je kreirati dodatne entitete (primjer: Actor, Director) koji također imaju CRUD forme za njihovo ažuriranje
- Postojeći entitet potrebno je ažurirati na način da se može povezati sa novim entitetima (primjer: Movie može biti povezan sa više Actor, Director entiteta)
- Prikom promjene entiteta ili njegovog brisanja, potrebno je osigurati dosljednu promjenu / brisanje pripadajućih slika iz direktorija sa slikama (primjer: assets)
- Za prikaz liste entiteta potrebno je koristiti u najvećoj mjeri JTable i pripadajuće AbstractTableModel modele
- Za navigaciju u aplikaciji potrebno je koristiti JMenu
- Potrebno je implementirati Drag and drop funkcionalnost na vlastito odabranom primjeru u aplikaciji (primjer: dodavanje Actora na Movie)
