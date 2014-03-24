cpth=/Users/claus/Bin/IMDB/imdb.jar
cpth=$cpth:/Users/claus/Bin/IMDB/icons.jar
cpth=$cpth:/Users/claus/Bin/IMDB/commons-lang-2.5.jar
cpth=$cpth:/Users/claus/Bin/IMDB/httpclient-4.1.1.jar
cpth=$cpth:/Users/claus/Bin/IMDB/commons-logging-1.1.1.jar
cpth=$cpth:/Users/claus/Bin/IMDB/httpcore-4.1.jar
cpth=$cpth:/Users/claus/Bin/IMDB/httpcore-nio-4.1.jar
cpth=$cpth:/Users/claus/Bin/IMDB/httpmime-4.1.1.jar

java -classpath $cpth MainStandAlone $*
