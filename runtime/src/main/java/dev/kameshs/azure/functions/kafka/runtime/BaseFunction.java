package dev.kameshs.azure.functions.kafka.runtime;

import io.quarkus.runtime.Application;
import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseFunction {

  protected static String deploymentStatus;
  protected static boolean started = false;
  protected static boolean bootstrapError = false;

  private static final int BUFFER_SIZE = 8096;

  protected static void initQuarkus() {
    StringWriter error = new StringWriter();
    PrintWriter errorWriter = new PrintWriter(error, true);
    if (Application.currentApplication()
        == null) { // were we already bootstrapped?  Needed for mock azure unit testing.
      try {
        Class<?> appClass = Class.forName("io.quarkus.runner.ApplicationImpl");
        String[] args = {};
        Application app = (Application) appClass.newInstance();
        app.start(args);
        errorWriter.println("Quarkus bootstrapped successfully.");
        started = true;
      } catch (Throwable ex) {
        bootstrapError = true;
        errorWriter.println("Quarkus bootstrap failed.");
        ex.printStackTrace(errorWriter);
      }
    } else {
      errorWriter.println("Quarkus bootstrapped successfully.");
      started = true;
    }
    deploymentStatus = error.toString();
  }
}
