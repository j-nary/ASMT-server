from crawl import *
import json

# f=open("db_token.txt","r")
# tk=f.readline().rstrip()

# client = MongoClient(tk)
# f.close()
# db = client.jdb
# collection = db.foods

# save_unique_number(["가톨릭대","강서대","감리교신학대","건국대","경기대","경희대","고려대","광운대","국민대"])


result = get_result("중앙대맛집")

json_data = json.dumps(result, ensure_ascii=False)

# JSON 파일로 저장할 경로와 파일 이름을 지정합니다.
file_path = "cau.json"

# JSON 파일로 저장합니다.
with open(file_path, "w", encoding="utf-8") as json_file:
    json_file.write(json_data)

print("JSON 파일이 성공적으로 저장되었습니다.")

# print(result)
# count=1
# for place in result:
#     collection.insert_one(place)
# f=open("datas.json","w")
# data = collection.find({})
# for a in data:
#     a.pop("_id")
#     #print(a)
#     f.write(json.dumps(a,ensure_ascii=False))
#     f.write(",\n")
# f.close()