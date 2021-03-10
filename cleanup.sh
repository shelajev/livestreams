#!/bin/sh

set -x

NAME=`date +'%Y%m%d'`
git checkout -b $NAME
git commit -am "Storing everything to a branch: $NAME"
git push -u origin $NAME
git checkout main


