#!/usr/bin/python

from banco import Postgres
from utils import *
import socket
import sys
import os
import json

def main():
    try:
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server_address = ('192.168.1.47', 8888)
        sock.connect(server_address)

        sock.send(bytes(json.dumps({'panel':'INICIANDO_ATUALIZACAO'})) + '\n')

        sock.send(bytes(json.dumps({'properties':'VARIAVEL'})) + '\n')
        result = json.loads(sock.recv(1024).decode('UTF-8'))
           
        sock.send(bytes(json.dumps({'properties':'VARIAVEL'})) + '\n')
        result = json.loads(sock.recv(1024).decode('UTF-8'))
        catalina='/opt/tomcat/'
        versao=result['versao']
        instancia=result['instancia']
        aplicacao = '/tmp/' + result['aplicacao']
        banco = '/tmp/' + result['banco']
        script = '/tmp/' + result['script']
        BD = carregarVariaveis(instancia)
        
        tipo = {'file':'ARQUIVO_APLICACAO'}
        receberArquivo(sock,arquivo,tipo)
        
        tipo = {'file':'ARQUIVO_BANCO'}
        receberArquivo(sock,arquivo,tipo)
        
        tipo = {'file':'ARQUIVO_SCRIPT'}
        receberArquivo(sock,arquivo,tipo)

        removerInstancia(catalina+'/webapps', instancia+'.war')

        BD.executarScript(banco)
        
        executarScript(arquivo=script)
        
        copiarArquivoParaWebapps(diretorio=catalina+'/webapps', aplicacao=aplicacao, listaInstancia=[instancia])

        sock.send(bytes(json.dumps({'panel':'ATUALIZADO'})) + '\n')

    except:
        sock.send(bytes(json.dumps({'panel':'FALHOU'})) + '\n')        
        raise
    finally:
        sock.send(bytes(json.dumps({'panel':'FECHANDO_CONEXAO'})) + '\n')
        sock.close()
        exit()

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

def receberArquivo(sock,nome,tipo):
    arquivo = open(nome, 'wb')
    #f = open(data.strip(),'wb')
    #data = {'message':'hello world!', 'test':123.4}
    
    sock.send(bytes(json.dumps(tipo)) + '\n')

    data,addr = sock.recvfrom(1024)

    try:
        while(data):
            arquivo.write(data)
            sock.settimeout(2)
            data,addr = sock.recvfrom(1024)
        print "File Downloaded"
    except:
        raise
    finally:
        arquivo.close()


if __name__ == '__main__':
    main

