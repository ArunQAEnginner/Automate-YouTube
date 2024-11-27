package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Assert;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        public static ChromeDriver driver;

        @Test
        public void testCase01() throws InterruptedException{
        //testCase01: Go to YouTube.com and Assert you are on the correct URL.
        //Click on "About" at the bottom of the sidebar, and print the message on the screen.
                boolean status = false;
                Wrappers test1 = new Wrappers();
                test1.YoutubeHomePage();
                status = test1.GetCurrentUrl().contains("youtube.com");
                org.testng.Assert.assertTrue(status,"Url is Wrong"); 
                test1.ClickAboutLink();
                String Text = test1.GetText(By.xpath("//section[@class='ytabout__content']"));
                System.out.println("Message on About Page : "+Text);
        } 

        @Test
        public void testCase02() throws InterruptedException{
        //testCase02: Go to the "Films" or "Movies" tab and in the 'Top Selling' section, scroll to the extreme right. 
        //Apply a Soft Assert on whether the movie is marked "A" for Mature or not. 
        //Apply a Soft assert on the movie category to check if it exists ex: "Comedy", "Animation", "Drama".   
                boolean status = false;
                Wrappers test2 = new Wrappers();
                test2.YoutubeHomePage();  
                test2.ClickButton("Movies");
                test2.SectionRightClickExtremeEnd("Top selling");
                SoftAssert softAssert = new SoftAssert();
                String MovieMarked = test2.GetSectionLastMovieMarkedAs("Top selling");
                System.out.println("MovieMarkedAs : "+MovieMarked);
                status = MovieMarked.equalsIgnoreCase("A");
                softAssert.assertFalse(status, "Movie not marked AS 'A'");
                String MovieCategory = test2.GetSectionLastMovieCategory("Top selling");
                System.out.println("Movie Category : "+MovieCategory);
                status = MovieCategory.equals("Animation") || MovieCategory.equals("Drama") || MovieCategory.equals("Comedy");
                softAssert.assertTrue(status, "movie category not exists in 'Comedy', 'Animation', 'Drama'");
                softAssert.assertAll();
        }

        @Test
        public void testCase03() throws InterruptedException{
        //testCase03: Go to the 'Music' tab and in the 1st section, scroll to the extreme right.
        //Print the name of the playlist.
        //Soft Assert on whether the number of tracks listed is less than or equal to 50.
                boolean status = false;
                Wrappers test3 = new Wrappers();
                test3.YoutubeHomePage();
                test3.ClickButton("Music");
                test3.SectionRightClickExtremeEnd("This year in music");
                String NameOfPlaylist = test3.GetSectionLastPlaylistTitle("This year in music");
                System.out.println("name of the playlist : "+NameOfPlaylist);
                int NummberOfTracks = test3.GetSectionLastItemNoOfTracks();
                System.out.println("number of tracks listed : "+NummberOfTracks);
                status = NummberOfTracks<=50;
                SoftAssert softAssert = new SoftAssert();
                softAssert.assertTrue(status);
        }

        @Test
        public void testCase04() throws InterruptedException{
        //testCase04: Go to the 'News' tab and 
        //print the title and body of the 1st 3 'Latest News Posts' along with the sum of the number of likes on all 3 of them.
        //No likes given means 0.
                boolean status = false;
                Wrappers test4 = new Wrappers();
                test4.YoutubeHomePage();
                test4.ClickButton("News");
                test4.LatestNewsPostTitleAndBody(3);
                int SumOfLikes = test4.SumOfNumberOfLikes(3);
                System.out.println("sum of the number of likes on all 3 of them : "+SumOfLikes);
        }

        @Test
        public void testCase05(){
                
        }
        
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}