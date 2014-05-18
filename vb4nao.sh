#!/bin/bash

RUNNER_SCRIPT_DIR=$(cd ${0%/*} && pwd)

cd $RUNNER_SCRIPT_DIR

CP=`ls lib | sed -e 's|^|lib/|' | tr '\n' ':'`

java -cp $CP pl.edu.agh.toik.vb4nao.Test