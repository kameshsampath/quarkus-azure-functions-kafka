package dev.kameshs.example;

import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.KafkaTrigger;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

public class GreeterFunqy {

  private Logger log = Logger.getLogger("dev.kameshs.example.greeter");

  @Incoming("func-sink")
  public void greeter(String data) {
    log.log(Level.INFO, data);
  }
}
