#!/usr/bin/python

import sys
import psycopg2 

class Banco(object):

    def __init__(self,usuario='postgres',senha='',ip='127.0.0.1',database=''):
        self.usuario = usuario
        self.senha = senha
        self.ip = ip
        self.database = database
    
    def criarConexao(self):
        pass

    def executarScript(self, script):
        try:
            conexao = self.criarConexao()
            cursor = conexao.cursor()
            cursor.execute(open(script, "r").read())
            cursor.fetchall()
        except:
            conexao.rollback()
            raise
        finally:
            cursor.close()
            conexao.close()

    def isPossuiConexao(self):
        try:
            conexao = self.criarConexao()
            cursor = conexao.cursor()
            cursor.execute("SELECT version();")
            linhaTeste = cursor.fetchall()
            print linhaTeste
            if linhaTeste > 1:
                return True
            else:
                return False
        except:
            print "Erro: ", sys.exc_info()[1]        
            return False
        finally:
            cursor.close()
            conexao.close()

class Postgres(Banco):

    def criarConexao(self):
        try:
            conexao = psycopg2.connect(host=self.ip, user=self.usuario, password=self.senha, database=self.database)
            return conexao
        except:
            raise

class MySql(Banco):

    def criarConexao(self):
        try:
            conexao = psycopg2.connect(host=self.ip, user=self.usuario, password=self.senha, database=self.database)
            return conexao
        except:
            raise

