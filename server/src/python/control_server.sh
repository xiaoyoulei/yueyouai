
start()
{
    nohup	python server/server.py 8001 &
}

stop()
{
	line=`ps aux | grep server.py | grep -v grep`
	pid=`echo $line | awk '{print $2}' `
	echo $pid" will be killed"
	kill -9 $pid
}

case C"$1" in
Cstart)
	start
	echo "server start , port is 8001"
	;;
Cstop)
	stop
	echo "server is stop"
	;;
C*)
	echo "usage: $0 {start|stop}"
	;;

esac
