# Wykorzystanie aplikacji Vagrant do przygotowania serwera aplikacyjnego

[Vagrant - Documentation](https://www.vagrantup.com/docs/index.html)

## Wprowadzenie (opcjonalne)

1. Instalacja aplikacji [VirtualBox](https://www.virtualbox.org/wiki/Linux_Downloads)

1. Instalacja aplikacji [Vagrant](pakiet ze strony vagrantup.com)

1. Dodanie wtyczek Vagranta: instalacja [dodatków maszyny wirtualnej](https://github.com/dotless-de/vagrant-vbguest)

   ```bash
   $ vagrant plugin install vagrant-vbguest
   ```

## Uruchomienie maszyny w środowisku Vagrant/VirtualBox

1. Utworzenie projektu Vagrant: `vagrant init`

1. Konfiguracja serwera przy pomocy pliku Vagrantfile:

   1. Instalacja systemu: [hashicorp/precise64](https://app.vagrantup.com/hashicorp/boxes/precise64)

      ```bash
      $ vagrant init hashicorp/precise64
      ```

   1. Instalacja dodatków maszyny wirtualnej (wtyczka vb-guest)

      ```bash
      $ vagrant plugin install vagrant-vbguest
      ```

   1. Instalacja serwera WWW: `sudo apt-get update -y; sudo apt-get install apache2 -y`

   1. Skopiowanie przykładowej strony do serwera: `copy /vagrant/...   /var/www/html`

   1. Konfiguracja portów maszyny wirtualnej.

   1. Uruchomienie i testowanie serwera.

1. Konfiguracja serwera przy pomocy pliku Vagrant file z wydzielonym skryptem Bash i kompletną aplikacją webową (np. node.js)
