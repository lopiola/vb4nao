all:
	mvn clean install
	mkdir -p release/lib
	cp lib/*.* release/lib/
	cp target/*.jar release/lib/
	cp vb4nao.sh release/

