package com.dev.shoki.controller;

import com.dev.shoki.constants.CUDefine;
import com.dev.shoki.constants.GS25Define;
import com.dev.shoki.constants.StoreType;
import com.dev.shoki.utils.Utils;
import com.dev.shoki.vo.CU;
import com.dev.shoki.vo.GS25;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shoki on 16. 12. 8..
 */


@RestController
@RequestMapping("/store/cu")
public class CUController {

    WebDriver webDriver;

    @RequestMapping("/init")
    public ResponseEntity gs25() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/shoki/Downloads/chromedriver");

        webDriver = new ChromeDriver();
        webDriver.get("http://cu.bgfretail.com/event/plus.do?category=event&depth2=1&sf=N");
        Thread.sleep(3000);
        loopCU();
        getCUList();

        return new ResponseEntity(null, HttpStatus.OK);
    }

    private void loopCU() throws InterruptedException {
        try {
            WebElement moreBtn = webDriver.findElement(By.className("prodListBtn-w"));
            if(moreBtn != null) {
                moreBtn.click();
                Thread.sleep(5000);
                loopCU();
            }
        } catch (NoSuchElementException e) {

        }
    }

    public void getCUList() throws InterruptedException {
        String htmlSource = webDriver.getPageSource();
        Document document = Jsoup.parse(htmlSource);

        Elements prodListWrap = document.select(".prodListWrap");
        Elements prodListUl = prodListWrap.select("ul");
        Elements prodList = prodListUl.select("li");

        List<CU> listCU = new ArrayList<CU>();
        for (Element item : prodList) {

            System.out.println(item.select(".prodName").text() + " / " + item.select(".prodPrice").text());

//            String flag = item.select("ul").select("li").text();
//
//            CU cu = new CU();
//            cu.setName(item.select(".prodName").text());
//            cu.setImgUrl(item.select("img").attr("src"));
//            cu.setPrice(Utils.convertPrice(item.select(".prodPrice").text()));
//            cu.setStoreType(StoreType.CU);
//
//            if(flag.equals(CUDefine.ProdItemType.ONE_ONE)) {
//                cu.setProdItemType(CUDefine.ProdItemType.ONE_ONE);
//            } else if(flag.equals(CUDefine.ProdItemType.TOW_ONE)) {
//                cu.setProdItemType(CUDefine.ProdItemType.TOW_ONE);
//            } else if(flag.equals(CUDefine.ProdItemType.THREE_ONE)) {
//                cu.setProdItemType(CUDefine.ProdItemType.THREE_ONE);
//            }
//            System.out.println(cu.getName() + " / " + cu.getStoreType());
//            listCU.add(cu);
        }
    }
}
