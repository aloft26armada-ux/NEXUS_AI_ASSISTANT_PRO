#!/usr/bin/env sh

# Improved shell script wrapper for Gradle

# Resolve links: $0 may be a link
PRG="$0"
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

SAVED="`pwd`"
cd "`dirname \"$PRG\"`" >/dev/null
APP_HOVER="`pwd`"
cd "$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Use the maximum available, or default to max.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/bin/java" ] ; then
        JAVACMD="$JAVA_HOME/bin/java"
    else
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum file descriptors if we can.
if [ "$OSTYPE" = "cygwin" ] || [ "$OSTYPE" = "msys" ] ; then
    :
else
    if [ "$MAX_FD" = "maximum" ] || [ "$MAX_FD" = "max" ] ; then
        MAX_FD_LIMIT=`ulimit -H -n`
        if [ $? -eq 0 ] ; then
            ulimit -n $MAX_FD_LIMIT
        fi
    fi
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched.
if [ "$OSTYPE" = "cygwin" ] ; then
    [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
fi

# Collect all arguments for the java command except the name of this script
CLK_ARGS=""
for arg do
    CLK_ARGS="$CLK_ARGS \"$arg\""
done

eval set -- "$CLK_ARGS"

# Execute Gradle
exec "$JAVACMD" \
    "-Dorg.gradle.appname=$APP_BASE_NAME" \
    -classpath "$APP_HOVER/gradle/wrapper/gradle-wrapper.jar" \
    org.gradle.wrapper.GradleWrapperMain "$@"

