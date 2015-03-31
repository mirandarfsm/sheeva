#!/usr/bin/python

from banco import Postgres
from utils import *
import socket
import sys
import os
import json

def main():
    try:
        socketMensagem = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server_address = ('192.168.1.47', 8888)
        socketMensagem.connect(server_address)

        socketMensagem.send(bytes(json.dumps({'panel':'INICIANDO_ATUALIZACAO'})) + '\n')

        obterDadosAtualizacao()

        BD = carregarVariaveis(instancia)

        removerInstancia(catalina+'/webapps', instancia+'.war')

        BD.executarScript(banco)
        
        executarScript(arquivo=script)
        
        copiarArquivoParaWebapps(diretorio=catalina+'/webapps', aplicacao=aplicacao, listaInstancia=[instancia])

        socketMensagem.send(bytes(json.dumps({'panel':'ATUALIZADO'})) + '\n')

    except:
        socketMensagem.send(bytes(json.dumps({'panel':'FALHOU'})) + '\n')        
        raise
    finally:
        socketMensagem.send(bytes(json.dumps({'panel':'FECHANDO_CONEXAO'})) + '\n')
        socketMensagem.close()
        exit()

def obterDadosAtualizacao():
    socketDownload = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('192.168.1.47', 9999)
    socketDownload.connect(server_address)

    socketDownload.send(bytes(json.dumps({'return':'PACOTE_ATUALIZACAO'})) + '\n')
    result = json.loads(socketDownload.recv(1024).decode('UTF-8'))

    objetosJson = json.dumps(socketDownload.recv(1024).decode('UTF-8'))

    obterVariaveis(objetosJson)

    salvarArquivoNoDisco(objetosJson['ARQUIVO_APLICACAO'],nomeArquivo,tipo)
    salvarArquivoNoDisco(objetosJson['ARQUIVO_BANCO'],nomeArquivo,tipo)
    salvarArquivoNoDisco(objetosJson['ARQUIVO_SCRIPT'],nomeArquivo,tipo)
    
    socketDownload.close()


def obterVariaveis(objetosJson):
    global catalina
    catalina='/opt/tomcat/'
    global versao
    versao=objetosJson['versao']
    global instancia
    instancia=objetosJson['instancia']
    global aplicacao
    aplicacao = '/tmp/' + objetosJson['aplicacao']
    global banco
    banco = '/tmp/' + objetosJson['banco']
    global script
    script = '/tmp/' + objetosJson['script']

def carregarVariaveis(instancia):
    caminhoArquivoConfig='/var/sigadaer/' + instancia + '/config.properties'
    try:
        arquivoConfig = open(caminhoArquivoConfig)
    except:
        raise
    textoConfig = arquivoConfig.readlines()
    for linha in textoConfig:
        if linha.strip().startswith('#'):
            continue
        if linha.find("jdbc/default/username") != -1:
            usuario = linha.split('=')[1].strip()
        if linha.find("jdbc/default/password") != -1:
            senha = linha.split('=')[1].strip()
        if linha.find("jdbc/default/connectionURL") != -1:
            ip = linha.split('//')[1].split(':')[0].strip()
            database = linha.split('//')[1].split(':')[1].split('/')[1].strip()

    return Postgres(usuario,senha,ip,database)

def salvarArquivoNoDisco(objetoJsonByteArray,nome,tipo):
    try:
        arquivo = open(nome, 'wb')
        objetoJson = bytearray(objetoJsonByteArray)
        arquivo.write(objetoJson)
        arquivo.close()
        print "File Downloaded"
    except:
        raise
    finally:
        arquivo.close()


if __name__ == '__main__':
    main

