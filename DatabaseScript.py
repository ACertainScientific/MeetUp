# -*- coding: utf-8 -*-
# Import required python libraries
# pancx 2019-04-10
import os
import time
import datetime
import shutil
# MySQL database details to which backup to be done. Make sure below user having enough privileges to take databases backup.

#数据库ip
DB_HOST = "cdb-.tcdb.com"
#数据库端口
DB_PORT = "10111"
#数据库用户名
DB_USER = "root"
#数据库密码
DB_USER_PASSWORD = "\'(ink^zm+36Xd=&18!\'"
#备份的数据库
DB_NAMES = "order,act,ggg"
#备份到的路径
BACKUP_PATH = "/sqlbak/"

# Getting current datetime to create seprate backup folder like "20190412".
DATETIME = time.strftime('%Y%m%d')
TODAYBACKUPPATH = BACKUP_PATH + DATETIME

# Checking if backup folder already exists or not. If not exists will create it.
if not os.path.exists(TODAYBACKUPPATH):
    os.makedirs(TODAYBACKUPPATH)

today = datetime.datetime.now()
for item in os.listdir(BACKUP_PATH):
    try:
        foldername = os.path.split(item)[1]
        day = datetime.datetime.strptime(foldername, "%Y%m%d")
        diff = today - day
        if diff.days >= 60:
            print('- - - del folder two months ago: ' + BACKUP_PATH + item)
            shutil.rmtree(BACKUP_PATH + item)
    except:
        pass

# Code for checking if you want to take single database backup or assinged multiple backups in DB_NAME.
DBS = DB_NAMES.split(",")
for DB_NAME in DBS:
    # Starting actual database backup process.
    DB_PATH = TODAYBACKUPPATH + "/" + DB_NAME + ".sql.gz"

    print("- - - baking '" + DB_NAME + ".sql'")
    dumpcmd = "mysqldump -h" + DB_HOST + " -P" + DB_PORT + " -u" + DB_USER + " -p" + DB_USER_PASSWORD + " --databases " + DB_NAME + " --set-gtid-purged=OFF |gzip > " + DB_PATH
    os.system(dumpcmd)

print("- - - Your backups has been created in '" + TODAYBACKUPPATH + "' directory")

# mysqldump: [Warning] Using a password on the command line interface can be insecure. 在命令行输入密码，就会提示这些安全警告信息（警告信息可忽略）