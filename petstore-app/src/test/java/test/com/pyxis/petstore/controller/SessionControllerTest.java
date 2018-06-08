package test.com.pyxis.petstore.controller;

import com.pyxis.petstore.controller.SessionController;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;

import static org.testinfected.hamcrest.spring.SpringMatchers.isRedirectedTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class SessionControllerTest {

    MockHttpSession mockSession = new MockHttpSession();
    SessionController controller = new SessionController();

    @Test public void
    invalidatesSessionAndRedirectsToHomePage() {
        String view = controller.delete(mockSession);
        assertTrue("session still valid", mockSession.isInvalid());
        assertThat("view", view, isRedirectedTo("/"));
    }
}