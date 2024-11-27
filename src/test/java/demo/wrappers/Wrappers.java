package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import demo.TestCases;

import java.time.Duration;
import java.util.List;

public class Wrappers {
    ChromeDriver driver = TestCases.driver; 
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(50));


    public void YoutubeHomePage(){
        driver.get("https://www.youtube.com/");
    }

    public String GetCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public void ClickAboutLink() throws InterruptedException{
        WebElement AboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='About']")));
        AboutLink.click();
    }

    public String GetText(By xpath){
        WebElement Text = wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
        return Text.getText();
    }

    public void ClickButton(String Explore) throws InterruptedException{
        WebElement Button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='"+Explore+"']")));
        Button.click();
        Thread.sleep(6000);
    }   


    public void SectionRightClickExtremeEnd(String SectionName) throws InterruptedException{
        WebElement RightClick = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='"+SectionName+"']/ancestor::ytd-item-section-renderer//button[@aria-label='Next']")));

        while (RightClick.isDisplayed()) {
            RightClick.click();
            Thread.sleep(2000);
            if(RightClick.isDisplayed()==false){
                break;
            }
        } 
    }

    public String  GetSectionLastMovieMarkedAs(String SectionName){
        WebElement MovieMarkedAs = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[text()='"+SectionName+"']/ancestor::ytd-item-section-renderer//div[@role=\"img\"][2]/p)[last()]")));
        return MovieMarkedAs.getText();
    }

    public String GetSectionLastMovieCategory(String SectionName){
        WebElement MovieCategory = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[text()='"+SectionName+"']/ancestor::ytd-item-section-renderer//h3/following-sibling::span)[last()]")));
        String[] GetMovieCategory = MovieCategory.getText().trim().split(" ");
        return GetMovieCategory[0];
    }

    public String GetSectionLastPlaylistTitle(String SectionName){
        WebElement GetTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='"+SectionName+"']/ancestor::ytd-item-section-renderer//h3//span)[last()]")));
        return GetTitle.getText();
    }


    public int GetSectionLastItemNoOfTracks(){
        WebElement GetTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='This year in music']/ancestor::ytd-item-section-renderer//div[contains(text(),'songs')])[last()]")));
        String[] NoOfTracks = GetTitle.getText().split(" ");
        return  Integer.parseInt(NoOfTracks[0]);
    }

    public void LatestNewsPostTitleAndBody(int NoOfNews) throws InterruptedException{
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[text()='Latest news posts']//ancestor::ytd-rich-section-renderer//a[@id='author-text']//span[@class='style-scope ytd-post-renderer']")));
        List<WebElement> NewsTitles = driver.findElements(By.xpath("//span[text()='Latest news posts']//ancestor::ytd-rich-section-renderer//a[@id='author-text']//span[@class='style-scope ytd-post-renderer']"));


        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[text()='Latest news posts']//ancestor::ytd-rich-section-renderer//*[@id='home-content-text']")));
        List<WebElement> NewsDescriptions = driver.findElements(By.xpath("//span[text()='Latest news posts']//ancestor::ytd-rich-section-renderer//*[@id='home-content-text']"));
      
        for(int i=0;i<NoOfNews;i++){
            System.out.println("Title : "+NewsTitles.get(i).getText()+" . "+"Description : "+NewsDescriptions.get(i).getText());
        }

    }

    public int SumOfNumberOfLikes(int NoOfLikes) throws InterruptedException{
        
            List<WebElement> Likes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[text()='Latest news posts']//ancestor::ytd-rich-section-renderer//ytd-rich-item-renderer//span[@id='vote-count-middle']")));
            int count = 0;
            for(int i=0;i<NoOfLikes;i++){
                if(Likes.get(i).getText()!=null && Likes.isEmpty()==false){
                    count = count+(Integer.parseInt(Likes.get(i).getText()));
                }
            }
            return count;
       
    }
}
