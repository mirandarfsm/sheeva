#!/bin/bash

instancia=$1
versao=$2
para_catalina=$3
parametro=$4 #atualizar/ novo
export PGPASSWORD=$(pegar senha do config.properties)

parar_catalina();
parar_instancia();
executar_sql();
trocar_war();
executar_bash();
iniciar_catalina();
novo();
main();
    #Se para_catalina for igual a 1, catalina stop
    #Se para_catalina for igual a 1, catalina start

