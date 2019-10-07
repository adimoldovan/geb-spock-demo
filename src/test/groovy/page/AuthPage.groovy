package page

import geb.Page

class AuthPage extends Page {
    static url = 'stubs/auth.html'
    static at = {
        headerText == "Authentication"
    }

    static content = {
        headerText { $('h1').text() }
        inputUsername { $('#input-login-username') }
        inputPassword { $('#input-login-password') }
        btnSubmitLogin { $('#login-submit') }
        usernameErrorText { $('#input-login-username + div').text() }
        passwordErrorText { $('#input-login-password + div').text() }
        generalErrorText { $('#login-error').text() }
    }

    def login(String username, String password) {
        inputUsername.value(username)
        inputPassword.value(password)
        btnSubmitLogin.click()
    }
}
