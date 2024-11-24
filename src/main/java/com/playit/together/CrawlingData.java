package com.playit.together;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class CrawlingData {

    private WebDriver driver;

    private static final String url = "https://map.naver.com/v5/search";
    private static final String path = "C:\\Users\\dlwog\\OneDrive\\Documents\\ZerobaseSpring\\playit\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";

    public void process() {

        // 크롬 드라이버 세팅 (드라이버 설치 경로 입력)
        System.setProperty("webdriver.chrome.driver", path);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
        driver = new ChromeDriver(options);

        getDataList();

        // 탭 닫기
        driver.close();
        // 브라우저 닫기
        driver.quit();
    }

    // 데이터 가져오기
    private void getDataList() {

        // (1) 브라우저에서 url로 이동한다.
        driver.get(url);

        // WebDriverWait을 사용하여 iframe이 로드될 때까지 대기
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 최대 10초 대기

        try {
            // (2) 검색결과 iframe으로 frame을 바꾼다.
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("iframe#searchIframe")));
            driver.switchTo().frame(iframe);

            // 검색 결과 장소 목록을 elements에 담는다.
            List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".mFg6p>.XUrfU")));

            System.out.println("TestTest**********************************");
            System.out.println("elements.size() = " + elements.size());

            for (WebElement element : elements) {
                System.out.println(element.getText());
                System.out.println();
            }

            // (3) 첫번째 검색결과를 클릭한다.
            if (!elements.isEmpty()) {
                elements.get(0).click();
            } else {
                System.out.println("검색 결과가 없습니다.");
                return; // 검색 결과가 없으면 메서드를 종료
            }

            // 요소가 로드될 때까지 기다린다.
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            // 현재 프레임에서 상위 프레임으로 이동한다.
            driver.switchTo().defaultContent();

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
            // (4) 상세정보가 나오는 프레임으로 이동한다.
            WebElement entryIframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("iframe#entryIframe")));
            driver.switchTo().frame(entryIframe);

            // (5) 상세정보 프레임에서 주소 정보가 들어있는 곳으로 이동한다.
            List<WebElement> placeSectionContents = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".place_section_content>.PIbes")));
            WebElement homeElement = placeSectionContents.get(1);

            // (6) "주소" 버튼 요소를 찾아 클릭한다.
            WebElement addressButton = homeElement.findElement(By.className("LDgIH"));
            addressButton.click();

            // (7) "도로명"과 "지번" 정보가 들어있는 div 요소를 찾아서, 해당 정보를 가져온다.
            WebElement addressDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("Y31Sf")));
            List<WebElement> addressInfos = addressDiv.findElements(By.className("nQ7Lh"));

            for (WebElement addressInfo : addressInfos) {
                WebElement addressType = addressInfo.findElement(By.tagName("span"));
                String address = addressInfo.getText().replace(addressType.getText(), "").trim();
                System.out.println(addressType.getText() + " : " + address);
            }
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
        }
    }

}