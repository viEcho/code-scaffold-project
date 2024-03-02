#!/bin/bash
RED_COLOR='\033[1;31m'
GREEN_COLOR='\033[1;32m'
#添加执行权限
chmod +x /Users/echo/Documents/sCloud/code-scaffold-project/redis-server-sample/src/main/resources/shell/redis-start.sh
cd ~
cd /usr/local/bin
redis-server
#  使用read命令达到类似bat中的pause命令效果
#echo 按任意键继续
#read -n 1
#echo 继续运行