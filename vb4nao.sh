#!/bin/bash

RUNNER_SCRIPT_DIR=$(cd ${0%/*} && pwd)

cd $RUNNER_SCRIPT_DIR

CP=`ls lib | grep jar | sed -e 's|^|lib/|' | tr '\n' ';'`

java -Djava.library.path=lib/ -cp $CP pl.edu.agh.toik.vb4nao.Application