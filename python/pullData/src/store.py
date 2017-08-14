import pandas
import pymongo


mongo_client = pymongo.MongoClient("localhost", 27017)
financial_db = mongo_client.financial_data
financial_collection = financial_db.data
data_collection = financial_db.raw_data


class DBStore:
    def __call__(self, data):
        for key, record in data.items():
            financial_collection.update_one({
                "ticker": key,
            }, {"$set": record}, upsert=True)
    
    def normsinv(self):
        norms = pandas.read_csv('normsinv.csv')
        record = {"data": list(norms.NORMSINV.values)}
        data_collection.update_one({
                "type": "NORMSINV",
            }, {"$set": record}, upsert=True)
        # print("here")
