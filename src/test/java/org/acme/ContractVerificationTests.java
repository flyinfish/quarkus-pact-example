package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Provider("quarkus-pact-example")
@PactFolder("pacts")
@DisabledIfSystemProperty(named = "isNightlyEcosystemTest", matches = "true", disabledReason = "https://github.com/quarkusio/quarkus/issues/23612#issuecomment-2115318234")
public class ContractVerificationTests {

  @ConfigProperty(name = "quarkus.http.test-port")
  int quarkusPort;

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  void pactVerificationTestTemplate(PactVerificationContext context) {
    context.verifyInteraction();
  }

  @BeforeEach
  void beforeEach(PactVerificationContext context) {
    context.setTarget(new HttpTestTarget("localhost", this.quarkusPort));
  }

  @State("default")
  void setupDefaultState() {
    // nothing here
  }

}
