JAVAC = javac
JAVA = java
CFLAGS = -g -Xlint:unchecked

# Path configurations
SRC_DIR = .
TEST_DIR = ../tests
BIN_DIR = ../bin/classes
TEST_BIN_DIR = ../bin/test-classes
MAIN_CLASS = Main
TEST_RES_DIR = $(TEST_DIR)/res

# JUnit paths (single standalone JAR)
JUNIT_CP = /usr/share/java/junit-platform-console-standalone.jar

# File discovery
MAIN_SOURCES = $(shell find $(SRC_DIR) -name '*.java')
TEST_SOURCES = $(shell find $(TEST_DIR) -name '*.java')

.PHONY: all compile test-compile test run clean clean-test-res

all: compile

compile:
	@mkdir -p $(BIN_DIR)
	$(JAVAC) $(CFLAGS) -d $(BIN_DIR) $(MAIN_SOURCES)

test-compile: compile
	@mkdir -p $(TEST_BIN_DIR)
	$(JAVAC) $(CFLAGS) -d $(TEST_BIN_DIR) \
	-cp "$(BIN_DIR):$(JUNIT_CP)" \
	$(TEST_SOURCES)

test: test-compile
	$(JAVA) -cp "$(BIN_DIR):$(TEST_BIN_DIR):$(JUNIT_CP)" \
	org.junit.platform.console.ConsoleLauncher \
	--scan-class-path \
	--class-path "$(BIN_DIR):$(TEST_BIN_DIR)"
	@$(MAKE) clean-test-res

run: compile
	$(JAVA) -cp $(BIN_DIR) $(MAIN_CLASS)

clean:
	rm -rf $(BIN_DIR) $(TEST_BIN_DIR)

clean-test-res:
	rm -rf $(TEST_RES_DIR)/*
