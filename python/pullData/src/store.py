import pymongo


mongo_client = pymongo.MongoClient("localhost", 27017)
financial_db = mongo_client.financial_data
financial_collection = financial_db.data


class DBStore:
    def __call__(self, data):
        for key, record in data.items():
            financial_collection.update_one({
                "ticker": key,
            }, {"$set": record}, upsert=True)
