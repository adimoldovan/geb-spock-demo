package spec

import page.AuthPage
import page.WelcomePage

class LoginSpec extends BaseSpec {

    def setup() {
        to AuthPage
    }

    def "Successful login with user #username"(String username, String password) {
        when: "attempting login with valid credentials"
            login(username, password)

        then: "welcome page is displayed"
            at WelcomePage

        and: "user is logged in"
            isLoggedIn(username)

        where:
            username | password
            'camel'  | 'camelpassword'
    }

    def "Unsuccessful login: #description"(String description, String username, String password, String expectedUserError, String expectedPasswordError, String expectedGeneralError) {
        when: "attempting login with invalid credentials"
            login(username, password)

        then: "welcome page is displayed"
            at AuthPage

        and: "expected errors are displayed"
            usernameErrorText == expectedUserError
            passwordErrorText == expectedPasswordError
            generalErrorText == expectedGeneralError

        where:
            description             | username   | password   | expectedUserError            | expectedPasswordError        | expectedGeneralError
            'missing user and pass' | ''         | ''         | 'Please enter your username' | 'Please enter your password' | ''
            'missing password'      | 'camel'    | ''         | ''                           | 'Please enter your password' | ''
            'missing username'      | ''         | 'somepass' | 'Please enter your username' | ''                           | ''
            'wrong user and pass'   | 'someuser' | 'somepass' | ''                           | ''                           | 'Invalid username or password!'
            'wrong password'        | 'camel'    | 'somepass' | ''                           | ''                           | 'Invalid username or password!'
    }
}
