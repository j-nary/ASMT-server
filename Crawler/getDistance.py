import requests
import math

kakao_key = open("kakaoRestKey.txt", "r").read()


def __get_distance_result__(x1, y1, x2, y2):
    return int(round(math.sqrt((x1 - x2) ** 2 + (y1 - y2) ** 2), 0))


def get_distance_between_to_address(addr1, addr2):
    response = requests.get(
        "https://dapi.kakao.com/v2/local/search/address.json",
        params={"query": addr1},
        headers={"Authorization": "KakaoAK " + kakao_key},
    ).json()
    x, y = response["documents"][0]["x"], response["documents"][0]["y"]  # x, y= 위도, 경도

    response = requests.get(
        "https://dapi.kakao.com/v2/local/geo/transcoord.json",
        params={"x": x, "y": y, "output_coord": "WTM"},
        headers={"Authorization": "KakaoAK " + kakao_key},
    ).json()
    # x1, y1 = 위경도를 WTM으로 변환
    x1, y1 = response["documents"][0]["x"], response["documents"][0]["y"]

    response = requests.get(
        "https://dapi.kakao.com/v2/local/search/address.json",
        params={"query": addr2},
        headers={"Authorization": "KakaoAK " + kakao_key},
    ).json()
    x, y = response["documents"][0]["x"], response["documents"][0]["y"]

    response = requests.get(
        "https://dapi.kakao.com/v2/local/geo/transcoord.json",
        params={"x": x, "y": y, "output_coord": "WTM"},
        headers={"Authorization": "KakaoAK " + kakao_key},
    ).json()
    x2, y2 = response["documents"][0]["x"], response["documents"][0]["y"]

    return __get_distance_result__(x1, y1, x2, y2)


print(get_distance_between_to_address("서울 동작구 사당로 22 창주빌딩", "서울 동작구 사당로 50"))
