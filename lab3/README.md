# docker

## Run

```bash
$ ./run.sh
```

## Stop

```bash
$ docker-compose -f app.yml down
```

---

Zapoznanie się z obsługą kontenerów w środowisku DockerCE.

3. Sprawdź stan systemu operacyjnego gospodarza i usługi Docker:
```bash
hg@hvvka ~/p/_/2/t/l/lab3> hostname                                                          master?
hvvka
hg@hvvka ~/p/_/2/t/l/lab3> uname -r                                                          master?
19.0.0
hg@hvvka ~/p/_/2/t/l/lab3> sw_vers
ProductName:	Mac OS X
ProductVersion:	10.15.1
BuildVersion:	19B88
hg@hvvka ~/p/_/2/t/l/lab3> docker --version                                                1 master?
Docker version 19.03.5, build 633a0ea
hg@hvvka ~/p/_/2/t/l/lab3> docker info                                                       master?
Client:
 Debug Mode: false

Server:
 Containers: 0
  Running: 0
  Paused: 0
  Stopped: 0
 Images: 23
 Server Version: 19.03.5
 Storage Driver: overlay2
  Backing Filesystem: extfs
  Supports d_type: true
  Native Overlay Diff: true
 Logging Driver: json-file
 Cgroup Driver: cgroupfs
 Plugins:
  Volume: local
  Network: bridge host ipvlan macvlan null overlay
  Log: awslogs fluentd gcplogs gelf journald json-file local logentries splunk syslog
 Swarm: inactive
 Runtimes: runc
 Default Runtime: runc
 Init Binary: docker-init
 containerd version: b34a5c8af56e510852c35414db4c1f4fa6172339
 runc version: 3e425f80a8c931f88e6d94a8c831b9d5aa481657
 init version: fec3683
 Security Options:
  seccomp
   Profile: default
 Kernel Version: 4.9.184-linuxkit
 Operating System: Docker Desktop
 OSType: linux
 Architecture: x86_64
 CPUs: 4
 Total Memory: 1.952GiB
 Name: docker-desktop
 ID: RXSW:BPC3:47X2:4LGS:KXDH:IA7E:7JJ5:J2MN:MAJB:DY6Y:KPR4:SR4Z
 Docker Root Dir: /var/lib/docker
 Debug Mode: true
  File Descriptors: 29
  Goroutines: 44
  System Time: 2019-11-22T12:40:52.5751228Z
  EventsListeners: 2
 HTTP Proxy: gateway.docker.internal:3128
 HTTPS Proxy: gateway.docker.internal:3129
 Registry: https://index.docker.io/v1/
 Labels:
 Experimental: false
 Insecure Registries:
  127.0.0.0/8
 Live Restore Enabled: false
 Product License: Community Engine
```

4. Uruchom kontener z systemem CentOS poleceniem:

```bash
hg@hvvka ~/p/_/2/t/l/lab3>  docker run -i -t centos /bin/bash                                master?
Unable to find image 'centos:latest' locally
latest: Pulling from library/centos
729ec3a6ada3: Pull complete
Digest: sha256:f94c1d992c193b3dc09e297ffd54d8a4f1dc946c37cbeceb26d35ce1647f88d9
Status: Downloaded newer image for centos:latest
[root@fc370680a6cf /]# hostname
fc370680a6cf
[root@fc370680a6cf /]# uname -r
4.9.184-linuxkit
[root@fc370680a6cf /]# cat /etc/os-release
NAME="CentOS Linux"
VERSION="8 (Core)"
ID="centos"
ID_LIKE="rhel fedora"
VERSION_ID="8"
PLATFORM_ID="platform:el8"
PRETTY_NAME="CentOS Linux 8 (Core)"
ANSI_COLOR="0;31"
CPE_NAME="cpe:/o:centos:centos:8"
HOME_URL="https://www.centos.org/"
BUG_REPORT_URL="https://bugs.centos.org/"

CENTOS_MANTISBT_PROJECT="CentOS-8"
CENTOS_MANTISBT_PROJECT_VERSION="8"
REDHAT_SUPPORT_PRODUCT="centos"
REDHAT_SUPPORT_PRODUCT_VERSION="8"

[root@fc370680a6cf /]# yum update
Failed to set locale, defaulting to C
CentOS-8 - AppStream                                                 136 kB/s | 6.3 MB     00:47
CentOS-8 - Base                                                      683 kB/s | 7.9 MB     00:11
CentOS-8 - Extras                                                    564  B/s | 2.1 kB     00:03
Dependencies resolved.
=====================================================================================================
 Package                  Arch           Version                             Repository         Size
=====================================================================================================
Upgrading:
[...]
Complete!
[root@fc370680a6cf /]# apt-get update
bash: apt-get: command not found
[root@fc370680a6cf /]# exit
exit
```

5. Uruchom nowy kontener zastępując opcje centos opcją ubuntu (ewentualnie debian) i powtórz wszystkie polecenia

```bash
hg@hvvka ~/p/_/2/t/l/lab3> docker run -i -t ubuntu /bin/bash                                 master?
Unable to find image 'ubuntu:latest' locally
latest: Pulling from library/ubuntu
7ddbc47eeb70: Pull complete
c1bbdc448b72: Pull complete
8c3b70e39044: Pull complete
45d437916d57: Pull complete
Digest: sha256:6e9f67fa63b0323e9a1e587fd71c561ba48a034504fb804fd26fd8800039835d
Status: Downloaded newer image for ubuntu:latest
root@d9ecb8b4c2fc:/# hostname
d9ecb8b4c2fc
root@d9ecb8b4c2fc:/# uname -r
4.9.184-linuxkit
root@d9ecb8b4c2fc:/# cat /etc/os-release
NAME="Ubuntu"
VERSION="18.04.3 LTS (Bionic Beaver)"
ID=ubuntu
ID_LIKE=debian
PRETTY_NAME="Ubuntu 18.04.3 LTS"
VERSION_ID="18.04"
HOME_URL="https://www.ubuntu.com/"
SUPPORT_URL="https://help.ubuntu.com/"
BUG_REPORT_URL="https://bugs.launchpad.net/ubuntu/"
PRIVACY_POLICY_URL="https://www.ubuntu.com/legal/terms-and-policies/privacy-policy"
VERSION_CODENAME=bionic
UBUNTU_CODENAME=bionic
root@d9ecb8b4c2fc:/# apt-get update
Get:1 http://archive.ubuntu.com/ubuntu bionic InRelease [242 kB]
[...]
Reading package lists... Done
root@d9ecb8b4c2fc:/# exit
exit
```

6. Pobierz do pamięci podręcznej obraz maszyny z serwerem apache:

```bash
hg@hvvka ~/p/_/2/t/l/lab3> docker pull bitnami/apache                                        master?
Using default tag: latest
latest: Pulling from bitnami/apache
3c9020349340: Pull complete
7e6bd745d374: Pull complete
bf4ac6c163ac: Pull complete
cc87392ca032: Pull complete
4a25cbf4894a: Pull complete
4a61a5983502: Pull complete
3548b3dfea7e: Pull complete
e520188a25d0: Pull complete
Digest: sha256:91393e9161256e40bb1f0193b7f28ec25dc1ad692d436fb5d1e665596b339f0b
Status: Downloaded newer image for bitnami/apache:latest
docker.io/bitnami/apache:latest
```

7. Uruchom nowy kontenerz pobranego obrazu:

```bash
hg@hvvka ~/p/_/2/t/l/lab3> docker run -d --name apache -p 8080:8080 bitnami/apache           master?
789eeb78ce07989d92dd978ac95e6041ee8b0c17c66115e93d0a060e6b6c5134
```

8. Otwórz na swoim komputerze stronę: http://localhost:8080. W przeglądarce powinien pojawić się napis _It works!_

_tak było_

9. W maszynie z Dockerem wyświetl listę kontenerów. Znajdź identyfikator swojego kontenera.

```bash
hg@hvvka ~/p/_/2/t/l/lab3> docker ps                                                         master?
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                              NAMES
789eeb78ce07        bitnami/apache      "/entrypoint.sh /run…"   3 minutes ago       Up 3 minutes        0.0.0.0:8080->8080/tcp, 8443/tcp   apache
```

Identyfikator: `789eeb78ce07`

10. Usuń swój kontener z serwem WWW. Sprawdź ile cyfr identyfikatora musisz wpisać, aby kontener został usunięty.

```bash
hg@hvvka ~/p/_/2/t/l/lab3> docker rm 7 -f                                                                                                                                1 master?
7
hg@hvvka ~/p/_/2/t/l/lab3> docker ps                                                                                                                                       master?
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
```

Trzeba wpisać tyle znaków, ile jest koniecznych, aby ID był jednoznaczny.

11. Uruchom ponownie kontener z dodatkową opcją:

```bash
hg@hvvka ~/p/_/2/t/l/lab3> docker run -d --name apache -p 8080:80 -v vagrant:/app bitnami/apache
b14e6735c0d9dc81d2e879f77768321df6cdc1185f781fbda1ffcb8acc38714a
```

