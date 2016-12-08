package com.dev.shoki.controller;

import com.dev.shoki.constants.ProdItemType;
import com.dev.shoki.constants.StoreType;
import com.dev.shoki.vo.GS25;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by shoki on 16. 12. 4..
 */
@RestController
@RequestMapping("/store/gs25")
public class GSController {


    WebDriver webDriver;

    @RequestMapping("/init")
    public void gs25() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/shoki/Downloads/chromedriver");

        webDriver = new ChromeDriver();
        webDriver.get("http://gs25.gsretail.com/gscvs/ko/products/event-goods");
        Thread.sleep(3000);
        webDriver.findElement(By.id("TOTAL")).click();
        Thread.sleep(5000);
        getGS25List();
    }

    public void getGS25List() throws InterruptedException {
        String htmlSource = webDriver.getPageSource();
        Document document = Jsoup.parse(htmlSource);

        Elements eventTab = document.select(".eventtab");
        Elements tblWrap = eventTab.select(".tblwrap").attr("style", "display:block");
        Element wrap = tblWrap.get(tblWrap.size()-1);
        Elements prodList = wrap.select(".prod_list");

        List<GS25> listGs25 = new ArrayList<GS25>();
        for (Element item : prodList) {
            for (Element prodBox : item.select(".prod_box")) {

                String flag = prodBox.select(".flag_box").select("span").text();
                String cost = prodBox.select(".cost").text();

                GS25 gs25 = new GS25();
                gs25.setName(prodBox.select(".tit").text());
                if(flag.equals(ProdItemType.ONE_ONE)) {
                    gs25.setProdItemType(ProdItemType.ONE_ONE);
                } else if(flag.equals(ProdItemType.TOW_ONE)) {
                    gs25.setProdItemType(ProdItemType.TOW_ONE);
                } else if(flag.equals(ProdItemType.BONUS)) {
                    gs25.setProdItemType(ProdItemType.BONUS);
                }
                gs25.setStoreType(StoreType.GS25);
                gs25.setImgUrl(prodBox.select("img").attr("src"));
                gs25.setPrice(Integer.valueOf(cost.replaceAll(",", "").replaceAll("원", "")));

                listGs25.add(gs25);
            }
        }
        ((JavascriptExecutor)webDriver).executeScript("goodsPageController.moveControl(1)");
        Thread.sleep(3000);
        getGS25List();
    }
}
