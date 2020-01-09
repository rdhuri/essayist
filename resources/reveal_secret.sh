#!/usr/bin/env bash

PRIVATE_KEY=$1
GIT_SECRET_PASSPHRASE=$2

sudo apt-get install apt-transport-https
echo "deb https://dl.bintray.com/sobolevn/deb git-secret main" | sudo tee -a /etc/apt/sources.list
sudo wget -qO - https://api.bintray.com/users/sobolevn/keys/gpg/public.key | sudo apt-key add -
sudo apt-get update
sudo apt-get install -y git-secret
sudo echo `which gpg`
echo "private key ${PRIVATE_KEY}"
#echo -e ${PRIVATE_KEY} > private_key.asc
echo ${PRIVATE_KEY} | base64 --decode > private_key.asc
gpg --import --no-tty --batch --yes private_key.asc
echo "Git secret passphrase ${GIT_SECRET_PASSPHRASE}"
git secret reveal -p ${GIT_SECRET_PASSPHRASE}