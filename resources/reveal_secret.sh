#!/usr/bin/env bash

PRIVATE_KEY=$1
GIT_SECRET_PASSPHRASE=$2

sudo chmod +x ./gradlew
sudo sh -c "echo 'deb https://dl.bintray.com/sobolevn/deb git-secret main' | tee -a /etc/apt/sources.list"
sudo wget -qO - https://api.bintray.com/users/sobolevn/keys/gpg/public.key | sudo apt-key add -
sudo apt-get update && sudo apt-get install -y gawk git-secret
sudo echo `which gpg`
echo ${PRIVATE_KEY} | base64 --decode > private_key
gpg --import --no-tty --batch --yes private_key

git secret reveal -p ${GIT_SECRET_PASSPHRASE}