package edu.bsu.cs;

import org.junit.jupiter.api.BeforeAll;

@SuppressWarnings("unused")
public class WebApiHandlerTest {
    @BeforeAll
    public static void reportUnusedReason() {
        System.out.println(
                """
                Since we are not to create unit tests that interact with the internet,
                we are unsure what we would test WebApiHandler.java for.
                All it really does is fetch JSON from the web and pass it on.
                
                Therefore, substituting the delivery method of the JSON
                completely undermines the responsibility of this class."""
        );
    }
}
