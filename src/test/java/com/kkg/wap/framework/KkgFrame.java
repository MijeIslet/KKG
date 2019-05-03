package com.kkg.wap.framework;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.List;

import static oracle.jrockit.jfr.events.Bits.intValue;
import static org.testng.Assert.assertTrue;

/**
 * Created by Administrator on 2016/5/16.
 */
public class KkgFrame {
    private WebDriver driver;
    private int stepInterval = Integer.parseInt(GlobalSettings.stepInterval);
    private int timeout = Integer.parseInt(GlobalSettings.timeout);
    private Logger logger = Logger.getLogger(KkgFrame.class.getName());

    public static String subNumber = "";

    /**
     * 构造函数初始化webdriver
     *
     * @param browserType 是浏览器类型
     */
    public KkgFrame(int browserType) {
        switch (browserType) {
            //1:chrome
            case 1:
                System.setProperty("webdriver.chrome.driver", "res/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            //2:opera
//            case 2:
//                System.setProperty("webdriver.opera.driver", "res/operadriver");
//                driver = new OperaDriver();
//                break;
            //3:firefox
            case 3:
//                System.setProperty("webdriver.gecko.driver", "res/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            //4:ie
            case 4:
                System.setProperty("webdriver.ie.driver", "res/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
            //case 5设置浏览器为google 手机模式
            case 5:
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "iPhone X");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                driver = new ChromeDriver(chromeOptions);
                break;
            //case 6设置浏览器为google 为指定宽度
            case 6:
                Map<String, Object> deviceMetrics = new HashMap<>();
                deviceMetrics.put("width", 360);
                deviceMetrics.put("height", 640);
                deviceMetrics.put("pixelRatio", 3.0);
                Map<String, Object> mobileEmulation_2 = new HashMap<>();
                mobileEmulation_2.put("deviceMetrics", deviceMetrics);
                mobileEmulation_2.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
                ChromeOptions chromeOptions_2 = new ChromeOptions();
                chromeOptions_2.setExperimentalOption("mobileEmulation", mobileEmulation_2);
                driver = new ChromeDriver(chromeOptions_2);
                break;
        }
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    /**
     * 构造函数初始化webdriver
     *
     * @param browserType 是浏览器类型
     */
    public KkgFrame(int browserType, String seleniumURL) {
        switch (browserType) {
            //1:chrome
            case 1:
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//                capabilities.setBrowserName("chrome");
//                capabilities.setVersion("2.48.2");
                capabilities.setPlatform(Platform.WINDOWS);
                try {
                    driver = new RemoteWebDriver(new URL(seleniumURL), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            //2:opera
            case 2:
                System.setProperty("webdriver.opera.driver", "res/operadriver");
                driver = new OperaDriver();
                break;
            //3:firefox
            case 4:
               /* driver = new FirefoxDriver();
                break;*/

                DesiredCapabilities capabilities3 = DesiredCapabilities.firefox();
//                capabilities.setBrowserName("chrome");
//                capabilities.setVersion("2.48.2");
                capabilities3.setPlatform(Platform.WINDOWS);
                try {
                    driver = new RemoteWebDriver(new URL(seleniumURL), capabilities3);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    break;
                }
                //4:safari
            case 3:
                driver = new SafariDriver();
                break;

        }
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }


    /**
     * 获取webdriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * 打开浏览器
     *
     * @param url 浏览器地址
     */
    public void openUrl(String url) {
        driver.get(url);
    }

    /**
     * 通过ID点击
     *
     * @param id 是控件ID
     */
    public void clickById(String id) {
        findById(id).click();
    }

    /**
     * 通过linkText点击
     *
     * @param linkText 是控件name
     */
    public void clickByLinkText(String linkText) {
        findByLinkText(linkText).click();
    }

    /**
     * 通过Name点击
     *
     * @param name 是控件name
     */
    public void clickByName(String name) {
        findByName(name).click();
    }


    /**
     * 通过xpath点击
     *
     * @param xpath 是控件xpath
     */
    public void clickByXpath(String xpath) {
        findByXpath(xpath).click();
    }

    /**
     * 通过class点击
     *
     * @param className 是控件class
     */
    public void clickByClass(String className) {
        findByClass(className).click();
    }

    /**
     *
     * @param className
     * @param index
     */
    public void clickByClassAndIndex(String className, int index) {
        findByClassAndIndex(className, index).click();
    }

    /**
     * 通过cssSelector点击
     *
     * @param cssSelector 是控件cssSelector
     */
    public void clickByCssSelector(String cssSelector) {
        findByCssSelecter(cssSelector).click();
    }

    /**
     * 通过ID输入值
     *
     * @param id    是控件id
     * @param value 是输入控件的值
     */
    public void typeById(String id, String value) {
        WebElement webElement = findById(id);
        webElement.clear();
        webElement.sendKeys(value);
    }

    /**
     * 通过Name输入值
     *
     * @param name  是控件name
     * @param value 是输入控件的值
     */
    public void typeByName(String name, String value) {
        WebElement webElement = findByName(name);
        webElement.clear();
        webElement.sendKeys(value);
    }

    /**
     * 通过Name输入值
     *
     * @param className 是控件class
     * @param value     是输入控件的值
     */
    public void typeByClass(String className, String value) {
        WebElement webElement = findByClass(className);
        webElement.clear();
        webElement.sendKeys(value);
    }

    /**
     * 通过Name输入值
     *
     * @param className 是控件class
     * @param value     是输入控件的值
     */
    public void typeByClassAndIndex(String className, int index, String value) {
        WebElement webElement = findByClassAndIndex(className, index);
        webElement.clear();
        webElement.sendKeys(value);
    }

    /**
     * 通过cssSelector输入值
     *
     * @param cssSelector 是控件cssSelcetor
     * @param value       是输入控件的值
     */
    public void typeByCssSelector(String cssSelector, String value) {
        WebElement webElement = findByCssSelecter(cssSelector);
        webElement.clear();
        webElement.sendKeys(value);
    }

    /**
     * 通过xpath输入值
     *
     * @param xpath 是控件xpath
     * @param value 是输入控件的值
     */
    public void typeByXpath(String xpath, String value) {
        WebElement webElement = findByXpath(xpath);
        logger.info(webElement.getAttribute("type"));
        if (webElement.getAttribute("type").equals("text")) {
            webElement.clear();
        }
        webElement.sendKeys(value);
    }


    /**
     * 通过ID查找控件
     *
     * @param elementId 是控件id
     */
    public WebElement findById(String elementId) {
        final long endTime = System.currentTimeMillis() + timeout;
        WebElement webElement;
        while (true) {
            try {

                webElement = driver.findElement(By.id(elementId));
                if (webElement.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    logger.info("can not find element by id:" + elementId + " retry!!!");
                    pause(stepInterval);
                    continue;
                } else {
                    handleFailure("Time out to find element by id " + elementId + " !!!");
                }
            }
        }
        return webElement;
    }

    /**
     * 通过ID查找控件
     *
     * @param linkText 是控件id
     */
    private WebElement findByLinkText(String linkText) {
        final long endTime = System.currentTimeMillis() + timeout;
        WebElement webElement;
        while (true) {
            try {
                webElement = driver.findElement(By.linkText(linkText));
                if (webElement.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    logger.info("can not find element by linkText:" + linkText + " retry!!!");
                    pause(stepInterval);
                    continue;
                } else {
                    handleFailure("Time out to find element by linkText: " + linkText + " !!!");
                }
            }
        }
        return webElement;
    }

    /**
     * 通过name查找控件
     *
     * @param name 是控件id
     */
    private WebElement findByName(String name) {
        final long endTime = System.currentTimeMillis() + timeout;
        WebElement webElement;
        while (true) {
            try {
                webElement = driver.findElement(By.name(name));
                if (webElement.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    logger.info("can not find element by name:" + name + " retry!!!");
                    pause(stepInterval);
                    continue;
                } else {
                    handleFailure("Time out to find element by name " + name + " !!!");
                }
            }
        }
        return webElement;
    }

    /**
     * 通过className查找控件
     *
     * @param className 是控件class
     */
    private WebElement findByClass(String className) {
        final long endTime = System.currentTimeMillis() + timeout;
        WebElement webElement = null;
        while (true) {
            try {
                webElement = driver.findElement(By.className(className));
                if (webElement.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    logger.info("can not find element by class:" + className + " retry!!!");
                    pause(stepInterval);
                    continue;
                } else {
                    handleFailure("Time out to find element by class " + className + " !!!");
                }
            }
        }
        return webElement;
    }

    /**
     * 通过className查找控件
     *
     * @param className 是控件class
     */
    private WebElement findByClassAndIndex(String className, int index) {
        final long endTime = System.currentTimeMillis() + timeout;
        WebElement webElement = null;
        while (true) {
            try {
                webElement = driver.findElements(By.className(className)).get(index);
                if (webElement.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    logger.info("can not find element by class:" + className + " retry!!!");
                    pause(stepInterval);
                    continue;
                } else {
                    handleFailure("Time out to find element by class " + className + " !!!");
                }
            }
        }
        return webElement;
    }

    /**
     * 通过xpath查找控件
     *
     * @param xpath 是控件xpath
     */
    private WebElement findByXpath(String xpath) {
        final long endTime = System.currentTimeMillis() + timeout;
        WebElement webElement;
        while (true) {
            try {
                webElement = driver.findElement(By.xpath(xpath));
                if (webElement.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    logger.info("can not find element by xpath:" + xpath + " retry!!!");
                    pause(stepInterval);
                    continue;
                } else {
                    handleFailure("Time out to find element by xpath " + xpath + " !!!");
                }
            }
        }
        return webElement;
    }

    /**
     * 通过cssSelector查找控件
     *
     * @param cssSelector 是控件css
     */
    private WebElement findByCssSelecter(String cssSelector) {
        final long endTime = System.currentTimeMillis() + timeout;
        WebElement webElement;
        while (true) {
            try {
                webElement = driver.findElement(By.cssSelector(cssSelector));
                if (webElement.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    logger.info("can not find element by cssSelector:" + cssSelector + " retry!!!");
                    pause(stepInterval);
                    continue;
                } else {
                    handleFailure("Time out to find element by cssSelector " + cssSelector + " !!!");
                }
            }
        }
        return webElement;
    }

    /**
     * 通过tagName查找控件
     *
     * @param tagName 是控件tag
     */
    public List<WebElement> findByTags(String tagName) {
        final long endTime = System.currentTimeMillis() + timeout;
        List<WebElement> webElementList;
        while (true) {
            try {
                webElementList = driver.findElements(By.tagName(tagName));
                if (webElementList.get(0).isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    logger.info("can not find element by tagName:" + tagName + " retry!!!");
                    pause(stepInterval);
                    continue;
                } else {
                    handleFailure("Time out to find element by tagName " + tagName + " !!!");
                }
            }
        }
        return webElementList;
    }

    /**
     * 通过tagName查找控件
     *
     * @param tagName 是控件tag
     */
    public WebElement findByTag(String tagName) {
        final long endTime = System.currentTimeMillis() + timeout;
        WebElement webElement;
        while (true) {
            try {
                webElement = driver.findElement(By.tagName(tagName));
                if (webElement.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    logger.info("can not find element by tagName:" + tagName + " retry!!!");
                    pause(stepInterval);
                    continue;
                } else {
                    handleFailure("Time out to find element by tagName " + tagName + " !!!");
                }
            }
        }
        return webElement;
    }

    /**
     * 错误 中断程序
     *
     * @param notice 是异常信息
     */
    private void handleFailure(String notice) {
        String png = LogTools.screenShot(this);
        String log = notice + " >> capture screenshot at " + png;
        logger.error(log);
//        if (GlobalSettings.baseStorageUrl.lastIndexOf("/") == GlobalSettings.baseStorageUrl.length()) {
//            GlobalSettings.baseStorageUrl = GlobalSettings.baseStorageUrl.substring(0, GlobalSettings.baseStorageUrl.length() - 1);
//        }
        //      Reporter.log(log + "<br/><img src=\"" + GlobalSettings.baseStorageUrl + "/" + png + "\" />");
        Assert.fail(log);

    }

    /**
     * 暂停
     *
     * @param time 是等待的时间单位毫秒
     */
    public void pause(int time) {
        if (time <= 0) {
            return;
        }
        try {
            Thread.sleep(time);
            logger.info("Pause " + time + " ms");
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 退出浏览器
     */
    public void quit() {
//        driver.manage().deleteAllCookies();
        driver.quit();
    }

    /**
     * 点击alert的确定
     */
    public void alertConfirm() {
        pause(stepInterval);
        alertAccept(System.currentTimeMillis(), timeout);
    }

    /**
     * 点击alert的取消
     */
    public void alertDismiss() {
        pause(stepInterval);
        alertDismiss(System.currentTimeMillis(), timeout);
    }

    /**
     * 点击alert的确定
     *
     * @param startTime 开始时间
     * @param timeout   超时时间
     */
    private void alertAccept(long startTime, int timeout) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            if (System.currentTimeMillis() - startTime > timeout) {
                logger.info("Element alert is not found");
                handleFailure("Failed to click confirm");
            } else {
                pause(500);
                logger.info("Element alert is not found, try again");
                alertAccept(startTime, timeout);
            }
        }
    }

    /**
     * 点击alert的取消
     *
     * @param startTime 开始时间
     * @param timeout   超时时间
     */
    private void alertDismiss(long startTime, int timeout) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (Exception e) {
            if (System.currentTimeMillis() - startTime > timeout) {
                logger.info("Element alert is not found");
                handleFailure("Failed to click cancel");
            } else {
                pause(500);
                logger.info("Element alert is not found, try again");
                alertDismiss(startTime, timeout);
            }
        }
    }


    /**
     * 通过xpath切换 iframe
     *
     * @param xpath iframe的xpath
     */
    public void enterFrameByXpath(String xpath) {
        pause(stepInterval);
        driver.switchTo().frame(findByXpath(xpath));
        logger.info("Entered iframe by xpath:" + xpath);
    }

    /**
     * 通过id切换 iframe
     *
     * @param id iframe的id
     */
    public void enterFrameById(String id) {
        pause(stepInterval);
        driver.switchTo().frame(findById(id));
        logger.info("Entered iframe by id:" + id);
    }

    /**
     * 通过id切换 iframe
     *
     * @param webElement
     */
    public void enterFrameByElement(WebElement webElement) {
        pause(stepInterval);
        driver.switchTo().frame(webElement);
        logger.info("Entered iframe by webElement:" + webElement);
    }


    /**
     * 通过name切换 iframe
     *
     * @param name iframe的name
     */
    public void enterFrameByName(String name) {
        pause(stepInterval);
        driver.switchTo().frame(findByName(name));
        logger.info("Entered iframe by name: " + name);
    }

    /**
     * 通过className切换 iframe
     *
     * @param className iframe的class
     */
    public void enterFramebyClass(String className) {
        pause(stepInterval);
        driver.switchTo().frame(findByClass(className));
        logger.info("Entered iframe by className: " + className);
    }

    /**
     * 通过cssSelector切换 iframe
     *
     * @param cssSelector iframe的cssSelector
     */
    public void enterFrameByCssSelector(String cssSelector) {
        pause(stepInterval);
        driver.switchTo().frame(findByCssSelecter(cssSelector));
        logger.info("Entered iframe by cssSelector:" + cssSelector);
    }

    /**
     * 通过xpath切换 iframe
     *
     * @param xpath iframe的xpath
     */
    public void enterFramebyXpath(String xpath) {
        pause(stepInterval);
        driver.switchTo().frame(findByXpath(xpath));
        logger.info("Entered iframe by xpath: " + xpath);
    }

    /**
     * 返回到默认的 iframe
     */
    public void leaveFrame() {
        pause(stepInterval);
        driver.switchTo().defaultContent();
        logger.info("Left the iframe");
    }


    /**
     * 刷新界面
     */
    public void refresh() {
        pause(stepInterval);
        driver.navigate().refresh();
        logger.info("Refreshed");
    }

    /**
     * 操作浏览器导航菜单-回退按钮
     */
    public void back() {
        pause(stepInterval);
        driver.navigate().back();
        logger.info("backed");
    }

    /**
     * 操作浏览器导航菜单-前进按钮
     */
    public void forward() {
        pause(stepInterval);
        driver.navigate().forward();
        logger.info("forwarded");
    }


    /**
     * 通过xpath悬停鼠标
     *
     * @param xpath 界面原属的xpath
     */
    public void mouseClickAndHoldByXpath(String xpath) {
        pause(stepInterval);
        // First make mouse out of browser
        Robot rb = null;
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        rb.mouseMove(0, 0);
        WebElement we = findByXpath(xpath);
        try {
            Actions builder = new Actions(driver);
            builder.moveToElement(we).clickAndHold().build().perform();
        } catch (Exception e) {
            e.printStackTrace();
            handleFailure("Failed to click and hold by xpath: " + xpath);
        }
        logger.info("click and hold by xpath: " + xpath);
    }

    /**
     * 通过id悬停鼠标
     *
     * @param id 界面原属的id
     */
    public void mouseClickAndHoldById(String id) {
        pause(stepInterval);
        // First make mouse out of browser
        Robot rb = null;
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        rb.mouseMove(0, 0);
        WebElement we = findById(id);
        try {
            Actions builder = new Actions(driver);
            builder.moveToElement(we).clickAndHold().build().perform();
        } catch (Exception e) {
            e.printStackTrace();
            handleFailure("Failed to click and hold by id: " + id);
        }
        logger.info("click and hold by id: " + id);
    }

    /**
     * 通过name悬停鼠标
     *
     * @param name 界面原属的name
     */
    public void mouseClickAndHoldByName(String name) {
        pause(stepInterval);
        // First make mouse out of browser
        Robot rb = null;
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        rb.mouseMove(0, 0);
        WebElement we = findByName(name);
        try {
            Actions builder = new Actions(driver);
            builder.moveToElement(we).clickAndHold().build().perform();
        } catch (Exception e) {
            e.printStackTrace();
            handleFailure("Failed to click and hold by name: " + name);
        }
        logger.info("click and hold by name: " + name);
    }

    /**
     * 通过class悬停鼠标
     *
     * @param className 界面原属的class
     */
    public void mouseClickAndHoldByClass(String className) {
        pause(stepInterval);
        // First make mouse out of browser
        Robot rb = null;
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        rb.mouseMove(0, 0);
        WebElement we = findByClass(className);
        try {
            Actions builder = new Actions(driver);
            builder.moveToElement(we).clickAndHold().build().perform();
        } catch (Exception e) {
            e.printStackTrace();
            handleFailure("Failed to click and hold by className: " + className);
        }
        logger.info("click and hold by className: " + className);
    }

    /**
     * 通过cssSelector悬停鼠标
     *
     * @param cssSelector 界面原属的class
     */
    public void mouseClickAndHoldByCssSelector(String cssSelector) {
        pause(stepInterval);
        // First make mouse out of browser
        Robot rb = null;
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        rb.mouseMove(0, 0);
        WebElement we = findByCssSelecter(cssSelector);
        try {
            Actions builder = new Actions(driver);
            builder.moveToElement(we).clickAndHold().build().perform();
        } catch (Exception e) {
            e.printStackTrace();
            handleFailure("Failed to click and hold by cssSelector: " + cssSelector);
        }
        logger.info("click and hold by cssSelector: " + cssSelector);
    }

    /**
     * 执行js返回webelement
     *
     * @param js javaScript串
     */
    public WebElement findByJs(String js) {
        WebElement element = (WebElement) ((JavascriptExecutor) driver).executeScript(js);
        return element;
    }


    /**
     * 执行js,并且用webelement做参数
     *
     * @param js javaScript串
     */
    public void executeJs(String js) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(js);
    }


    /**
     * 通过id悬停鼠标
     *
     * @param linktext 界面原属的LinkText
     */
    public void mouseClickAndHoldByLinkText(String linktext) {
        pause(stepInterval);
        // First make mouse out of browser
        Robot rb = null;
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        rb.mouseMove(0, 0);
        WebElement we = findByLinkText(linktext);
        try {
            Actions builder = new Actions(driver);
            builder.moveToElement(we).clickAndHold().build().perform();
        } catch (Exception e) {
            e.printStackTrace();
            handleFailure("Failed to click and hold by linktext: " + linktext);
        }
        logger.info("click and hold by linktext: " + linktext);
    }


    /**
     * 切换窗口--打开一个新窗口
     */
    public void switchWindow() {
        String currentWindow = driver.getWindowHandle();//获取当前窗口句柄
        Set<String> handles = driver.getWindowHandles();//获取所有窗口句柄
        Iterator<String> it = handles.iterator();
        while (it.hasNext()) {
            if (currentWindow == it.next()) {
                continue;// 跳出本次循环，继续下个循环
            }
            driver.switchTo().window(it.next());//切换到新窗口
        }

    }

    /**
     * 切换窗口--打开2个以上窗口
     */
    public void switchWindowByIndex(int i) {
        Set<String> windowHandles = driver.getWindowHandles();//获取当前窗口句柄
        List<String> it = new ArrayList<String>(windowHandles);//获取所有窗口句柄
        try {
            Thread.sleep(1000);
            driver.switchTo().window(it.get(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = driver.getCurrentUrl();
        System.out.println("url");
        // driver.switchTo().window(it.get(0));//切换到新窗口
    }

    /**
     * 显式等待页面ID出现并点击
     *
     * @param elementid
     */
    public void WebDriverWaitAndclickById(int second, final String elementid) {
        WebElement delaytofind = (new WebDriverWait(driver, second)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id(elementid));
            }
        });
        delaytofind.click();
    }

    /**
     * 显式等待页面xpath出现并点击
     *
     * @param xpath
     */
    public void WebDriverWaitAndclickByXpath(int second, final String xpath) {
        WebElement delaytofind = (new WebDriverWait(driver, second)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath(xpath));
            }
        });
        delaytofind.click();
    }

    /**
     * 显式等待页面LinkText出现并点击
     *
     * @param linkText
     */
    public void WebDriverWaitAndclickBylinkText(int second, final String linkText) {
        WebElement delaytofind = (new WebDriverWait(driver, second)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.linkText(linkText));
            }
        });
        delaytofind.click();
    }


    /**
     * 显式等待页面元素出现id定位
     *
     * @param id
     */
    public void WebDriverWaitUntilPageContainsElementById(String id) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));

        //        findByClass(id);
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        driver.findElement(By.id("wait")).click();
//        ExpectedCondition<WebElement> e1 = ExpectedConditions.visibilityOfElementLocated(By.id(id));
//
//        System.out.println(wait.until(e1).getText());
//        System.out.println(e1.apply(driver).getText());
    }

    /**
     * 显式等待页面元素出现class定位
     *
     * @param className
     */
    public void WebDriverWaitUntilPageContainsElementByClass(String className) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
//        findByClass(className);
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        driver.findElement(By.className("wait")).click();
//        ExpectedCondition<WebElement> e1 = ExpectedConditions.visibilityOfElementLocated(By.className(className));
//
//        System.out.println(wait.until(e1).getText());
//        System.out.println(e1.apply(driver).getText());
    }

    /**
     * 显式等待页面元素出现
     *
     * @param name
     */
//    public void WebDriverWaitUntilPageContainsElementByName(String name) {
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(name)));
//        findByName(name);
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        driver.findElement(By.name("wait")).click();
//        ExpectedCondition<WebElement> e1 = ExpectedConditions.visibilityOfElementLocated(By.name(name));
//
//        System.out.println(wait.until(e1).getText());
//        System.out.println(e1.apply(driver).getText());
//    }

    /**
     * 显式等待页面元素出现xpath定位
     *
     * @param xPath
     */
    public void WebDriverWaitUntilPageContainsElementByXPath(String xPath) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
    }

    /**
     * 显式等待页面元素出现linkText定位
     *
     * @param linkText
     */
    public void WebDriverWaitUntilPageContainsElementByLinkText(String linkText) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
//        findByLinkText(linkText);
//        WebDriverWait wait = new WebDriverWait(driver, 10);
//        driver.findElement(By.name("wait")).click();
//        ExpectedCondition<WebElement> e1 = ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText));
//
//        System.out.println(wait.until(e1).getText());
//        System.out.println(e1.apply(driver).getText());
    }


    /**
     * 显式等待页面文本出现
     *
     * @param text
     */
    public void WebDriverWaitUntilPageContainsText(String text) {
        driver.getPageSource().contains(text);
        WebDriverWait wait = new WebDriverWait(driver, 10);

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name()));

//        driver.getPageSource().contains(text)(By.name("wait"));
//        ExpectedCondition<WebElement> e1 = ExpectedConditions.invisibilityOfElementWithText(text);
//
//          System.out.println(wait.until(e1).getText());
//         System.out.println(e1.apply(driver).getText());
    }

    /**
     * 显式等待页面,验证文本出现
     *
     * @param text
     */
    public void WebDriverWaitUntilPageContainsText(String id,String text) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id(id),text));

    }


    /**
     * 判断页面元素是否出现
     *
     * @param LinkText
     */
    public void expectLinkTextExistOrNotByLinkText(boolean expectExist, String LinkText) {
        WebElement webElement = findByLinkText(LinkText);
        if (expectExist) {
            if (webElement.isDisplayed()) {
                Assert.assertTrue(true);
            } else {
                handleFailure("can not found LinkText:" + LinkText);
            }

        } else {
            if (webElement.isDisplayed()) {
                Assert.assertTrue(false);
            } else {
                handleFailure("can not found LinkText:" + LinkText);
            }
        }
    }

    /**
     * 判断页面元素是否出现
     *
     * @param xpath
     */
    public void expectXpathExistOrNotByXpath(boolean expectExist, final String xpath) {
        WebElement webElement = findByXpath(xpath);
        if (expectExist) {
            if (webElement.isDisplayed()) {
                Assert.assertTrue(true);
            } else {
                handleFailure("can not found xpath:" + xpath);
            }

        } else {
            if (webElement.isDisplayed()) {
                Assert.assertFalse(false);
            } else {
                handleFailure("can not found xpath:" + xpath);
            }
        }
    }

    /**
     * 判断页面元素是否出现
     *
     * @param className
     */
    public void expectClassExistOrNotByClass(boolean expectExist, final String className) {
        WebElement webElement = findByClass(className);
        if (expectExist) {
            if (webElement.isDisplayed()) {
                Assert.assertTrue(true);
            }

        } else {
            if (webElement.isDisplayed()) {
                Assert.assertTrue(false);
            } else {
                handleFailure("can not found className:" + className);
            }
        }
    }

    /**
     * 判断页面元素是否出现
     *
     * @param name
     */
    public void expectNameExistOrNotByName(boolean expectExist, final String name) {
        WebElement webElement = findByName(name);
        if (expectExist) {
            if (webElement.isDisplayed()) {
                Assert.assertTrue(true);
            } else {
                handleFailure("can not found name:" + name);
            }

        } else {
            if (webElement.isDisplayed()) {
                Assert.assertTrue(false);
            } else {
                handleFailure("can not found Name:" + name);
            }
        }
    }


    /**
     * 断言-HTML页面文本是否出现,当expectExist为True，结果为false，程序中断
     *
     * @param text
     */
    public void expectTextExistOrNot(boolean expectExist, String text) {
        boolean textExist = driver.getPageSource().contains(text);
        if (expectExist != textExist) {
            handleFailure("can not found text:" + text);
        }
        //以下方法也可以
//        if (expectExist) {
//            if (textExist) {
//                Assert.assertTrue(true);
//            } else {
//                handleFailure("can not found name:" + text);
//            }
//
//        } else {
//            if (textExist) {
//                handleFailure("can not found Name:" + text);
//            } else {
//                Assert.assertFalse(false);
//            }
//        }

    }


    /**
     * 判断HTML页面文本是否出现，return不管是true还是false，程序都是正常运行。
     *
     * @param text
     */
    public boolean isContainTextFromPageSourceOrNot(String text) {
        return driver.getPageSource().contains(text);
//        boolean isTheTextPresent = driver.getPageSource().contains(text);
//        if (isTheTextPresent) {
//            return true;//这样写，不管是true还是false，程序都是正常运行的。
//        } else {  //Assert如果使用了，catch就不会中断程序
//            return false;
//        }
    }

    /**
     * 上传附件ByClass
     */
    public void fileUploadByClass(String className, String filePath) {
        WebElement webElement = findByClass(className);
        webElement.sendKeys(filePath);
    }

    /**
     * 上传附件ById
     */
    public void fileUploadById(String id, String filePath) {
        WebElement webElement = findById(id);
        webElement.sendKeys(filePath);
    }

    /**
     * 上传附件ByXpath
     */
    public void fileUploadByXpath(String xpath, String filePath) {
        WebElement webElement = findByXpath(xpath);
        webElement.sendKeys(filePath);
    }

    /**
     * 利用AutoIT上传文件,调用exe执行文件
     */
    public void fileUploadByAutoIT(String filePath) {
        // Java 的Runtime 模块的getruntime.exec()方法可以调用exe 程序并执行。
        Runtime exe = Runtime.getRuntime();
        pause(2000);
        try {
            // 运行指定位置的.exe文件
            exe.exec(filePath);
            //设置合适的上传等待时间，不然可能会上传失败
            pause(4000);
        } catch (IOException e) {
            System.out.println("Error to run the exe");
            e.printStackTrace();
        }
    }


    /**
     * 利用AutoITX上传文件
     */
    public void fileUploadByAutoITX(String xpath, String filePath) {
    }


    /**
     * 执行js,操作滚动条到任意高度
     */
    public void scrollHight(int height) {
        //滚动条滚动
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        String setscroll = "document.documentElement.scrollTop=" + height;//垂直滚动条
//        String setscroll = "document.documentElement.scrollLeft=" + "500";//水平滚动条  参考http://blog.csdn.net/liuxueyi521/article/details/49022465
//        String high ="scrollTo(0,10000)";//直接输入数据
        executor.executeScript(setscroll);
    }

    /**
     * 执行js,操作滚动条到元素位置
     */
    public void scrollElement(String xpath) {
        WebElement webElement = findByXpath(xpath);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView();", xpath);//移动到元素element对象的”顶端“与当前窗口的”顶部“对齐
        //  executor.executeScript("arguments[0].scrollIntoView(false);",className);//移动到元素element对象的”底端“与当前窗口的”顶部“对齐
    }

    /**
     * 执行js,操作滚动条到指定元素位置
     */
    public void moveMenu(String cssSelector) {
        WebElement webElement = findByCssSelecter(cssSelector);
        //滚动到指定元素位置
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).perform();
    }


    /**
     * 通过属性值搜引选择下接框
     *
     * @param id,index
     */
    public void selectByIndex(String id, int index) {
        Select selector = new Select(findById(id));
        selector.selectByIndex(index);
    }

    /**
     * 选择下接框的属性值
     *
     * @param id, value
     */
    public void selectByValue(String id, String value) {
        Select selector = new Select(findById(id));
        selector.selectByValue(value);
    }

    /**
     * 选择下接框文本
     *
     * @param
     */
    public void selectByVisibleTextById(String id, String text) {
        Select selector = new Select(findById(id));
        selector.selectByVisibleText(text);
    }


    /**
     * 判断display不为none时（今日剩余预约笔数出现），且出现笔数为0时，预订失败，提示：最大订单数超出当日限制
     *
     * @param
     */
    public boolean isMaxOrderNumberAccess() {
        boolean isOrderLimit = driver.getPageSource().contains("<span id=\"maxOrderNum\" style=\"float:right;margin-right: 20px\">0</span>");
        boolean isShow = driver.getPageSource().contains("<div id=\"shengyuCount\" style=\"display: none;\">");
        if (!isShow && isOrderLimit) {
            //限购且笔数为0
            return true;
        }
        return false;

    }

    /**
     * 判断display不为none时（出现回购协议）时，需要先同意协议
     *
     * @param
     */
    public boolean isProtocolDisplay(String text) {
        try {
            boolean isProtocolDisplay = driver.getPageSource().contains(text);
            return isProtocolDisplay;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 断言-元素指定标签属性的值与期待值一致(如果获取的是文本用getText())
     *
     * @param value
     */
    public void assertValueByXpath(String xpath, String value) {
        String text = findByXpath(xpath).getAttribute("value");
        Assert.assertEquals(text, value);
    }

    /**
     * 断言-元素文本与期待值一致
     *
     * @param value
     */
    public void assertValueEqualsToTheTextByXpath(String xpath, String value) {
        String text = findByXpath(xpath).getText();
        Assert.assertEquals(text, value);
    }

    /**
     * 获取文本用getText()
     *
     * @param
     */
    public String getText(String xpath) {
        String value = findByXpath(xpath).getText();
        //System.out.println(value);
        return value;
    }

    /**
     * 获取文本用getValue()
     *
     * @param
     */
    public String getValue(String xpath) {
        String value = findByXpath(xpath).getAttribute("value");
        return value;
    }

    /**
     * getText()获取文本（例：订单号：18080816373670885409），并只截取（订单号：）后面的字符串
     *
     * @param
     */
    public String getTextAndStartTrim(String xpath) {
        //获取页面订单号
        String trimStr = findByXpath(xpath).getText();
        System.out.println(trimStr);
        //截取订单号
        trimStr = trimStr.substring(trimStr.indexOf("：") + 1);
        System.out.println(trimStr);
        //赋值给subNumber，供大B端/S系统调用
        subNumber = trimStr;
        return trimStr;
    }

    /**
     * getText()获取文本（例：￥：254.380），并只截取（￥）后面的字符串
     *
     * @param
     */
    public String getTextAndTrimDollarSign(String xpath) {
        //获取页面金额
        String trimStr = findByXpath(xpath).getText();
        System.out.println("截取前文本：" + trimStr);
        //截取金额
        trimStr = trimStr.substring(trimStr.indexOf("￥") + 1);
        System.out.println("截取后文本：" + trimStr);
        return trimStr;
    }

    /**
     * getText()获取文本（例：￥：254.380），并只截取空格后面的字符串
     *
     * @param
     */
    public String getTextAndTrimSpace(String xpath) {
        //获取页面金额
        String trimStr = findByXpath(xpath).getText();
        System.out.println("截取前文本：" + trimStr);
        //截取金额
        trimStr = trimStr.substring(trimStr.indexOf(" ") + 1);
        System.out.println("截取后文本：" + trimStr);
        return trimStr;
    }

    /**
     * 断言-订单大于0时下单成功
     *
     * @param
     */
    public void orderNumLimit(String xpath) {
        String num = findByXpath(xpath).getText();
        Integer it = new Integer(num);
        int i = intValue(num);
        findByXpath(xpath).isDisplayed();
        if (i > 0) {

        }
        System.out.println();
        return;
    }

    /**
     * 连接MYSQL数据库获取User_id
     *
     * @param
     */
    private Statement statement = null;
    private java.sql.Connection connect = null;
    private ResultSet result = null;
    private ResultSet result1 = null;
    private ResultSet result2 = null;
    private int result3 = 0;

    //连接数据库插入数据假实名
    public String realnameFromDB(String mobile) {
        try {
            String url = "jdbc:mysql://47.96.187.68:3306/dfshop_db";
            String userName = "hzshop";
            String password = "hZaYs^20_5dF";
            String user_id = "";

            String sql = "select * from ls_usr_detail WHERE user_mobile = " + mobile + "";

            String sql_1 = "UPDATE ls_usr_detail set auth_status='1',is_unwrap='0' where user_mobile = " + mobile + "";

            //连接数据库
            boolean result = mysqlDBConnect(url, userName, password);
            if (!result) {
                System.out.println("connect db failed !!");
                return null;
            }

            ResultSet resultSet = executeQuery(sql);

            if (resultSet.next()) {
                //获取resultSet集里的mobile_code字段值
                user_id = resultSet.getString("user_id");
                System.out.println("user_id :" + user_id);

            } else {
                System.out.println("没有获取到user_id");
                return null;
            }

            //执行SQL语句并获取执行结果
            int resultSet1 = executeUpdate(sql_1);
            if (resultSet1 != 1){
                System.out.println("KkgFrame.realnameFromDB.update_Sql_1 !!");
                return null;
            }

            String sql_2 =
                    "INSERT INTO ls_user_idcard (user_id,real_name,idcard_num,default_sts,update_time,bank_card,idcard_date,channel_no,bank_code,phone,prov,city)" +
                            "VALUES('" + user_id + "','秋~雨','441625199510105858','1','2018-12-14 15:09:45','6262626262626262626','2038-12-12 00:00:00','C_YB_PAY','ICBC','" + mobile + "','广东省','深圳市');";

            int resultSet2 = executeUpdate(sql_2);
            if (resultSet2 != 1){
                if (resultSet1 != 1){
                    System.out.println("KkgFrame.realnameFromDB.update_Sql_2 !!");
                    return null;
                }
            }

            return "更新成功";


        } catch (Exception E) {
            E.printStackTrace();

        }
        closeDb();
        return null;
    }


    /**
     * 连接MYSQL数据库获取验证码
     *
     * @param
     */

    //连接数据库获取验证码
    public String getCodeFromDB(String mobile) {
        try {
            String url = "jdbc:mysql://47.96.187.68:3306/dfshop_db";
            String userName = "hzshop";
            String password = "hZaYs^20_5dF";
            String sql = "select mobile_code from ls_sms_log where user_phone = " + mobile + " ORDER BY id desc limit 1";
            //连接数据库
            boolean result = mysqlDBConnect(url, userName, password);
            if (!result) {
                System.out.println("connect db failed !!");
                return null;
            }
            //执行SQL语句并存储到resultSet集
            ResultSet resultSet = executeQuery(sql);

            if (resultSet.next()) {
                //获取resultSet集里的mobile_code字段值
                String mobile_code = resultSet.getString("mobile_code");
                System.out.println("mobile_code :" + mobile_code);
                return mobile_code;

            } else {
                System.out.println("没有获取到验证码");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        closeDb();
        return null;
    }

    //连接数据库方法
    private boolean mysqlDBConnect(String url, String username, String password) {
        try {
            //mysql驱动
            String driver = "com.mysql.jdbc.Driver";
            //动态加载驱动程序
            Class.forName(driver);
            //getConnection（）方法连接数据库
            connect = DriverManager.getConnection(url, username, password);
            if (!connect.isClosed()) {
                System.out.println("数据库连接成功");
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("链接数据库失败");
            return false;
        }
    }

    //ResultSet类，用来存放获取的结果集
    private ResultSet executeQuery(String sql) {
        try {
            //创建statement类对象，用来执行SQL语句
            this.statement = connect.createStatement();
            this.result = statement.executeQuery(sql);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //int类，用来存放更新个数的结果集
    private int executeUpdate(String sql) {
        try {
            //创建statement类对象，用来执行SQL语句
            this.statement = connect.createStatement();
            this.result3 = statement.executeUpdate(sql);
            return result3;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //关闭ResultSet对象、关闭数据库连接
    private void closeDb() {
        try {
            //关闭ResultSet对象
            result.close();
            //关闭数据库连接
            this.connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void winMax() {
        driver.manage().window().maximize();
    }

}



