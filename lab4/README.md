# kubernetes

## Wymagania wstępne

- stan repozytorium z laboratorium dotyczącym Dockera

- minikube  
  [minikube installation](https://minikube.sigs.k8s.io/docs/start/)

- docker

- kubectl

## Run

1. minicube delete (ewentualnie) & start z odpowiednią ilością CPUs i pamięci
```bash
$ minikube delete
🔥  Deleting "minikube" in virtualbox ...
💔  The "minikube" cluster has been deleted.
🔥  Successfully deleted profile "minikube"
$ minikube start --cpus 4 --memory 8192
😄  minikube v1.5.2 on Ubuntu 18.04
✨  Automatically selected the 'virtualbox' driver (alternates: [none])
🔥  Creating virtualbox VM (CPUs=4, Memory=8192MB, Disk=20000MB) ...
🐳  Preparing Kubernetes v1.16.2 on Docker '18.09.9' ...
🚜  Pulling images ...
🚀  Launching Kubernetes ... 
⌛  Waiting for: apiserver
🏄  Done! kubectl is now configured to use "minikube"
```

2. kubectl apply

```bash
$ kubectl apply -f app-deployment.yaml,app-service.yaml,mysql-claim0-persistentvolumeclaim.yaml,mysql-deployment.yaml,mysql-service.yaml
deployment.apps/app configured
service/app configured
persistentvolumeclaim/mysql-claim0 configured
deployment.apps/mysql configured
service/mysql configured

```

3. kubectl get deployments --watch  
Opcja `watch` pozwala na obserwowanie dostępności deploymentów.
```bash
$ kubectl get deployments --watch
NAME    READY   UP-TO-DATE   AVAILABLE   AGE
app     0/1     1            0           4s
mysql   0/1     1            0           4s
app     1/1     1            1           15s
mysql   1/1     1            1           36s
```
3. kubectl get pods

```bash
$ kubectl get pods
NAME                     READY   STATUS    RESTARTS   AGE
app-c969b97f4-r2zwx      1/1     Running   0          96s
mysql-6d7b4bdc95-cjjgp   1/1     Running   0          96s
```

4. Podglądanie logów

Logi z aplikacji:
```bash
$ kubectl logs app-c969b97f4-r2zwx -f
The application will start in 30s...

        ██╗ ██╗   ██╗ ████████╗ ███████╗   ██████╗ ████████╗ ████████╗ ███████╗
        ██║ ██║   ██║ ╚══██╔══╝ ██╔═══██╗ ██╔════╝ ╚══██╔══╝ ██╔═════╝ ██╔═══██╗
        ██║ ████████║    ██║    ███████╔╝ ╚█████╗     ██║    ██████╗   ███████╔╝
  ██╗   ██║ ██╔═══██║    ██║    ██╔════╝   ╚═══██╗    ██║    ██╔═══╝   ██╔══██║
  ╚██████╔╝ ██║   ██║ ████████╗ ██║       ██████╔╝    ██║    ████████╗ ██║  ╚██╗
   ╚═════╝  ╚═╝   ╚═╝ ╚═══════╝ ╚═╝       ╚═════╝     ╚═╝    ╚═══════╝ ╚═╝   ╚═╝

:: JHipster 🤓  :: Running Spring Boot 2.1.9.RELEASE ::
:: https://www.jhipster.tech ::

2019-12-06 02:01:24.121  INFO 1 --- [           main] i.g.j.s.JhipsterSampleApplicationApp     : Starting JhipsterSampleApplicationApp on app-c969b97f4-r2zwx with PID 1 (/app.jar started by root in /)
2019-12-06 02:01:24.126  INFO 1 --- [           main] i.g.j.s.JhipsterSampleApplicationApp     : The following profiles are active: prod,swagger
2019-12-06 02:01:35.880  WARN 1 --- [           main] i.g.j.c.liquibase.AsyncSpringLiquibase   : Warning, Liquibase took more than 5 seconds to start up!
2019-12-06 02:01:42.187  INFO 1 --- [           main] i.g.j.sample.config.WebConfigurer        : Web application configuration, using profiles: prod
2019-12-06 02:01:42.189  INFO 1 --- [           main] i.g.j.sample.config.WebConfigurer        : Web application fully configured
2019-12-06 02:01:49.117  INFO 1 --- [           main] i.g.j.s.JhipsterSampleApplicationApp     : Started JhipsterSampleApplicationApp in 26.852 seconds (JVM running for 28.524)
2019-12-06 02:01:49.128  INFO 1 --- [           main] i.g.j.s.JhipsterSampleApplicationApp     : 
----------------------------------------------------------
	Application 'jhipsterSampleApplication' is running! Access URLs:
	Local: 		http://localhost:8080/
	External: 	http://172.17.0.6:8080/
	Profile(s): 	[prod, swagger]
----------------------------------------------------------
```

Logi z bazy:
```bash
$ kubectl logs mysql-6d7b4bdc95-cjjgp -f 
Initializing database
2019-12-06T02:01:11.066935Z 0 [Warning] [MY-011070] [Server] 'Disabling symbolic links using --skip-symbolic-links (or equivalent) is the default. Consider not using this option as it' is deprecated and will be removed in a future release.
2019-12-06T02:01:11.067050Z 0 [System] [MY-013169] [Server] /usr/sbin/mysqld (mysqld 8.0.17) initializing of server in progress as process 30
2019-12-06T02:01:12.968406Z 5 [Warning] [MY-010453] [Server] root@localhost is created with an empty password ! Please consider switching off the --initialize-insecure option.
2019-12-06T02:01:14.733585Z 0 [System] [MY-013170] [Server] /usr/sbin/mysqld (mysqld 8.0.17) initializing of server has completed
Database initialized
Initializing certificates
Generating a RSA private key
..........+++++
..................+++++
unable to write 'random state'
writing new private key to 'ca-key.pem'
-----
Generating a RSA private key
.................+++++
.......+++++
unable to write 'random state'
writing new private key to 'server-key.pem'
-----
Generating a RSA private key
............................+++++
.........................................+++++
unable to write 'random state'
writing new private key to 'client-key.pem'
-----
Certificates initialized
MySQL init process in progress...
MySQL init process in progress...
2019-12-06T02:01:16.477825Z 0 [Warning] [MY-011070] [Server] 'Disabling symbolic links using --skip-symbolic-links (or equivalent) is the default. Consider not using this option as it' is deprecated and will be removed in a future release.
2019-12-06T02:01:16.478138Z 0 [System] [MY-010116] [Server] /usr/sbin/mysqld (mysqld 8.0.17) starting as process 102
2019-12-06T02:01:17.103118Z 0 [Warning] [MY-011810] [Server] Insecure configuration for --pid-file: Location '/var/run/mysqld' in the path is accessible to all OS users. Consider choosing a different directory.
2019-12-06T02:01:17.138310Z 0 [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.17'  socket: '/var/run/mysqld/mysqld.sock'  port: 0  MySQL Community Server - GPL.
2019-12-06T02:01:17.165190Z 0 [Warning] [MY-011302] [Server] Plugin mysqlx reported: 'Failed at SSL configuration: "SSL context is not usable without certificate and private key"'
2019-12-06T02:01:17.166661Z 0 [System] [MY-011323] [Server] X Plugin ready for connections. Socket: '/var/run/mysqld/mysqlx.sock'
Warning: Unable to load '/usr/share/zoneinfo/iso3166.tab' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/leap-seconds.list' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/zone.tab' as time zone. Skipping it.
Warning: Unable to load '/usr/share/zoneinfo/zone1970.tab' as time zone. Skipping it.

2019-12-06T02:01:25.147231Z 0 [System] [MY-010910] [Server] /usr/sbin/mysqld: Shutdown complete (mysqld 8.0.17)  MySQL Community Server - GPL.

MySQL init process done. Ready for start up.

2019-12-06T02:01:25.550148Z 0 [Warning] [MY-011070] [Server] 'Disabling symbolic links using --skip-symbolic-links (or equivalent) is the default. Consider not using this option as it' is deprecated and will be removed in a future release.
2019-12-06T02:01:25.550283Z 0 [System] [MY-010116] [Server] /usr/sbin/mysqld (mysqld 8.0.17) starting as process 1
2019-12-06T02:01:26.061266Z 0 [Warning] [MY-011810] [Server] Insecure configuration for --pid-file: Location '/var/run/mysqld' in the path is accessible to all OS users. Consider choosing a different directory.
2019-12-06T02:01:26.095954Z 0 [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.17'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.
2019-12-06T02:01:26.208802Z 0 [Warning] [MY-011302] [Server] Plugin mysqlx reported: 'Failed at SSL configuration: "SSL context is not usable without certificate and private key"'
2019-12-06T02:01:26.210736Z 0 [System] [MY-011323] [Server] X Plugin ready for connections. Socket: '/var/run/mysqld/mysqlx.sock' bind-address: '::' port: 33060
mys
```

5. Sprawdzanie IP minikube
```bash
$ minikube ip
192.168.99.104
```

6. Sprawdzanie IP aplikacji  
Widać, że aplikacja nie ma zewnętrznego portu
```bash
$ kubectl get svc
NAME         TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)    AGE
app          ClusterIP   10.101.188.164   <none>        8080/TCP   6m27s
kubernetes   ClusterIP   10.96.0.1        <none>        443/TCP    8m9s
mysql        ClusterIP   10.100.203.167   <none>        3306/TCP   6m27s
```

7. Wystawienie adresu aplikacji `app`
```bash
$ kubectl expose deployment app --type=NodePort --name app-exposed
service/app-exposed exposed
```

8. Sprawdzenie IP wystawionej aplikacji
```bash
$ kubectl get svc
NAME          TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
app           ClusterIP   10.101.188.164   <none>        8080/TCP         8m54s
app-exposed   NodePort    10.98.72.64      <none>        8080:31800/TCP   38s
kubernetes    ClusterIP   10.96.0.1        <none>        443/TCP          10m
mysql         ClusterIP   10.100.203.167   <none>        3306/TCP         8m54s
```

9. Adres aplikacji  
Adres aplikacji jest złożeniem IP minikube i portu `app-exposed`:
http://192.168.99.104:31800/


## Stop

```bash
$ minikube stop
```

---

Zadanie:  
Uruchom swoją aplikację (testowaną wcześniej w ́środowisku Docker) w środowisku minikube. Wykorzystaj przygotowane wcześniej kontenery.

Wykonane kroki:

1. Instalacja [Kompose](https://github.com/kubernetes/kompose)  
Kompose to narzędzie tłumaczące pliki Docker Compose na zasoby Kubernetesa.

```bash
# Linux
curl -L https://github.com/kubernetes/kompose/releases/download/v1.19.0/kompose-linux-amd64 -o kompose

# macOS
curl -L https://github.com/kubernetes/kompose/releases/download/v1.19.0/kompose-darwin-amd64 -o kompose

chmod +x kompose
sudo mv ./kompose /usr/local/bin/kompose
```

2. Konwersja głównego pliku app.yml

```bash
$ kompose convert -f app.yml
WARN Volume mount on the host "~/volumes/jhipster/jhipsterSampleApplication/mysql/" isn't supported - ignoring path on the host 
INFO Kubernetes file "app-service.yaml" created   
INFO Kubernetes file "mysql-service.yaml" created 
INFO Kubernetes file "app-deployment.yaml" created 
INFO Kubernetes file "mysql-deployment.yaml" created 
INFO Kubernetes file "mysql-claim0-persistentvolumeclaim.yaml" created 
```

3. Edycja plików app-deployment.yaml i mysql-deployment.yaml  
Pliki należy edytować, ponieważ narzędzie Kompose do konwersji konwertuje do wersji API, która nie jest wspierana przez kubectl:

`app-deployment.yaml`  
Zamiana:  
```yaml
apiVersion: extensions/v1beta1 -> apiVersion: apps/v1 
``` 
Dodanie do `spec`: 
```yaml 
spec:
  selector:
    matchLabels:
      io.kompose.service: app
```

`mysql-deployment.yaml`  
Zamiana:  
```yaml
apiVersion: extensions/v1beta1 -> apiVersion: apps/v1 
``` 
Dodanie do `spec`: 
```yaml 
spec:
  selector:
    matchLabels:
      io.kompose.service: mysql
```

