# jenkins

1. Pobrano plik Vagrantfile do katalogu `/home/zosia/devops/vagrant/jenkins`. Dostosowano liczbę procesorów:
  ```bash
  config.vm.provider "virtualbox" do |vb|
     vb.memory = "8192"
     vb.cpus = 4
  end
  ```
2. Po uruchomieniu maszyny otworzono w przeglądarce stronę [http://localhost:8080/](http://localhost:8080/). 
Aktywowano Jenkinsa wpisując hasło odczytane z konsoli i założono swoje konto.

3. Doinstalowano do Jenkinsa sugerowane pakiety oraz `Blue Ocean` (dla lepszego wyglądu) i `Docker`
do obsługi kontenerów z poziomu Jenkinsa. Nie instaowano `Locale`.

4. W katalogu `/home/zosia/devops/vagrant/`, obok pliku Vagrantfile, utworzono lokalne repozytorium git:
```bash
$ cd /vagrant
$ mkdir repo.git
$ cd repo.git
$ git init --bare --shared
```
Jest to repozytorium typu bare tzn. nie ma obszaru roboczego i służy tylko do
przechowywania kodu. Wszystko co w zwykłym repozytorium jest w katalogu `.git`, 
tutaj znajduje się bezpośrednio w folderze repozytorium (tutaj: `/home/zosia/devops/vagrant/repo.git`).

5. Skopiowano załączony plik `post-receive` do katalogu `repo.git/hooks`. 
Zawarty wewnątrz kod ma za zadanie uruchomić zadanie Jenkinsa po każdej modyfikacji
repozytorium. 
W pliku `Vagrantfile` podmontowano folder nadrzędny do folderu `jenkins`,
ponieważ repozytorium `repo.git` zostało utworzone w systemie hosta:
```yaml
  config.vm.synced_folder "../", "/mnt/"
```

Skonfigurowano repozytorium git z naszą aplikacją tak, aby 
umożliwiało wysyłanie kodu do przygotowanego przed chwilą repozytorium:
```bash
$ git remote add jenkins /home/zosia/devops/myapp
$ git remote -vv
$ git push jenkins master
```

6. Utwórzono w Jenkinsie nowe zadanie budujące naszą aplikację. 
W zadaniu skonfigurowano:
- Repozytorium kodu: ustawiono `git` i podano ścieżkę do repozytorium `repo.git`.
Ponieważ Jenkins działa w systemie Linux, podano ścieżkę w postaci: 
`file:///mnt/repo.git`
- Wyzwalacze zadania: ustawiono "Wyzwalaj zdalnie przez skrypt" 
i podano token wyzwalacza.
- Budowanie: skonfigurowano narzędzie budujące aplikację. Wykorzystano skrypt uruchaniający
Maven Wrapper, który uruchamia kompilację projektu, testy oraz budowanie pliku wykonywalnego aplikacji:
```bash
./mvnw -Pprod verify
```

7. W pliku `post-receive` zmodyfikowano parametry tak aby odpowiadały 
naszej konfiguracji: użytkownik, hasło, nazwa zadania, token zadania.

8. W repozytorium roboczym wprowadzono niewielkie zmiany, wykonano operację
`git commit`, a następnie `git push jenkins master`. Zadanie na Jenkinsie 
uruchomiło się automatycznie. 
