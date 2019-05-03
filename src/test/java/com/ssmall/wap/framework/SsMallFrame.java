package com.ssmall.wap.framework;

import com.kkg.wap.framework.GlobalSettings;
import com.ssmall.wap.data.RunProData;
import com.ssmall.wap.log.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SsMallFrame {

    private WebDriver driver;
    private int timeout = Integer.parseInt(GlobalSettings.timeout);

    public boolean isRun = true;

    private int driverTyep;
    public static int CHROMEDRIVER = 1;

    public HashMap<String,String> map;

    public ArrayList<String> runData;

    /**
     * 构造函数初始化webdriver
     *
     * @param browserType 是浏览器类型
     */
    public SsMallFrame(int browserType) {
        map = new HashMap<>();
        runData = new ArrayList<>();

        driverTyep = browserType;
        switch (driverTyep) {
            //1:chrome
            case 1:
                System.setProperty("webdriver.chrome.driver", "res/chromedriver.exe");
                //设置chrome跨域
                ChromeOptions options=new ChromeOptions();
                options.addArguments("--args --disable-web-security --user-data-dir");
                driver = new ChromeDriver(options);
                Log.log("info","成功打开浏览器");
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
     * 获取webdriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     *
     *  打开网址
     *
     * @param url 浏览器地址
     */
    public void openUrl(String url) {
        if(url.contains("url!")){
            driver.get(map.get(url.substring(4)));
            Log.log("info","成功打开网址");
            return;
        }
        driver.get(url);
        Log.log("info","成功打开网址");
    }

    /**
     * 退出浏览器
     */
    public void quit() {
        driver.quit();
        Log.log("info","成功退出浏览器");
    }

    /**
     * 暂停
     *
     * @param time 是等待的时间单位毫秒
     */
    public void pause(int time) {
        if (time <= 0) {
            Log.log("info","Pause " + time + " ms");
            return;
        }
        try {
            Thread.sleep(time);
            if (time > RunProData.PAUSETIME) {
                Log.log("info", "Pause " + time + " ms");
            }
        } catch (InterruptedException e) {
            Log.log("error",e.getMessage());
        }
    }


    /**
     *
     *  执行点击操作
     *
     * @param value
     * @param xpath 唯一标识
     */
    public void clickKey(String value,String xpath) {
        if(value.contains("get!")){
            String s = value.substring(4);
            String s1 = map.get(s);
            String s2 = xpath.replaceFirst("get!",s1);
            findByXpath(s2).click();
            return;
        }

        if (value.contains(("linkText"))){
            try {
                findByLinkText(xpath).click();
            } catch (Exception e){
                isRun = false;
            }
            return;
        }
        findByXpath(xpath).click();
    }

    /**
     *
     *  等待文本出现并执行点击操作
     *
     * @param text 唯一标识
     */
    public void seeandclickKey(String text) {
        WebDriverWaitAndclickBylinkText(10,text);
    }

    /**
     *
     * 输入文本
     *
     * @param text 输入的文本内容
     * @param xpath 唯一标识
     *
     */
    public void setText(String text,String xpath) {
        WebElement webElement = findByXpath(xpath);
        Log.log("info",webElement.getAttribute("type"));
        webElement.clear();

        if (text.contains("set!")){
            webElement.sendKeys(map.get(text.substring(4)));
            Log.log("info","成功输入文本:" + map.get(text.substring(4)));
            return;
        }
        if (text.contains("random!")){
            if (text.contains("mobile")) {
                StringBuffer sb = new StringBuffer("1803");
                for (int i = 0; i < 7; i++) {
                    int random = (int) (Math.random() * 10);
                    sb.append(random);
                }
                map.put("mobile",sb.toString());
                map.put("mobile_name","中" + sb.toString().substring(4));

                webElement.sendKeys(map.get("mobile"));
                Log.log("info", "成功输入文本:" + map.get("mobile"));
            }
            return;
        }
        webElement.sendKeys(text);
        Log.log("info","成功输入文本:" + text);
    }



    /**
     *
     *  在指定的元素中上输入文件
     *
     * @param filename
     * @param xpath
     */
    public void upload(String filename, String xpath) {
        WebElement  file = driver.findElement(By.xpath(xpath));
        file.sendKeys(filename);
        Log.log("info","设置文件" + filename + "完成！");
    }

    /**
     *
     * 获取文本的值
     *
     * @param valueKey 获取与map的关键字
     * @param xpath 获取文本的路径
     */
    public void getText(String valueKey, String xpath) {
        String text = findByXpath(xpath).getText();
        getHandle(valueKey,text);
    }

    /**
     *
     * 获取文本的值
     *
     * @param valueKey 获取与map的关键字
     * @param xpath 获取文本的路径
     */
    public void getValue(String valueKey, String xpath) {
        String text = findByXpath(xpath).getAttribute("value");
        getHandle(valueKey,text);
    }

    /**
     *
     * 处理获取的文本，写入map
     *
     * @param valueKey 获取与map的关键字
     * @param text 获取到的文本
     */
    private void getHandle(String valueKey,String text){
        if(!text.isEmpty()){
            if(valueKey.contains("code!")){
                String[] valueStr = valueKey.split("!");
                map.put(valueStr[1],text.substring(5,11));
                Log.log("info","成功获取文本：" + text.substring(5,11));
                return;
            }

            if(valueKey.contains("!")){
                String[] valueStr = valueKey.split("!");
                map.put(valueStr[1],text.replace(valueStr[0],"").trim());
            }else {
                map.put(valueKey,text);
            }
            Log.log("info","成功获取文本：" + text);
            return;
        }
        map.put(valueKey,"0");
        Log.log("error","获取文本失败,对应key已经见赋值为0");

    }

    /**
     *
     * 检查特定文本是否存在
     *
     * @param look 有值就獲取map中的值查找
     * @param text 文本内容
     */
    public void seeText(String look,String text) {
        if (look.contains("!")){
            isRun = driver.getPageSource().contains(map.get(text));
        }else {
            isRun = driver.getPageSource().contains(text);
        }
        if(isRun){
            Log.log("info","文本:" + text + "，是否存在：" + isRun);
            if(look.contains("click")){
                findByLinkText(text).click();
            }
            return;
        }
        if(!isRun){
            Log.log("info","文本:" + text + "，是否存在：" + isRun);
            if(look.contains("FALSE")){
                isRun = true;
            }
            return;
        }
    }

    /**
     *
     * 检查特定元素是否存在
     *
     * @param xpath 唯一标识
     */
    public void seeXpath(String xpath) {
        WebElement webElement = findByXpath(xpath);
        isRun = webElement.isDisplayed();
        Log.log("info","元素:" + xpath + "，是否存在：" + isRun);
        if (!isRun) {
            Log.log("error","can not found xpath:" + xpath);
        }
    }

    /**
     * 通过xpath查找控件
     *
     * @param xpath 是控件xpath
     */
    private WebElement findByXpath(String xpath) {
        final long endTime = System.currentTimeMillis() + timeout;
        WebElement webElement = null;
        while (true) {
            try {
                webElement = driver.findElement(By.xpath(xpath));
                if (webElement.isDisplayed()) {
                    Log.log("info","成功获取到元素：" + xpath);
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    Log.log("info","can not find element by xpath:" + xpath + " retry!!!");
                    continue;
                } else {
                    //BUG: 找不到，在这超时会卡死
                    Log.log("error","获取元素超时");
                    isRun = false;
                    break;
                }
            }
        }
        return webElement;
    }

    /**
     * 通过文本查找控件
     *
     * @param linkText 是控件id
     */
    private WebElement findByLinkText(String linkText) {
        final long endTime = System.currentTimeMillis() + timeout;
        WebElement webElement;
        while (true) {
            try {
                By s = By.linkText(linkText);
                webElement = driver.findElement(s);
                if (webElement.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                if (System.currentTimeMillis() < endTime) {
                    Log.log("info","can not find element by linkText:" + linkText + " retry!!!");
                    continue;
                } else {
                    Log.log("error","Time out to find element by linkText: " + linkText + " !!!");
                    isRun = false;
                }
            }
        }
        return webElement;
    }

    /**
     *
     * 验金额准确性
     *
     * @param value  计算公式的描述
     * @param reckonText 计算公式
     */
    public void reckon(String value,String reckonText) {
        String[] reckonTexts = reckonText.split(",");
        Double dou = 0.0;
        Double count  = Double.parseDouble(map.get(reckonTexts[0])) * 2;

        for(String key : reckonTexts){
            dou += Double.parseDouble(map.get(key));
            Log.log("info",key + "：" + map.get(key));
        }

        if (dou - count < 0.000001 && dou - count > -0.000001){
            Log.log("info",value + "计算正确");
            return;
        }
        Log.log("info",value + "计算错误");

    }


    /**
     * 计算预付款券的资金是否计算正确
     */
    public void reckonYufuKuan() {
        Double d = Double.parseDouble(map.get("yufukuan"))
                - Double.parseDouble(map.get("realPrice"));
        if (d > 0.0000001){
            map.put("yufukuan",map.get("backBounty"));
        }

        Double d0 = Double.parseDouble(map.get("total-0"))
                + Double.parseDouble(map.get("yufukuan"))
                - Double.parseDouble(map.get("fee"))
                - Double.parseDouble(map.get("dingjin"))
                - Double.parseDouble(map.get("total-1"));
        if(0.0 - d0 < 0.000001 && 0.0 - d0 > -0.000001){
            Log.log("info","资金总额计算正确！");
        }else {
            Log.log("error","资金总额计算錯誤！");
            isRun = false;
        }

        Double d1 = Double.parseDouble(map.get("total-use-0"))
                + Double.parseDouble(map.get("yufukuan"))
                - Double.parseDouble(map.get("realPrice"))
                - Double.parseDouble(map.get("total-use-1"));
        if(0.0 - d1 < 0.000001 && 0.0 - d1 > -0.000001){
            Log.log("info","可用资金计算正确！");
        }else {
            Log.log("error","可用资金计算错误！");
            isRun = false;
        }

        Double d2 = Double.parseDouble(map.get("jindou-0"))
                + Double.parseDouble(map.get("backBounty"))
                - Double.parseDouble(map.get("jindou-1"));
        if(0.0 - d2 < 0.000001 && 0.0 - d2 > -0.000001){
            Log.log("info","金豆计算正确！");
        }else {
            isRun = false;
            Log.log("error","金豆计算错误！");
        }

        //以上是下单后
        //以下是退订后

        if(Double.parseDouble(map.get("yufukuan")) - Double.parseDouble(map.get("fee")) > 0.0000001){
            if(Double.parseDouble(map.get("top_profitClose")) > 0.0000001){
                Double d4 = Double.parseDouble(map.get("total-1"))
                        - Double.parseDouble(map.get("yufukuan"))
                        + Double.parseDouble(map.get("fee"))
                        + Double.parseDouble(map.get("dingjin"))
                        + Double.parseDouble(map.get("top_profitClose"))
                        - Double.parseDouble(map.get("total-2"));
                if(0.0 - d4 < 0.000001 && 0.0 - d4 > -0.000001){
                    Log.log("info","退订后资金总额计算正确！");
                }else {
                    Log.log("error","退订后资金总额计算错误！");
                    isRun = false;
                }

                Double d3 = Double.parseDouble(map.get("total-use-1"))
                        - Double.parseDouble(map.get("yufukuan"))
                        + Double.parseDouble(map.get("fee"))
                        + Double.parseDouble(map.get("dingjin"))
                        + Double.parseDouble(map.get("orderPrice"))
                        - Double.parseDouble(map.get("total-use-2"));
                if(0.0 - d3 < 0.000001 && 0.0 - d3 > -0.000001){
                    Log.log("info","退订后可用资金计算正确！");
                }else {
                    Log.log("error","退订后可用资金计算错误！");
                    isRun = false;
                }
            }else {
                if(Double.parseDouble(map.get("yufukuan"))
                        - Double.parseDouble(map.get("fee"))
                        - Double.parseDouble(map.get("dingjin"))
                        - Math.abs(Double.parseDouble(map.get("top_profitClose"))) > 0.00000001){

                    Double d4 = Double.parseDouble(map.get("total-1"))
                            - Double.parseDouble(map.get("yufukuan"))
                            + Double.parseDouble(map.get("fee"))
                            + Double.parseDouble(map.get("dingjin"))
                            - Double.parseDouble(map.get("total-2"));
                    if(0.0 - d4 < 0.000001 && 0.0 - d4 > -0.000001){
                        Log.log("info","退订后资金总额计算正确！");
                    }else {
                        Log.log("error","退订后资金总额计算错误！");
                        isRun = false;
                    }

                    Double d3 = Double.parseDouble(map.get("total-use-1"))
                            - Double.parseDouble(map.get("yufukuan"))
                            + Double.parseDouble(map.get("realPrice"))
                            - Double.parseDouble(map.get("total-use-2"));
                    if(0.0 - d3 < 0.000001 && 0.0 - d3 > -0.000001){
                        Log.log("info","退订后可用资金计算正确！");
                    }else {
                        Log.log("error","退订后可用资金计算错误！");
                        isRun = false;
                    }

                }else {

                    Double d4 = Double.parseDouble(map.get("total-1"))
                            + Double.parseDouble(map.get("yufukuan"))
                            - Double.parseDouble(map.get("fee"))
                            - Double.parseDouble(map.get("dingjin"))
                            + Double.parseDouble(map.get("top_profitClose"))
                            - Double.parseDouble(map.get("total-2"));
                    if(0.0 - d4 < 0.000001 && 0.0 - d4 > -0.000001){
                        Log.log("info","退订后资金总额计算正确！");
                    }else {
                        Log.log("error","退订后资金总额计算错误！");
                        isRun = false;
                    }

                    Double d3 = Double.parseDouble(map.get("total-use-1"))
                            + Double.parseDouble(map.get("yufukuan"))
                            - Double.parseDouble(map.get("fee"))
                            - Double.parseDouble(map.get("dingjin"))
                            + Double.parseDouble(map.get("orderPrice"))
                            + Double.parseDouble(map.get("top_profitClose"))
                            - Double.parseDouble(map.get("total-use-2"));
                    if(0.0 - d3 < 0.000001 && 0.0 - d3 > -0.000001){
                        Log.log("info","退订后可用资金计算正确！");
                    }else {
                        Log.log("error","退订后可用资金计算错误！");
                        isRun = false;
                    }
                }
            }
        }else {

            Double d4 = Double.parseDouble(map.get("total-1"))
                    + Double.parseDouble(map.get("top_profitClose"))
                    - Double.parseDouble(map.get("total-2"));
            if(0.0 - d4 < 0.000001 && 0.0 - d4 > -0.000001){
                Log.log("info","退订后资金总额计算正确！");
            }else {
                Log.log("error","退订后资金总额计算错误！");
                isRun = false;
            }

            Double d3 = Double.parseDouble(map.get("total-use-1"))
                    + Double.parseDouble(map.get("orderPrice"))
                    + Double.parseDouble(map.get("top_profitClose"))
                    - Double.parseDouble(map.get("total-use-2"));
            if(0.0 - d3 < 0.000001 && 0.0 - d3 > -0.000001){
                Log.log("info","退订后可用资金计算正确！");
            }else {
                Log.log("error","退订后可用资金计算错误！");
                isRun = false;
            }

        }
        if (!isRun){
            for (Map.Entry<String, String> m : map.entrySet()) {
                Log.log("error","key:" + m.getKey() + " value:" + m.getValue());
            }
        }


    }

    /**
     * 执行js,操作滚动条到任意高度
     */
    public void scrollHight(String keyValue,int height) {

        if(keyValue.contains("app")){
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, " + height + ")");
            return;
        }

        //滚动条滚动
        ((JavascriptExecutor) driver).executeScript("document.documentElement.scrollTop=" + height);
//        String setscroll = "document.documentElement.scrollLeft=" + "500";//水平滚动条  参考http://blog.csdn.net/liuxueyi521/article/details/49022465
//        String high ="scrollTo(0,10000)";//直接输入数据

    }

    /**
     * 外部插入数据
     *
     * @param key 关键字
     * @param value 值
     */
    public void mapput(String key, String value) {
        if (value.contains("put!")){
            map.put(key,map.get(value.substring(4)));
            return;
        }
        map.put(key,value);
    }

    /**
     *
     *通过xpath悬停鼠标
     *
     * @param xpath
     */
    public void hover(String xpath) {
        pause(RunProData.PAUSETIME);
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
            Log.log("error","Failed to click and hold by xpath: " + xpath);
            isRun = false;
        }
        Log.log("info","click and hold by xpath: " + xpath);
    }

    /**
     * 显式等待页面LinkText出现并点击
     *
     * @param linkText
     *
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
     *
     * 下拉框选择选项
     *
     * @param index 选择的选项ID
     * @param xpath 下拉框的元素路径
     */
    public void list(int index, String xpath) {
        new Select(driver.findElement(By.xpath(xpath))).selectByIndex(index);
    }


    /**
     * 进入iframe
     *
     * @param xpath
     */
    public void goiframe(String xpath) {
        driver.switchTo().frame(driver.findElement(By.xpath((xpath))));
        pause(2000);
        Log.log("info","成功进入ifame");
    }


    /**
     * 退出iframe
     *
     */
    public void endiframe() {
        driver.switchTo().defaultContent();
        pause(2000);
        Log.log("info","成功退出ifame");
    }

    /**
     * 刷新界面
     */
    public void refresh() {
        pause(RunProData.PAUSETIME);
        driver.navigate().refresh();
        Log.log("info","Refreshed");
    }

    /**
     * 切换窗口--打开2个以上窗口
     */
    public void switchWindowByIndex(int i) {
        Set<String> windowHandles = driver.getWindowHandles();//获取当前窗口句柄
        List<String> it = new ArrayList<>(windowHandles);//获取所有窗口句柄
        try {
            pause(1000);
            driver.switchTo().window(it.get(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // driver.switchTo().window(it.get(0));//切换到新窗口
    }


    /**
     *
     * 判断元素是否存在，是否点击，不存在，点击无影响
     *
     * @param iskey 是否点击，不填写为点击
     * @param xpath 点击元素路径
     */
    public void isclick(String iskey, String xpath) {

        if(!iskey.isEmpty()){
            if (!Boolean.parseBoolean(iskey)){
                //.....
                return;
            }
        }
        try {
            driver.findElement(By.xpath(xpath)).click();
        }catch (Exception e){

        }finally {
            return;
        }

    }
}
