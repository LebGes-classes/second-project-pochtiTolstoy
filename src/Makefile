JAVAC = javac
CFLAGS = -g
SRC_DIR = .
BIN_DIR = ../build

SRCS = $(shell find $(SRC_DIR) -name '*.java')
CLASS = $(patsubst $(SRC_DIR)/%.java, $(BIN_DIR)/%.class, $(SRCS))

all: $(CLASS)

$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	@mkdir -p $(BIN_DIR)
	$(JAVAC) $(CFLAGS) -d $(BIN_DIR) $<

clean:
	rm -rf $(BIN_DIR)

run: all
	java -cp $(BIN_DIR) Main

debug: all
	jdb -sourcepath . -classpath ../build Main

debug_ini: all
	cp ../debug/jdb.ini .
	jdb -sourcepath . -classpath ../build Main
	rm jdb.ini
