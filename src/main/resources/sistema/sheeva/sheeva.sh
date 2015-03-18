#!/bin/bash

VERSION=${1}
DIR_MASTER=${2}

echo "Iniciando Script..."
bash /tmp/sheeva-${VERSION}.sh ${DIR_MASTER}
echo "Script Finalizado"
