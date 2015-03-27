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
    try:
        for filename in glob(diretorio + "/*.war"):
            shutil.copy(filename, temp)
            shutil.rmtree(filename.replace('.war', ''), True)
            if os.path.exists(filename):
                os.remove(filename)
    except:
        raise

def devolverDiretorioWebapps(diretorio):
    try:
        for filename in glob(temp + "/*.war"):
            shutil.copy(filename, diretorio)
    except:
        raise

def copiarArquivoParaWebapps(diretorio, arquivo, listaInstancia):
    try:
        for instancia in listaInstancia:
            shutil.copy(arquivo, diretorio + "/" + instancia + ".war")
    except:
        raise


