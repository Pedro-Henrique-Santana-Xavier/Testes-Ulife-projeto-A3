package Tests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class MensagensTest {
    WebDriver driver = new ChromeDriver();

    final String user = "12522190539@ulife.com.br";
    final String password = "220104";

    @Test
    @DisplayName("Teste de Login")
    public void DeveriaEfetuarLogin() throws InterruptedException {

        System.setProperty("webdriver.chorme.driver", "src\\driver\\chromedriver.exe");

        //----------------Efetuar login----------------

        driver.get("https://www.ulife.com.br/login.aspx");

        driver.manage().window().setSize(new Dimension(1936, 1066));
        driver.findElement(By.id("txtLogin")).sendKeys(user);
        driver.findElement(By.id("txtPassword")).sendKeys(password);
        driver.findElement(By.id("txtPassword")).sendKeys(Keys.ENTER);

        String h1PaginaInicialEsperado = "Oi Pedro, veja seus compromissos"; // String esperada

        String h1PaginaInicialCapturado = driver.findElement(By.tagName("h1")).getText(); // String capturada

        Assertions.assertEquals(h1PaginaInicialEsperado, h1PaginaInicialCapturado);

    }

    @Test
    @DisplayName("Teste de envio de mensagens")
    public void DeveriaEnviarMensagem () throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src\\driver\\chromedriver.exe");

        //----------------Efetuar login----------------

        driver.get("https://www.ulife.com.br/login.aspx");
        driver.manage().window().setSize(new Dimension(1936, 1066));
        driver.findElement(By.id("txtLogin")).sendKeys(user);
        driver.findElement(By.id("txtPassword")).sendKeys(password);
        driver.findElement(By.id("txtPassword")).sendKeys(Keys.ENTER);

        //----------------Enviar mensagem----------------

        driver.findElement(By.linkText("Mensagens")).click();

        driver.findElement(By.id("ctl00_b_hlkComposeMessage")).click(); //clica no botão "Escrever mensagem"

        WebElement iframeDestinatario = driver.findElement(By.id("iMessage"));

        driver.switchTo().frame(iframeDestinatario);

        String remetente = "Pedro Henrique Santana Xavier";
        String assunto = "Teste automatizado";
        String mensagem = "Esta mensagem é um teste automatizado";

        driver.findElement(By.id("token-input-facebook-demo")).sendKeys(remetente);

        Thread.sleep(Duration.ofSeconds(3));//Faz com que o programa pause por 3 segundo

        driver.findElement(By.id("token-input-facebook-demo")).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(By.id("token-input-facebook-demo")).sendKeys(Keys.ENTER);

        driver.findElement(By.id("txtSubject")).sendKeys(assunto);

        Thread.sleep(Duration.ofSeconds(2));

        driver.findElement(By.id("Editor_elm1")).sendKeys(mensagem);
        driver.findElement(By.id("imbSend")).click();

        Thread.sleep(Duration.ofSeconds(5));

        driver.navigate().refresh();

        Thread.sleep(Duration.ofSeconds(5));

        //----------------Capturar a mensagem----------------

        driver.findElement(By.cssSelector(" #ctl00_b_upR > div > ul > li > .inboxmessagesubject > h1 > a")).click();

        String remetenteCapturado = driver.findElement(By.id("ctl00_b_rptMessages_ctl00_collapseMessage__upl")).getText();
        String assuntoCapturado = driver.findElement(By.cssSelector(".headerthread > h1")).getText();

        WebElement iframeCorpoMensagem = driver.findElement(By.id("ctl00_b_rptMessages_ctl00_collapseMessage_litMessageBody_ifr"));
        driver.switchTo().frame(iframeCorpoMensagem);

        String mensagemCapturado = driver.findElement(By.tagName("body")).getText();

        System.out.println("Remetente -> " + remetenteCapturado);
        System.out.println("Assunto -> " + assuntoCapturado);
        System.out.println("Conteudo -> " + mensagemCapturado);

        //----------------Asserts----------------

        Assertions.assertEquals(remetente, remetenteCapturado);
        Assertions.assertEquals(assunto, assuntoCapturado);
        Assertions.assertEquals(mensagem, mensagemCapturado);

        }

    }





