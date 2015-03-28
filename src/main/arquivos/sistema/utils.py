#!/usr/bin/python
import time
from glob import glob
import os
import sys
import shutil

temp="/tmp/"

def follow(thefile):
    thefile.seek(0,2)
    while True:
        line = thefile.readline()
        if not line:
            time.sleep(0.1)
            continue
        yield line

def observarLog(arquivo):
    logfile = open(arquivo,"r")
    loglines = follow(logfile)
    for line in loglines:
        print line,

def limparDiretorioWebapps(diretorio):
    for filename in glob(diretorio + "/*.war"):
        removerInstancia(diretorio,filename)    

def removerInstancia(diretorio, aplicacao):
    try:
        shutil.copy(aplicacao, temp)
        shutil.rmtree(aplicacao.replace('.war', ''), True)
        if os.path.exists(aplicacao):
            os.remove(aplicacao)
    except:
        raise

def devolverInstanciaWebapps(aplicacao,diretorio):
    try:
        shutil.copy(temp + "/" + filename, diretorio)
    except:
        raise

def devolverDiretorioWebapps(diretorio):
    for filename in glob(temp + "/*.war"):
        devolverInstanciaWebapps(filename, diretorio)

def copiarArquivoParaWebapps(diretorio, arquivo, listaInstancia):
    try:
        for instancia in listaInstancia:
            shutil.copy(arquivo, diretorio + "/" + instancia + ".war")
    except:
        raise

def executarScript(diretorio='',arquivo):
    try:
        os.system("chmod +x " + diretorio + arquivo)
        os.system('./' + diretorio + arquivo)
    except:
        raise
