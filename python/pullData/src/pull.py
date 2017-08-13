import csv
import numpy
import pandas
import pymongo
import requests

from datetime import datetime
from io import StringIO


mongo_client = pymongo.MongoClient("localhost", 27017)
financial_db = mongo_client.financial_data
financial_collection = financial_db.data


class Pull:

    def __call__(self, source, tickers, start_date, end_date):
        if source=='Google':
            results = self.google_call(tickers, start_date, end_date)
            return results
        elif source=='Database':
            results = self.database_call(tickers, start_date, end_date)
            return results

    def google_call(self, tickers, start_date, end_date):
        """
        google_call makes a call to the google finance api for historical data
        Args:
            None (uses the class variables)
        Returns:
            None (sets self.results)
        """
        results = {}
        for ticker in tickers:
            data_string = "https://www.google.com/finance/historical?q={ticker_symbol}&startdate={start_date}&enddate={end_date}&output=csv".format(
                ticker_symbol = ticker,
                start_date = start_date,
                end_date = end_date
            )
            df = pandas.read_csv(StringIO(requests.get(data_string).text))
            df['Return'] = df.Close - df.Close.shift(-1)
            df['DailyPeriodicReturn'] = (df['Return'] / df.Close.shift(-1))
            df['ContinuouslyCompoundingDailyPeriodicReturn'] = numpy.log(df.Close / df.Close.shift(-1))
            df = df.fillna(0.0)
            results[ticker] = {
                "symbol": ticker,
                "date_added": datetime.utcnow(),
                "data": df.to_dict(orient="records"),
                "close_prices": list(df.Close.values),
                "returns": list(df.Return.values),
                "daily_periodic_return": list(df.DailyPeriodicReturn.values),
                "continuous_daily_periodic_return": list(df.ContinuouslyCompoundingDailyPeriodicReturn.values),
                "start_date": start_date,
                "end_date": end_date,
                "url": data_string
            }
        return results

    def database_call(self, tickers, start_date, end_date):
        """
        database_call makes a call to mongodb for the latest data
        Args:
            None
        """
        results = {}
        for ticker in tickers:
            results[ticker] = financial_collection.find({
                "ticker": ticker})[:][0]
        return results
