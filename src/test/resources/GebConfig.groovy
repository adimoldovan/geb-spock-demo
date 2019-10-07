import com.aoe.gebspockreports.GebReportingListener
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.safari.SafariDriver

static ChromeOptions chromeOptions() {
    ChromeOptions cOpt = new ChromeOptions()
    return cOpt
}

driver = {
    WebDriverManager.chromedriver().setup()
    new ChromeDriver(chromeOptions())
}

environments {
    grid {
        driver = {
            DesiredCapabilities cap = new DesiredCapabilities()
            cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions())
            cap.setBrowserName("chrome")
            new RemoteWebDriver(cap)
        }
    }

    chromeHeadless {
        driver = {
            WebDriverManager.chromedriver().setup()
            new ChromeDriver(chromeOptions().setHeadless(true))
        }
    }

    firefox {
        driver = {
            WebDriverManager.firefoxdriver().setup()
            new FirefoxDriver()
        }
    }

    firefoxHeadless {
        driver = {
            WebDriverManager.firefoxdriver().setup()
            FirefoxOptions options = new FirefoxOptions()
            options.setHeadless(true)
            new FirefoxDriver()
        }
    }

    safari {
        driver = {
            new SafariDriver()
        }
    }

    edge {
        driver = {
            WebDriverManager.edgedriver().setup()
            new EdgeDriver()
        }
    }
}

baseUrl = "https://adimoldovan.github.io/web-stubs/"

reportingListener = new GebReportingListener()
reportsDir = 'target/geb-spock-reports'
