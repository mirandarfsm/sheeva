#!/usr/bin/python

import sys
import psycopg2 

def carregarVariaveis(om):
    caminhoArquivoConfig='/var/sigadaer/sigad-' + om + '/config.properties'
    try:
        arquivoConfig = open(caminhoArquivoConfig)
    except:
        raise
    textoConfig = arquivoConfig.readlines()
    for linha in textoConfig:
        if linha.find('#') != -1:
            continue
        if linha.find("jdbc/default/username") != -1:
            global usuarioDB
            usuarioDB = linha.split('=')[1].strip()
        if linha.find("jdbc/default/password") != -1:
            global senhaDB
            senhaDB = linha.split('=')[1].strip()
        if linha.find("jdbc/default/connectionURL") != -1:
            global ipDB
            ipDB = linha.split('//')[1].split(':')[0].strip()
            global nomeDB
            nomeDB = linha.split('//')[1].split(':')[1].split('/')[1].strip()

def criarConexao(om):
    carregarVariaveis(om)
    try:
        conexao = psycopg2.connect(host=ipDB, user=usuarioDB, password=senhaDB, database=nomeDB)
        return conexao
    except:
        raise

def execucarScript(om, script):
    try:
        conexao = criarConexao(om)
        cursor = conexao.cursor()
        cursor.execute(open(script, "r").read())
        cursor.fetchall()
        cursor.close()
        conexao.close()
    except:
        conexao.rollback()
        raise
    finally:
        cursor.close()
        conexao.close()

def isPossuiConexao(om):
    try:
        conexao = criarConexao(om)
        cursor = conexao.cursor()
        cursor.execute("select * from usuario limit 1;")
        linhaTeste = cursor.fetchall()
        print linhaTeste
        if linhaTeste > 1:
            return True
        else:
            return False
        cursor.close()
        conexao.close()
    except:
        print "Erro: ", sys.exc_info()[1]        
        return False




