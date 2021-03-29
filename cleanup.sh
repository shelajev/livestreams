#!/bin/sh

set -x

NAME=`date +'%Y%m%d'`
git checkout -b $NAME
git commit -am "Storing everything to a branch: $NAME"
git push -u origin $NAME
git checkout main
#cleanup the main bran
shopt -s extglob
rm -rf -v !("LICENSE"|"README.md"|"cleanup.sh")
shopt -u extglob
git add .
git commit -am "cleanup"
git push

