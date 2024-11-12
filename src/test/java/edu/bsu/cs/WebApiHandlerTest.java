package edu.bsu.cs;

@SuppressWarnings("unused")
public class WebApiHandlerTest {
    public void reportUnusedReason() {
        System.out.println(
                """
                Since we are not to create unit tests that interact with the internet,
                we have no idea what we would test WebApiHandler.java for.
                All it really does is fetch JSON from the web and pass it on.
                
                Therefore, substituting the delivery method of the JSON
                completely undermines the responsibility of this class."""
        );
    }
}
