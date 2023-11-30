module io.questdb.benchmarks {
    requires io.questdb;
    requires jmh.core;
    requires org.apache.logging.log4j;
    requires simpleclient;
    requires simpleclient.common;
    requires java.desktop;

    exports org.questdb.jmh_generated;
}
