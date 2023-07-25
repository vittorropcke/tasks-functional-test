package br.ce.wcaquino.tasks.functional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TasksTest {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private final String URLBase = "http://localhost:8001/tasks/";

	

	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 20);
		driver.navigate().to(URLBase);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()=\"Tasks\"]")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()=\"A very simple task management tool\"]")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("todoTable")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addTodo")));
		
	}

	@After
	public void tearDown()  {
		driver.close();
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() {
		String dataDeHoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		 driver.findElement(By.id("addTodo")).click();
		 WebElement campoDescricaoTarefa = driver.findElement(By.id("task"));
		 WebElement campoDataTarefa = driver.findElement(By.id("dueDate"));
				 
		 campoDescricaoTarefa.sendKeys("Teste Funcional");
		 campoDataTarefa.sendKeys(dataDeHoje);
		
		
		 driver.findElement(By.id("saveButton")).click();
		
		String msg = driver.findElement(By.id("message")).getText();
		
		Assert.assertEquals("Success!", msg);
	
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr/td[normalize-space(text())='Teste Funcional'][1]")));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr/td[normalize-space(text())='%s'][2]", dataDeHoje)));
		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		String dataDeHoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		 driver.findElement(By.id("addTodo")).click();
		 //WebElement campoDescricaoTarefa = driver.findElement(By.id("task"));
		 WebElement campoDataTarefa = driver.findElement(By.id("dueDate"));
				 
		 campoDataTarefa.sendKeys(dataDeHoje);
		
		
		 driver.findElement(By.id("saveButton")).click();
		
		String msg = driver.findElement(By.id("message")).getText();
		
		Assert.assertEquals("Fill the task description", msg);
		
	}

	@Test
	public void naoDeveSalvarTarefaSemData() {
		//String dataDeHoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		 driver.findElement(By.id("addTodo")).click();
		 WebElement campoDescricaoTarefa = driver.findElement(By.id("task"));
		 //WebElement campoDataTarefa = driver.findElement(By.id("dueDate"));
				 
		 campoDescricaoTarefa.sendKeys("Teste Funcional");
		
		
		 driver.findElement(By.id("saveButton")).click();
		
		String msg = driver.findElement(By.id("message")).getText();
		
		Assert.assertEquals("Fill the due date", msg);
		
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		//String dataDeHoje = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		 driver.findElement(By.id("addTodo")).click();
		 WebElement campoDescricaoTarefa = driver.findElement(By.id("task"));
		 WebElement campoDataTarefa = driver.findElement(By.id("dueDate"));
				 
		 campoDescricaoTarefa.sendKeys("Teste Funcional");
		 campoDataTarefa.sendKeys("01/01/1990");
		
		 driver.findElement(By.id("saveButton")).click();
		
		String msg = driver.findElement(By.id("message")).getText();
		
		Assert.assertEquals("Due date must not be in past", msg);
		
	}
}
