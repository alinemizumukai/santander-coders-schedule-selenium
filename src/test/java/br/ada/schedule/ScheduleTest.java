package br.ada.schedule;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScheduleTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(
                new ChromeOptions().addArguments("--remote-allow-origins=*")
        );
    }

    @BeforeEach
    public void setUrl(){
        driver.get("http://localhost:8080/app/users");
    }

    @AfterAll
    public static void destroy(){
        driver.quit();
    }

    @Test
    @Order(1)
    public void cadastrarUmUsuarioComNomeUsernamePassword_deveTerSucesso(){

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("name")).sendKeys("Cristina Yang");
        driver.findElement(By.id("username")).sendKeys("cyang");
        driver.findElement(By.id("password")).sendKeys("cy-123");
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.xpath("//*[text()='cyang']")
        );
        assertNotNull(element);
    }

    @Test
    public void cadastrarUmUsuarioSemNome_naoPermitido(){

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("username")).sendKeys("mgrey");
        driver.findElement(By.id("password")).sendKeys("mg-321");
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.className("user-form-error")
        );
        assertNotNull(element);
        assertEquals("não deve estar em branco", element.getText());
    }

    @Test
    public void cadastrarUmUsuarioSemUsername_naoPermitido(){

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("name")).sendKeys("Meredith Grey");
        driver.findElement(By.id("password")).sendKeys("mg-321");
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.className("user-form-error")
        );
        assertNotNull(element);
        assertEquals("não deve estar em branco", element.getText());
    }

    @Test
    public void cadastrarUmUsuarioSemPassword_naoPermitido(){

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("name")).sendKeys("George O'Malley");
        driver.findElement(By.id("username")).sendKeys("gomalley");
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.className("user-form-error")
        );
        assertNotNull(element);
        assertEquals("não deve estar em branco", element.getText());
    }

    @Test
    @Order(2)
    public void cadastrarUmUsuarioComUsernameExistente_naoPermitido(){

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("name")).sendKeys("Charles Yang");
        driver.findElement(By.id("username")).sendKeys("cyang");
        driver.findElement(By.id("password")).sendKeys("char-123");
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.className("user-form-error")
        );
        assertNotNull(element);
        assertEquals("Username already in use", element.getText());
    }

    @Test
    @Order(2)
    public void cadastrarUmUsuarioComMesmoNomeEUsernameDiferente_deveTerSucesso(){

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("name")).sendKeys("Cristina Yang");
        driver.findElement(By.id("username")).sendKeys("crisyang");
        driver.findElement(By.id("password")).sendKeys("123-cy");
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.xpath("//*[text()='crisyang']")
        );
        assertNotNull(element);
    }

    @Test
    @Order(2)
    public void cadastrarUmUsuarioComMesmoPasswordEUsernameDiferente_deveTerSucesso(){

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/a")).click();

        driver.findElement(By.id("name")).sendKeys("Alex Karev");
        driver.findElement(By.id("username")).sendKeys("akarev");
        driver.findElement(By.id("password")).sendKeys("cy-123");
        driver.findElement(By.xpath("//form/button")).click();

        WebElement element = driver.findElement(
                By.xpath("//*[text()='akarev']")
        );
        assertNotNull(element);
    }

    @Test
    @Order(3)
    public void listarTodosOsUsuariosCadastrados(){

        List<WebElement> element = new ArrayList<>();
        element.add(driver.findElement(
                By.xpath("//*[text()='cyang']")
        ));
        element.add(driver.findElement(
                By.xpath("//*[text()='crisyang']")
        ));
        element.add(driver.findElement(
                By.xpath("//*[text()='akarev']")
        ));

        assertNotNull(element);
        assertEquals(3, element.size());
    }

    @Test
    @Order(2)
    public void listarCamposNameUsernameEEdit(){
        WebElement username = driver.findElement(By.xpath("//td[text()='cyang']"));
        assertNotNull(username);
        WebElement name = driver.findElement(By.xpath("//td[text()='cyang']//preceding-sibling::td"));
        assertNotNull(name);
        WebElement editElement = driver.findElement(By.xpath("//td[text()='cyang']//following-sibling::td/a[text()='Edit']"));
        assertNotNull(editElement);
    }
}
