package page

import geb.Page

class WelcomePage extends Page {
    static at = { greetingText.startsWith("Welcome") }

    static content = {
        greetingText { $('h1#greeting').text() }
        btnLogout { $('#logout-submit') }
    }

    def isLoggedIn(String username) {
        greetingText == "Welcome $username"
        btnLogout.isDisplayed()
    }
}
