import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FormularioTeste {

    private AndroidDriver<MobileElement> driver;

    @Before
    public void inicializarAppium() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("deviceName", "emulator-5554");
        desiredCapabilities.setCapability("automationName", "uiautomator2");

        //Instala a aplicação - Caso ela esteja instlada ele reinicia o app
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "/home/local/CONDUCTOR/priscila.mayume/Documentos/projetosCORE/CampoTreinamentoAppium/src/main/resources/CTAppium_1_2.apk");

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Selecionar formulario
        //primeira forma ... buscando pelo segundo elemento da classe
//        List<MobileElement> elementosEncontrados = driver.findElements(By.className("android.widget.TextView"));
//        elementosEncontrados.get(1).click();

        //segunda forma ... buscando pelo xpath
        driver.findElement(By.xpath("//android.widget.TextView[@text='Formulário']")).click();

    }

    @After
    public void sair(){
        driver.quit();

    }

    @Test
    public void devePreencherCampoTexto() {

        //Escrever nome
        MobileElement campoNome = driver.findElement(MobileBy.AccessibilityId("nome"));
        campoNome.sendKeys("Maria Fernanda");

        //Validar o nome escrito
        String text = campoNome.getText();
        Assert.assertEquals("Maria Fernanda", text);

    }

    @Test
    public void deveInteragirCombo() {

        //clicar no combo
        driver.findElement(MobileBy.AccessibilityId("console")).click();

        //selecionar a opção desejada
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Nintendo Switch']")).click();

        //verifica a opção escolhida
        String text = driver.findElement(By.xpath("//android.widget.TextView[@text='Nintendo Switch']")).getText();
        Assert.assertEquals("Nintendo Switch", text);
    }

    @Test
    public void deveInteragirSwitchCheckBox() {

        MobileElement check = driver.findElement(By.className("android.widget.CheckBox"));
        MobileElement switc = driver.findElement(MobileBy.AccessibilityId("switch"));
        Assert.assertTrue(check.getAttribute("checked").equals("false"));
        Assert.assertTrue(switc.getAttribute("checked").equals("true"));

        //clicar nos elementos
        check.click();
        switc.click();

        //verificar status aleterados
        Assert.assertTrue(check.getAttribute("checked").equals("true"));
        Assert.assertTrue(switc.getAttribute("checked").equals("false"));

    }

    @Test
    public void devePreencherESalvarormulario() {

        //Escrever nome
        MobileElement campoNome = driver.findElement(MobileBy.AccessibilityId("nome"));
        campoNome.sendKeys("Priscila - teste");

        //clicar no combo
        driver.findElement(MobileBy.AccessibilityId("console")).click();

        //selecionar a opção desejada
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Nintendo Switch']")).click();

        //marcar check e o switch
        MobileElement check = driver.findElement(By.className("android.widget.CheckBox"));
        MobileElement switc = driver.findElement(MobileBy.AccessibilityId("switch"));
//        check.click();
        switc.click();

        //Clicar em salvar
        driver.findElement(By.xpath("//android.widget.TextView[@text='SALVAR']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Validações

        MobileElement validaNome = driver.findElement(By.xpath("//android.widget.TextView[@text='Nome: Priscila - teste']"));
//        MobileElement validaConsoleSwitch = driver.findElement(By.xpath("//android.widget.TextView[@text='Console: switch']"));

        //Forma generica - ele procura um elemento cujo txt inicie por'Console'
        MobileElement validaConsole = driver.findElement(By.xpath("//android.widget.TextView[starts-with(@text, 'Console:')]"));
        MobileElement validaSwitch = driver.findElement(By.xpath("//android.widget.TextView[@text='Switch: Off']"));
        MobileElement validaCheckBox = driver.findElement(By.xpath("//android.widget.TextView[@text='Checkbox: Desabilitado']"));

        Assert.assertEquals("Nome: Priscila - teste", validaNome.getText());
        Assert.assertTrue(validaNome.getText().endsWith("Priscila - teste"));
//        Assert.assertTrue(validaConsoleSwitch.getText().endsWith("Console: switch"));
        Assert.assertEquals("Console: switch", validaConsole.getText());
        Assert.assertTrue(validaSwitch.getText().endsWith("Off"));
        Assert.assertTrue(validaCheckBox.getText().endsWith("Desabilitado"));

    }
}
