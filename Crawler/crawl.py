import re
import time
from urllib import parse
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from webdriver_manager.chrome import ChromeDriverManager
import getDistance


def extract_number(string):
    # 변동이나 ~ 패턴이 포함된 경우 0 반환
    if "변동" in string or "~" in string:
        return 0
    
    # 숫자 추출
    numbers = re.sub(r"[^\d]", "", string)
    
    # 추출된 숫자가 비어있는 경우 0 반환
    if not numbers:
        return 0
    
    # 숫자 반환
    return int(numbers)


def get_picture_link(el):
    try:
        src = el.find_element(By.CLASS_NAME, "K0PDV").get_attribute("style")
        return parse.unquote("https://search.pstatic.net/common/?autoRotate=true&quality=95&type=f320_320&"+src[src.find("src="):-3])
    except:
        return None


def save_unique_number(target_locates):
    for target_locate in target_locates:
        temp = []
        driver.get("https://map.naver.com/v5/search/%s" % target_locate)
        time.sleep(5)
        f = open(target_locate + ".txt", 'w')
        driver.switch_to.frame("searchIframe")
        for _ in range(6):
            driver.find_element(By.CSS_SELECTOR, "span.h69bs.KvAhC.utj_r").click()
            for i in range(10):
                time.sleep(0.2)
                driver.find_element(By.XPATH, '//body').send_keys(Keys.CONTROL + Keys.END)
            for i in driver.find_elements(By.CSS_SELECTOR, "span.place_bluelink.TYaxT"):
                i.click()
                time.sleep(0.7)
                driver.switch_to.default_content()
                driver.switch_to.frame("entryIframe")
                p = re.compile("place/\d+")
                m = p.search(driver.current_url).group()
                numbers = re.sub(r'[^0-9]', '', m)
                if numbers not in temp:
                    temp.append(numbers)
                    f.write("%s\n" % numbers)
                driver.switch_to.default_content()
                driver.switch_to.frame("searchIframe")
            driver.find_elements(By.CSS_SELECTOR, "svg.yUtES")[1].click()
        f.close()


def load_saving_number(target):
    with open(target+".txt") as f:
        lines = f.readlines()
    lines = [line.rstrip('\n') for line in lines]
    return lines


def crawl_without_naver_order(driver, i):
    menus = []
    menu_names = []

    try:
        if float(driver.find_element(By.TAG_NAME,"em").text) > 5 :
            stars=None
        else:
            stars=driver.find_element(By.TAG_NAME,"em").text
        info = {"link": "https://map.naver.com/v5/entry/place/" + i,
                "name": driver.find_element(By.CLASS_NAME, "Fc1rA").text,
                "star": stars,
                "locate": driver.find_element(By.CLASS_NAME, "LDgIH").text
                }
        info["distance"] = getDistance.get_distance_between_to_address("서울 동작구 흑석로 84", info["locate"])
        driver.get("https://m.place.naver.com/restaurant/%s/menu/list" % i)
        time.sleep(0.8)
        driver.implicitly_wait(0)
        for more in driver.find_elements(By.CLASS_NAME, "lfH3O"):
            more.click()
        if len(driver.find_elements(By.CLASS_NAME, "YBmM2")) == 0:
            for price, names in zip(driver.find_elements(By.CLASS_NAME, "GXS1X"),
                                    driver.find_elements(By.CLASS_NAME, "lPzHi")):
                # menus.append([names.text, price.text, None])
                a_menu = {
                    "food_name" : names.text,
                    "price" : extract_number(price.text),
                    "img_src" : None
                }
                menus.append(a_menu)

                menu_names.append(names.text)
        else:
            for price, names, picture in zip(driver.find_elements(By.CLASS_NAME, "GXS1X"),
                                            driver.find_elements(By.CLASS_NAME, "lPzHi"),
                                            driver.find_elements(By.CLASS_NAME, "YBmM2")):
                a_menu = {
                    "food_name" : names.text,
                    "price" : extract_number(price.text),
                    "img_src" : get_picture_link(picture)
                }
                menus.append(a_menu)
                # menus.append([names.text, price.text, get_picture_link(picture)])
                menu_names.append(names.text)
        info["menus"] = menus
        info["menu_names"] = menu_names
    except Exception as e:
        print(e)
        return None
    return info


def crawl_with_naver_order(driver, i):
    menus = []
    menu_names=[]

    if float(driver.find_element(By.TAG_NAME, "em").text) > 5:
        stars = None
    else:
        stars = driver.find_element(By.TAG_NAME, "em").text
    info = {"link": "https://map.naver.com/v5/entry/place/" + i,
            "name": driver.find_element(By.CLASS_NAME, "Fc1rA").text,
            "star": stars,
            "locate": driver.find_element(By.CLASS_NAME,"IH7VW").text
            }
    info["distance"] = getDistance.get_distance_between_to_address("서울 관악구 관악로 1", info["locate"])
    driver.get("https://m.place.naver.com/restaurant/%s/menu/list" % i)
    time.sleep(0.8)
    # Todo : 이미지 리스트 구분해서 menus에 append 한 뒤에 info return
    driver.implicitly_wait(0)
    for menu in driver.find_elements(By.CLASS_NAME, "order_list_item"):
        if len(menu.find_elements(By.CSS_SELECTOR, ".img_box.no_img")) == 0:
            name = menu.find_element(By.CLASS_NAME, "tit").text
            price = extract_number(menu.find_element(By.CLASS_NAME, "price").text)
            img_src = get_picture_link_with_naver(menu)
            menus.append([name, price, img_src])
            menu_names.append(name)
        else:
            name = menu.find_element(By.CLASS_NAME, "tit").text
            price = extract_number(menu.find_element(By.CLASS_NAME, "price").text)
            menus.append([name, price, None])
            menu_names.append(name)

    info["menus"] = menus
    info["menu_names"] = menu_names
    return info


def get_picture_link_with_naver(pic):
    try:
        img_src = pic.find_element(By.CLASS_NAME, "img").get_attribute("src")
        return img_src
    except:
        return None


def get_result(target):
    do_crawl(target)
    driver.close()
    return fin


def do_crawl(target):
    nums = load_saving_number(target)
    count = 0
    with open(target+".txt","r") as f :
        lines = len(f.readlines())
    print("총 %d개의 정보가 있습니다. 크롤링할 수를 입력 해 주세요 : (0 : 모두 )" %lines)
    select=int(input())
    if select==0 or select>lines : select=lines
    for i in nums[:select]:
        count += 1
        print("now : %d" % count)
        next_url = "https://m.place.naver.com/restaurant/%s/home" % i
        driver.get(next_url)
        time.sleep(0.7)
        driver.implicitly_wait(0)
        # if len(driver.find_elements(By.CLASS_NAME, "rW1jX")) == 0:
        #     driver.implicitly_wait(1)
        #     res = crawl_without_naver_order(driver, i)
        #     print(res)
        #     if res is not None:
        #         fin.append(res)
        # else:
        #     driver.implicitly_wait(1)
        #     fin.append(crawl_with_naver_order(driver, i))
        res = crawl_without_naver_order(driver, i)
        if res is not None:
            fin.append(res)


fin = []
chrome_options = webdriver.ChromeOptions()
driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)